package edu.jsu.mcis.cs310.tas_fa22.dao;

import edu.jsu.mcis.cs310.tas_fa22.Badge;
import edu.jsu.mcis.cs310.tas_fa22.DailySchedule;
import edu.jsu.mcis.cs310.tas_fa22.Shift;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

public class ShiftDAO {
    private static final String QUERY_ID = "SELECT * FROM shift WHERE id = ?";
    private static final String QUERY_BADGE = "SELECT * FROM employee WHERE badgeid = ?";
    private static final String QUERY_DAILYSCHEDULE = "SELECT * FROM dailyschedule WHERE id = ?";
    private final DAOFactory daoFactory;

    public ShiftDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Shift find(int id) {
        Shift shift = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();
            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_ID);
                ps.setInt(1, id);

                boolean hasResults = ps.execute();

                if (hasResults) {
                    rs = ps.getResultSet();
                    while (rs.next()) {
                        String description = rs.getString("description");
                        DailySchedule defaultschedule = findDailySchedule(rs.getInt("dailyscheduleid"));
                        shift = new Shift(id, description, defaultschedule);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
        }
        return shift;
    }

    public Shift find(Badge badge) {
        Shift shift = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();
            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_BADGE);
                ps.setString(1, badge.getId());

                boolean hasResults = ps.execute();

                if (hasResults) {
                    rs = ps.getResultSet();

                    while (rs.next()) {
                        int shiftid = rs.getInt("shiftid");

                        shift = find(shiftid);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
        }
        return shift;
    }

    public Shift find(Badge badge, LocalDate date) {
//        Shift shift = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//
//        try {
//            shift = find(badge);
////            shift.
////            Connection conn = daoFactory.getConnection();
////            if (conn.isValid(0)) {
////                ps = conn.prepareStatement(QUERY_BADGE);
////                ps.setString(1, badge.getId());
////
////                boolean hasResults = ps.execute();
////
////                if (hasResults) {
////                    rs = ps.getResultSet();
////
////                    while (rs.next()) {
////                        int shiftid = rs.getInt("shiftid");
////
////                        shift = new Shift(shiftid, );
////                    }
////                }
////            }
//        } catch (SQLException e) {
//            throw new DAOException(e.getMessage());
//        } finally {
//            if (rs != null) {
//                try {
//                    rs.close();
//                } catch (SQLException e) {
//                    throw new DAOException(e.getMessage());
//                }
//            }
//            if (ps != null) {
//                try {
//                    ps.close();
//                } catch (SQLException e) {
//                    throw new DAOException(e.getMessage());
//                }
//            }
//        }
        return find(badge);
    }



    private DailySchedule findDailySchedule(int dailyscheduleid) {
        DailySchedule dailyschedule = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        HashMap<String, String> map = new HashMap<>();
        try {
            Connection conn = daoFactory.getConnection();
            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_DAILYSCHEDULE);
                ps.setInt(1, dailyscheduleid);

                boolean hasResults = ps.execute();

                if (hasResults) {
                    rs = ps.getResultSet();

                    while (rs.next()) {
                        map.put("id", rs.getString("id"));
                        map.put("shiftstart", rs.getString("shiftstart"));
                        map.put("shiftstop", rs.getString("shiftstop"));
                        map.put("roundinterval", rs.getString("roundinterval"));
                        map.put("graceperiod", rs.getString("graceperiod"));
                        map.put("dockpenalty", rs.getString("dockpenalty"));
                        map.put("lunchstart", rs.getString("lunchstart"));
                        map.put("lunchstop", rs.getString("lunchstop"));
                        map.put("lunchthreshold", rs.getString("lunchthreshold"));
                        dailyschedule = new DailySchedule(map);
                    }

                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
        }
        return dailyschedule;
    }
}

