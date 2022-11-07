package edu.jsu.mcis.cs310.tas_fa22.dao;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import edu.jsu.mcis.cs310.tas_fa22.Punch;
import edu.jsu.mcis.cs310.tas_fa22.PunchAdjustmentType;
import edu.jsu.mcis.cs310.tas_fa22.Shift;
import org.json.simple.*;

public final class DAOUtility {

    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift) {
        int minutes = 0;

        for (int i = 0; i < dailypunchlist.size(); i += 2) {
            Punch punch1 = dailypunchlist.get(i);
            Punch punch2 = dailypunchlist.get(i+1);

            LocalTime time1 = punch1.getAdjustedtimestamp().toLocalTime();
            LocalTime time2 = punch2.getAdjustedtimestamp().toLocalTime();

            minutes += (int) time1.until(time2, ChronoUnit.MINUTES); // Makes Shift1Weekday, Shift1Weekend
        }

        minutes -= shift.getlunchduration(); // Makes Shift2Weekday work

        return minutes;
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
}
