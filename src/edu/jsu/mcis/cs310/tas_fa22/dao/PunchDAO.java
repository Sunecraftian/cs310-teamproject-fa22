package edu.jsu.mcis.cs310.tas_fa22.dao;

import edu.jsu.mcis.cs310.tas_fa22.EventType;
import edu.jsu.mcis.cs310.tas_fa22.Punch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class PunchDAO {

    private static final String QUERY_FIND   = "SELECT * FROM event WHERE id = ?";
    private static final String QUERY_CREATE = "INSERT INTO event VALUES (?, ?, ?, ?, ?)";

    private final DAOFactory daoFactory;

    PunchDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    // Retrieve New Punch \\
    public Punch find(int id) {

        Punch punch = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, id);

                boolean hasresults = ps.execute();

                if (hasresults) {
                    rs = ps.getResultSet();

                    while (rs.next()) {

                        int terminalid = rs.getInt("terminalid");
                        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
                        LocalDateTime originaltimestamp = LocalDateTime.parse(rs.getString("timestamp"));
                        EventType punchtype = EventType.values()[rs.getInt("eventtypeid")];
                        punch = new Punch(id, terminalid, badgeDAO.find(String.valueOf(id)), originaltimestamp, punchtype);
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
        return punch;
    }


    // Insert Punch Into Database \\
    public int create(Punch punch) {

        PreparedStatement ps = null;
        int update = 0;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_CREATE);
                ps.setInt(1, punch.getId()); // id
                ps.setInt(2, punch.getTerminalid()); // terminalid
                ps.setString(3, punch.getBadge().getId()); // badgeid
                ps.setString(4, punch.getOriginaltimestamp().toString()); // timestamp
                ps.setInt(5, punch.getPunchtype().ordinal()); // eventtype

                update = ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
        }

        return update;
    }
}
