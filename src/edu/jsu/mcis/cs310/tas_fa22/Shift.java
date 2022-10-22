package edu.jsu.mcis.cs310.tas_fa22;

//import java.time.LocalTime;
import java.util.HashMap;
import java.time.*;
public class Shift {

    //private final int lunchduration, shiftduration;
    private int GracePeriod, RoundInterval, Dock, Id, lunchThresh, LunchDuration,
    ShiftDuration;

    private String description;
    private LocalTime shiftStart, shiftStop, lunchStart, lunchStop;
    //HashMap<String, String> map = new HashMap<String, String>();
    //CONSTRUCTOR
    public Shift(HashMap<String, String> map){
        this.Id = Integer.parseInt(map.get("id"));
        this.description = map.get("description");
        this.RoundInterval = Integer.parseInt(map.get("roundinterval"));
        this.GracePeriod = Integer.parseInt(map.get("graceperiod"));
        this.shiftStart = LocalTime.parse(map.get("shiftstart"));
        this.shiftStop = LocalTime.parse(map.get("shiftstop"));
        this.lunchStart = LocalTime.parse(map.get("lunchstart"));
        this.lunchStop = LocalTime.parse(map.get("lunchstop"));
        this.lunchThresh = Integer.parseInt(map.get("lunchthreshold"));
        this.Dock = Integer.parseInt(map.get("dockpenalty"));
        this.LunchDuration = (int)Duration.between(this.lunchStart, this.lunchStop).toMinutes();
        this.ShiftDuration = (int)Duration.between(this.shiftStart, this.shiftStop).toMinutes();
    }

    public int getId() {
        return Id;
    }

    public int getRoundInterval() {
        return RoundInterval;
    }

    public int getGracePeriod() {
        return GracePeriod;
    }

    public int getDock() {
        return Dock;
    }

    public LocalTime getLunchStart() {
        return lunchStart;
    }

    public int getLunchThresh() {
        return lunchThresh;
    }

    public LocalTime getLunchStop() {
        return lunchStop;
    }

    public String getDescription() {
        return description;
    }

    public LocalTime getShiftStart() {
        return shiftStart;
    }

    public LocalTime getShiftStop() {
        return shiftStop;
    }

    public int getLunchDuration() {
        return LunchDuration;
    }

    public int getShiftDuration() {
        return ShiftDuration;
    }

    @Override
     public String toString(){
        String results = String.format("%s: %s - %s (%d minutes); Lunch: %s - %s (%d minutes)",
                getDescription(), getShiftStart().toString(), getShiftStop().toString(), getShiftDuration(),
                getLunchStart(), getLunchStop(), getLunchDuration());

        return results;
    }
}



