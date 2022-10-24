package edu.jsu.mcis.cs310.tas_fa22.dao;

import edu.jsu.mcis.cs310.tas_fa22.*;
import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import org.json.simple.*;

/**
 * 
 * Utility class for DAOs.  This is a final, non-constructable class containing
 * common DAO logic and other repeated and/or standardized code, refactored into
 * individual static methods.
 * 
 */
public final class DAOUtility {
   /* private static Object ArrayList;
    private static String punchData;
    Punch punch;

    Hashmap<>
    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift) {

    }



    public static String getPunchListAsJSON(ArrayList<Punch> dailypunchlist){
        /* Create ArrayList Object */
        ArrayList<HashMap<string, string = "" >> jsonData;

        /* Create HashMap Object (one for every Punch!) */
        HashMap<string, string = "" > punchData = new HashMap<>();

        /* Add Punch Data to HashMap */
        punchData.put("id", String.valueOf(punch.getId));
        punchData.put("badgeid", String.valueOf(punch.getBadgeid));
        punchData.put("terminalid", String.valueOf(punch.getTerminalid));
        /* ... continue in the same way with the remaining Punch data ...*/
        
        /* Append HashMap to ArrayList */
        jsonData.add(punchData);

        String json = JSONValue.toJSONString(jsonData);
    }
}
