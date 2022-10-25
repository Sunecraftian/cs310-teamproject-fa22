package edu.jsu.mcis.cs310.tas_fa22;

//import java.time.LocalTime;
import java.util.HashMap;
import java.time.*;
public class Shift {

    //private final int lunchduration, shiftduration;
    private final int graceperiod, roundinterval, dock, id, lunchthresh, lunchduration,
    shiftduration;

    private final String description;
    private final LocalTime shiftstart, shiftstop, lunchstart, lunchstop;
    //HashMap<String, String> map = new HashMap<String, String>();
    //CONSTRUCTOR
    public Shift(HashMap<String, String> map){
        this.id = Integer.parseInt(map.get("id"));
        this.description = map.get("description");
        this.roundinterval = Integer.parseInt(map.get("roundinterval"));
        this.graceperiod = Integer.parseInt(map.get("graceperiod"));
        this.shiftstart = LocalTime.parse(map.get("shiftstart"));
        this.shiftstop = LocalTime.parse(map.get("shiftstop"));
        this.lunchstart = LocalTime.parse(map.get("lunchstart"));
        this.lunchstop = LocalTime.parse(map.get("lunchstop"));
        this.lunchthresh = Integer.parseInt(map.get("lunchthreshold"));
        this.dock = Integer.parseInt(map.get("dockpenalty"));
        this.lunchduration = (int)Duration.between(this.lunchstart, this.lunchstop).toMinutes();
        this.shiftduration = (int)Duration.between(this.shiftstart, this.shiftstop).toMinutes();
    }

    public int getid() {
        return id;
    }

    public int getroundinterval() {
        return roundinterval;
    }

    public int getgraceperiod() {
        return graceperiod;
    }

    public int getdock() {
        return dock;
    }

    public LocalTime getlunchstart() {
        return lunchstart;
    }

    public int getlunchthresh() {
        return lunchthresh;
    }

    public LocalTime getlunchstop() {
        return lunchstop;
    }

    public String getdescription() {
        return description;
    }

    public LocalTime getshiftstart() {
        return shiftstart;
    }

    public LocalTime getshiftstop() {
        return shiftstop;
    }

    public int getlunchduration() {
        return lunchduration;
    }

    public int getshiftduration() {
        return shiftduration;
    }

    @Override
     public String toString(){
        String results = String.format("%s: %s - %s (%d minutes); Lunch: %s - %s (%d minutes)",
                getdescription(), getshiftstart().toString(), getshiftstop().toString(), getshiftduration(),
                getlunchstart(), getlunchstop(), getlunchduration());

        return results;
    }
}



