package edu.jsu.mcis.cs310.tas_fa22.dao;

import edu.jsu.mcis.cs310.tas_fa22.Badge;
import edu.jsu.mcis.cs310.tas_fa22.*;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.sql.*;
public class ShiftDAO {
    private static final String QUERY_ID = "SELECT * FROM shift WHERE id = ?";
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
                ps = conn.prepareStatement(QUERY_ID);
                ps.setInt(1, id);

                boolean hasResults = ps.execute();

                if (hasResults) {
                    rs = ps.getResultSet();
                    while (rs.next()) {
                        map.put("id", rs.getString("id"));
                        map.put("description", rs.getString("description"));
                        map.put("shiftstart", rs.getString("shiftstart"));
                        map.put("shiftstop", rs.getString("shiftstop"));
                        map.put("roundinterval", rs.getString("roundinterval"));
                        map.put("graceperiod", rs.getString("graceperiod"));
                        map.put("dockpenalty", rs.getString("dockpenalty"));
                        map.put("lunchstart", rs.getString("lunchstart"));
                        map.put("lunchstop", rs.getString("lunchstop"));
                        map.put("lunchthreshold", rs.getString("lunchthreshold"));
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
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                ps1 = conn.prepareStatement(QUERY_BADGE);
                ps1.setString(1, badge.getId());

                boolean hasResults1 = ps1.execute();

                if (hasResults1) {
                    rs1 = ps1.getResultSet();

                    while (rs1.next()) {
                        int shiftid = rs1.getInt("shiftid");

                        ps2 = conn.prepareStatement(QUERY_ID);
                        ps2.setInt(1, shiftid);

                        boolean hasResults2 = ps2.execute();

                        if (hasResults2) {
                            rs2 = ps2.getResultSet();
                            while (rs2.next()) {
                                map.put("id", rs2.getString("id"));
                                map.put("description", rs2.getString("description"));
                                map.put("shiftstart", rs2.getString("shiftstart"));
                                map.put("shiftstop", rs2.getString("shiftstop"));
                                map.put("roundinterval", rs2.getString("roundinterval"));
                                map.put("graceperiod", rs2.getString("graceperiod"));
                                map.put("dockpenalty", rs2.getString("dockpenalty"));
                                map.put("lunchstart", rs2.getString("lunchstart"));
                                map.put("lunchstop", rs2.getString("lunchstop"));
                                map.put("lunchthreshold", rs2.getString("lunchthreshold"));

                                shift = new Shift(map);
                            }
                        }
                    }

                }







            }
        }catch(SQLException e){
            throw new DAOException(e.getMessage());
        }
        finally {

            if (rs1 != null) {
                try {
                    rs1.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps1 != null) {
                try {
                    ps1.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (rs2 != null) {
                try {
                    rs2.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps2 != null) {
                try {
                    ps2.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
        }
        return shift;
    }
}

