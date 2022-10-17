package edu.jsu.mcis.cs310.tas_fa22.dao;

import edu.jsu.mcis.cs310.tas_fa22.Badge;
import edu.jsu.mcis.cs310.tas_fa22.*;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.sql.*;
public class ShiftDAO {
    private static final String QUERY = "SELECT * FROM shift WHERE id = ?";
    private static final String QUERY_BADGE = "SELECT * FROM  employee WHERE badgeid = ?";
    private final DAOFactory daoFactory;
    private HashMap<String, String> map = new HashMap<>();

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
                ps = conn.prepareStatement(QUERY);
                ps.setInt(1, id);

                boolean hasResults = ps.execute();

                if (hasResults) {
                    rs = ps.getResultSet();
                    while (rs.next()) {
                        map.put("id", rs.getString("id"));
                        map.put("description", rs.getString("description"));
                        map.put("shiftStart", rs.getString("shiftStart"));
                        map.put("shiftStop", rs.getString("shiftStop"));
                        map.put("RoundInterval", rs.getString("roundInterval"));
                        map.put("GracePeriod", rs.getString("graceperiod"));
                        map.put("lunchStart", rs.getString("lunchstart"));
                        map.put("lunchStop", rs.getString("lunchstop"));
                        shift = new Shift(map);
                    }
                }

            }
            }catch(SQLException e){
            throw new DAOException(e.getMessage());
        }
        finally {

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
                ps = conn.prepareStatement(QUERY);
                ps.setInt(1, Integer.parseInt(badge.getId()));

                boolean hasResults = ps.execute();

                if (hasResults) {
                    rs = ps.getResultSet();
                    while (rs.next()) {
                        map.put("id", rs.getString("id"));
                        map.put("description", rs.getString("description"));
                        map.put("shiftStart", rs.getString("shiftStart"));
                        map.put("shiftStop", rs.getString("shiftStop"));
                        map.put("RoundInterval", rs.getString("roundInterval"));
                        map.put("GracePeriod", rs.getString("graceperiod"));
                        map.put("lunchStart", rs.getString("lunchstart"));
                        map.put("lunchStop", rs.getString("lunchstop"));
                        shift = new Shift(map);
                    }
                }

            }
        }catch(SQLException e){
            throw new DAOException(e.getMessage());
        }
        finally {

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
}

