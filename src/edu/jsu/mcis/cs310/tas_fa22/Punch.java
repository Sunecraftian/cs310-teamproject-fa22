package edu.jsu.mcis.cs310.tas_fa22;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("ALL")
public class Punch {

    private final Integer id, terminalid;
    private final Badge badge;
    private final EventType punchtype;
    private final LocalDateTime originaltimestamp;
    private LocalDateTime adjustedtimestamp;
    private final PunchAdjustmentType adjustmentType;

    // New Punch \\
    public Punch(int terminalid, Badge badge, EventType punchtype) {
        this.id = null;
        this.terminalid = terminalid;
        this.badge = badge;
        this.punchtype = punchtype;
        this.originaltimestamp = LocalDateTime.now();
        this.adjustmentType = null;
    }

    // Existing Punch \\
    public Punch(int id, int terminalid, Badge badge, LocalDateTime originaltimestamp, EventType punchtype) {
        this.id = Integer.valueOf(id);
        this.terminalid = terminalid;
        this.badge = badge;
        this.punchtype = punchtype;
        this.originaltimestamp = originaltimestamp;
        this.adjustmentType = PunchAdjustmentType.SHIFT_START;
    }

    public String printOriginal() {
        String original = "#";
        String badgeID = getBadge().getId();
        String pType = getPunchtype().toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MM/dd/yyyy HH:mm:ss");
        String timeStamp = getOriginaltimestamp().format(formatter);

        original += badgeID + " " + pType + ": " + timeStamp;

        return original;
    }
    public Integer getId() { return id; }
    public Badge getBadge() {
        return badge;
    }

    public LocalDateTime getOriginaltimestamp() {
        return originaltimestamp;
    }

    public int getTerminalid() {
        return terminalid.intValue();
    }

    public EventType getPunchtype() {
        return punchtype;
    }

}
