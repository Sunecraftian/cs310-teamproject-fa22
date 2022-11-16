package edu.jsu.mcis.cs310.tas_fa22.dao;

import edu.jsu.mcis.cs310.tas_fa22.Punch;
import edu.jsu.mcis.cs310.tas_fa22.PunchAdjustmentType;
import edu.jsu.mcis.cs310.tas_fa22.Shift;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public final class DAOUtility {

    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift) {
        int m;
        int minutes = 0;

        try {
            boolean hadLunch = false;
            for (int i = 0; i < dailypunchlist.size(); i += 2) {
                Punch punch1 = dailypunchlist.get(i);
                Punch punch2 = dailypunchlist.get(i + 1);

                LocalTime time1 = punch1.getAdjustedtimestamp().toLocalTime();
                LocalTime time2 = punch2.getAdjustedtimestamp().toLocalTime();

                if (punch1.getAdjustmentType() == PunchAdjustmentType.LUNCH_START ||
                    punch2.getAdjustmentType() == PunchAdjustmentType.LUNCH_START ||
                    punch1.getAdjustmentType() == PunchAdjustmentType.LUNCH_STOP ||
                    punch2.getAdjustmentType() == PunchAdjustmentType.LUNCH_STOP) hadLunch = true;

                m = (int) time1.until(time2, ChronoUnit.MINUTES);

                if (!hadLunch && m > shift.getLunchThreshold()) {
                    m -= shift.getLunchDuration();
                }

                minutes += m;
            }


        } catch (IndexOutOfBoundsException e) {
            // this may break things
            return minutes;
        }

        return minutes;
    }

    public static double calculateAbsenteeism(ArrayList<Punch> punchlist, Shift s) {
        double percentage;
        float min = calculateTotalMinutes(punchlist, s);
        float worktime = (s.getShiftDuration() - s.getLunchDuration()) * 5;

        percentage = ((worktime - min) / worktime);

        return percentage;
    }

    public static String getPunchListAsJSON(ArrayList<Punch> dailypunchlist) {
        String jsonString;

        JSONArray json = new JSONArray();

        for (Punch punch : dailypunchlist) {
            JSONObject object = new JSONObject();

            object.put("originaltimestamp", punch.getOriginaltimestamp().format(DateTimeFormatter.ofPattern("E MM/dd/yyyy HH:mm:ss")).toUpperCase());
            object.put("badgeid", punch.getBadge().getId());
            object.put("adjustedtimestamp", punch.getAdjustedtimestamp().format(DateTimeFormatter.ofPattern("E MM/dd/yyyy HH:mm:ss")).toUpperCase());
            object.put("adjustmenttype", punch.getAdjustmentType().toString());
            object.put("terminalid", String.valueOf(punch.getTerminalid()));
            object.put("id", String.valueOf(punch.getId()));
            object.put("punchtype", punch.getPunchtype().toString());

            json.add(object);
        }

        jsonString = JSONValue.toJSONString(json);
        return jsonString;
    }


    public static String getPunchListPlusTotalsAsJSON(ArrayList<Punch> punchlist, Shift s){
        String jsonString;
        String absenteeism = String.format("%.2f%%", calculateAbsenteeism(punchlist, s)*100);
        String totalminutes = String.valueOf(calculateTotalMinutes(punchlist, s));
        JSONObject json = new JSONObject();
        JSONArray punches = new JSONArray();

        for (Punch punch : punchlist) {
            JSONObject object = new JSONObject();

            object.put("originaltimestamp", punch.getOriginaltimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toUpperCase());
            object.put("badgeid", punch.getBadge().getId());
            object.put("adjustedtimestamp", punch.getAdjustedtimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toUpperCase());
            object.put("adjustmenttype", punch.getAdjustmentType().toString());
            object.put("terminalid", String.valueOf(punch.getTerminalid()));
            object.put("id", String.valueOf(punch.getId()));
            object.put("punchtype", punch.getPunchtype().toString());

            punches.add(object);
        }

        json.put("absenteeism", absenteeism);
        json.put("totalminutes", totalminutes);
        json.put("punchlist", punches);

        jsonString = JSONValue.toJSONString(json);
        return jsonString;
    }
}
