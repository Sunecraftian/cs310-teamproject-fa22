package edu.jsu.mcis.cs310.tas_fa22;

import java.util.HashMap;
import java.time.*;
public class Shift {

    private final int id;

    private final String description;
    private final DailySchedule defaultschedule;

    private HashMap<Integer, DailySchedule> dailyScheduleList = new HashMap<>();

    public Shift(int id, String description, DailySchedule defaultschedule){
        this.id = id;
        this.description = description;
        this.defaultschedule = defaultschedule;

        this.dailyScheduleList.put(DayOfWeek.MONDAY.getValue(), getDefaultSchedule());
        this.dailyScheduleList.put(DayOfWeek.TUESDAY.getValue(), getDefaultSchedule());
        this.dailyScheduleList.put(DayOfWeek.WEDNESDAY.getValue(), getDefaultSchedule());
        this.dailyScheduleList.put(DayOfWeek.THURSDAY.getValue(), getDefaultSchedule());
        this.dailyScheduleList.put(DayOfWeek.FRIDAY.getValue(), getDefaultSchedule());
    }

    public int getId() { return id; }
    public String getDescription() { return description; }

    public DailySchedule getDefaultSchedule() { return defaultschedule; }

//    public void setDefaultSchedule(DailySchedule defaultschedule) { this.defaultschedule = defaultschedule; }

    public DailySchedule getDailySchedule(DayOfWeek dayOfWeek) { return dailyScheduleList.get(dayOfWeek.getValue()); }

    public void setDailySchedule(DayOfWeek dayOfWeek, DailySchedule dailySchedule) { this.dailyScheduleList.replace(dayOfWeek.getValue(), dailySchedule); }


    public int getRoundInterval() { return defaultschedule.getRoundInterval(); }

    public int getGracePeriod() { return defaultschedule.getGracePeriod(); }

    public int getDockPenalty() { return defaultschedule.getDockPenalty(); }

    public LocalTime getLunchStart() { return defaultschedule.getLunchStart(); }

    public int getLunchThreshold() { return defaultschedule.getLunchThreshhold(); }

    public LocalTime getLunchStop() { return defaultschedule.getLunchStop(); }

    public LocalTime getShiftStart() { return defaultschedule.getShiftStart(); }

    public LocalTime getShiftStop() { return defaultschedule.getShiftStop(); }

    public int getLunchDuration() { return defaultschedule.getLunchDuration(); }

    public int getShiftDuration() { return defaultschedule.getShiftDuration(); }

    @Override
     public String toString(){
        return String.format("%s: %s - %s (%d minutes); Lunch: %s - %s (%d minutes)",
                getDescription(), getShiftStart().toString(), getShiftStop().toString(), getShiftDuration(),
                getLunchStart(), getLunchStop(), getLunchDuration());
    }
}



