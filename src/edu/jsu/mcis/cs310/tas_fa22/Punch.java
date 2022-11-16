package edu.jsu.mcis.cs310.tas_fa22;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


public class Punch {

    private final Integer id, terminalid;
    private final Badge badge;
    private final EventType punchtype;
    private final LocalDateTime originaltimestamp;
    private LocalDateTime adjustedtimestamp;
    private PunchAdjustmentType adjustmentType;

    // New Punch \\
    public Punch(int terminalid, Badge badge, EventType punchtype) {
        this.id = null;
        this.terminalid = terminalid;
        this.badge = badge;
        this.punchtype = punchtype;
        this.originaltimestamp = LocalDateTime.now();
    }

    // Existing Punch \\
    public Punch(int id, int terminalid, Badge badge, LocalDateTime originaltimestamp, EventType punchtype) {
        this.id = id;
        this.terminalid = terminalid;
        this.badge = badge;
        this.punchtype = punchtype;
        this.originaltimestamp = originaltimestamp;
    }

    public void adjust(Shift s) {
        this.adjustedtimestamp = this.getOriginaltimestamp();

        int dayOfWeek = this.getOriginaltimestamp().getDayOfWeek().getValue();
        LocalTime originalLocalTime = this.getOriginaltimestamp().toLocalTime();

        LocalTime intervalBeforeShift = s.getShiftStart().minus(s.getRoundInterval(), ChronoUnit.MINUTES);
        LocalTime shiftStart = s.getShiftStart();
        LocalTime graceAfterStart = s.getShiftStart().plus(s.getGracePeriod(), ChronoUnit.MINUTES);
        LocalTime dockAfterStart = s.getShiftStart().plus(s.getDockPenalty(), ChronoUnit.MINUTES);

        LocalTime lunchStart = s.getLunchStart();
        LocalTime lunchEnd = s.getLunchStop();

        LocalTime dockBeforeEnd = s.getShiftStop().minus(s.getDockPenalty(), ChronoUnit.MINUTES);
        LocalTime graceBeforeEnd = s.getShiftStop().minus(s.getGracePeriod(), ChronoUnit.MINUTES);
        LocalTime shiftEnd = s.getShiftStop();
        LocalTime intervalAfterShift = s.getShiftStop().plus(s.getRoundInterval(), ChronoUnit.MINUTES);

        boolean isWeekend = dayOfWeek == 6 || dayOfWeek == 7;  // Weekend Punch
        boolean inInterval = false, inGrace = false, inDock = false, inLunch = false;    // Clock In Punches
        boolean outInterval = false, outGrace = false, outDock = false, outLunch = false;   // Clock Out Punches


        if (this.getPunchtype() == EventType.CLOCK_IN) {
            if (originalLocalTime.isAfter(intervalBeforeShift.minusSeconds(1)) && originalLocalTime.isBefore(shiftStart))
                inInterval = true;
            if (originalLocalTime.isAfter(shiftStart) && originalLocalTime.isBefore(graceAfterStart)) inGrace = true;
            if (originalLocalTime.isAfter(graceAfterStart) && originalLocalTime.isBefore(dockAfterStart.plusSeconds(1))) inDock = true;
            if (originalLocalTime.isAfter(lunchStart) && originalLocalTime.isBefore(lunchEnd)) inLunch = true;
        }

        if (this.getPunchtype() == EventType.CLOCK_OUT) {
            if (originalLocalTime.isAfter(lunchStart) && originalLocalTime.isBefore(lunchEnd)) outLunch = true;
            if (originalLocalTime.isAfter(dockBeforeEnd.minusSeconds(1)) && originalLocalTime.isBefore(graceBeforeEnd)) outDock = true;
            if (originalLocalTime.isAfter(graceBeforeEnd) && originalLocalTime.isBefore(shiftEnd)) outGrace = true;
            if (originalLocalTime.isAfter(shiftEnd) && originalLocalTime.isBefore(intervalAfterShift.plusSeconds(1)))
                outInterval = true;

        }


        if (!isWeekend) {
            if (inInterval || inGrace) {
                this.adjustedtimestamp = this.getOriginaltimestamp().with(shiftStart);
                this.adjustmentType = PunchAdjustmentType.SHIFT_START;
                return;
            } else if (inDock) {
                this.adjustedtimestamp = this.getOriginaltimestamp().with(dockAfterStart);
                this.adjustmentType = PunchAdjustmentType.SHIFT_DOCK;
                return;
            } else if (outLunch) {
                this.adjustedtimestamp = this.getOriginaltimestamp().with(lunchStart);
                this.adjustmentType = PunchAdjustmentType.LUNCH_START;
                return;
            } else if (inLunch) {
                this.adjustedtimestamp = this.getOriginaltimestamp().with(lunchEnd);
                this.adjustmentType = PunchAdjustmentType.LUNCH_STOP;
                return;
            } else if (outDock) {
                this.adjustedtimestamp = this.getOriginaltimestamp().with(dockBeforeEnd);
                this.adjustmentType = PunchAdjustmentType.SHIFT_DOCK;
                return;
            } else if (outGrace || outInterval) {
                this.adjustedtimestamp = this.getOriginaltimestamp().with(shiftEnd);
                this.adjustmentType = PunchAdjustmentType.SHIFT_STOP;
                return;
            }
        }
        int roundinterval = s.getRoundInterval();

        int sec = this.getOriginaltimestamp().getSecond();
        int min = this.getOriginaltimestamp().getMinute();

        if (min % roundinterval == 0) {
            this.adjustedtimestamp = this.adjustedtimestamp.truncatedTo(ChronoUnit.MINUTES);
            this.adjustmentType = PunchAdjustmentType.NONE;
            return;
        }

        min = (sec >= 30) ? this.getOriginaltimestamp().plusMinutes(1).getMinute() : this.getOriginaltimestamp().getMinute();
        if (sec >= 30) this.adjustedtimestamp = this.getOriginaltimestamp().plusMinutes(1);

        if (min % roundinterval < Math.ceil(roundinterval / 2f)) {
            this.adjustedtimestamp = this.adjustedtimestamp.truncatedTo(ChronoUnit.MINUTES).minusMinutes(min % roundinterval);
        } else if (min % roundinterval >= Math.ceil(roundinterval / 2f)) {
            this.adjustedtimestamp = this.adjustedtimestamp.truncatedTo(ChronoUnit.MINUTES).plusMinutes(roundinterval - (min % roundinterval));
        }
        this.adjustmentType = PunchAdjustmentType.INTERVAL_ROUND;
    }

    public String printOriginal() {
        return String.format("#%s %s: %s", getBadge().getId(), getPunchtype().toString(),
                getOriginaltimestamp().format(DateTimeFormatter.ofPattern("E MM/dd/yyyy HH:mm:ss")).toUpperCase());
    }

    public String printAdjusted() {
        return String.format("#%s %s: %s (%s)", getBadge().getId(), getPunchtype().toString(),
                getAdjustedtimestamp().format(DateTimeFormatter.ofPattern("E MM/dd/yyyy HH:mm:ss")).toUpperCase(), getAdjustmentType());
    }

    public Integer getId() {
        return id;
    }

    public Badge getBadge() {
        return badge;
    }

    public LocalDateTime getOriginaltimestamp() {
        return originaltimestamp;
    }

    public LocalDateTime getAdjustedtimestamp() {
        return adjustedtimestamp;
    }

    public int getTerminalid() {
        return terminalid;
    }

    public EventType getPunchtype() {
        return punchtype;
    }

    public PunchAdjustmentType getAdjustmentType() {
        return adjustmentType;
    }
}
