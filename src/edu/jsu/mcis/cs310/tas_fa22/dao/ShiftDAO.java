package edu.jsu.mcis.cs310.tas_fa22.dao;

//import edu.jsu.mcis.cs310.tas_fa22.Badge;
import edu.jsu.mcis.cs310.tas_fa22.*;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.sql.*;
public class ShiftDAO {
    private static final String QUERY = "SELECT * FROM shift WHERE id = ?";
    private final DAOFactory daoFactory;
    private HashMap<String, String> map = new HashMap<>();

    public ShiftDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Shift find(Badge Id) {
        Shift shift = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();
            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY);


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
