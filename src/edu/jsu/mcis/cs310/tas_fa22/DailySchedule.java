package edu.jsu.mcis.cs310.tas_fa22;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;

public class DailySchedule {

    private final LocalTime shiftstart, shiftstop, lunchstart, lunchstop;
    private final int id, roundinterval, graceperiod, lunchthreshhold, dockpenalty, shiftduration, lunchduration;

    public DailySchedule(HashMap<String, String> map) {
        this.id = Integer.parseInt(map.get("id"));
        this.shiftstart = LocalTime.parse(map.get("shiftstart"));
        this.shiftstop = LocalTime.parse(map.get("shiftstop"));
        this.roundinterval = Integer.parseInt(map.get("roundinterval"));
        this.graceperiod = Integer.parseInt(map.get("graceperiod"));
        this.dockpenalty = Integer.parseInt(map.get("dockpenalty"));
        this.lunchstart = LocalTime.parse(map.get("lunchstart"));
        this.lunchstop = LocalTime.parse(map.get("lunchstop"));
        this.lunchthreshhold = Integer.parseInt(map.get("lunchthreshold"));
        this.lunchduration = (int) Duration.between(this.lunchstart, this.lunchstop).toMinutes();
        this.shiftduration = (int)Duration.between(this.shiftstart, this.shiftstop).toMinutes();
    }

    public int getId() { return id; }

    public LocalTime getShiftStart() { return shiftstart; }

    public LocalTime getShiftStop() { return shiftstop; }

    public int getRoundInterval() { return roundinterval; }

    public int getGracePeriod() { return graceperiod; }

    public int getDockPenalty() { return dockpenalty; }

    public LocalTime getLunchStart() { return lunchstart; }

    public LocalTime getLunchStop() { return lunchstop; }

    public int getLunchThreshhold() { return lunchthreshhold; }

    public int getShiftDuration() { return shiftduration; }

    public int getLunchDuration() { return lunchduration; }

}
