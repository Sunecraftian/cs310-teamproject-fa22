package edu.jsu.mcis.cs310.tas_fa22.dao;

import edu.jsu.mcis.cs310.tas_fa22.Badge;
import edu.jsu.mcis.cs310.tas_fa22.EventType;
import edu.jsu.mcis.cs310.tas_fa22.Punch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

public class PunchDAO {

    private static final String QUERY_FIND = "SELECT * FROM event WHERE id = ?";
    private static final String QUERY_CREATE = "INSERT INTO event (terminalid, badgeid, timestamp, eventtypeid) VALUES (?, ?, ?, ?)";
    private static final String QUERY_LIST = "SELECT * FROM event WHERE badgeid = ?";

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
                        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

                        int terminalid = rs.getInt("terminalid");
                        Badge badge = badgeDAO.find(rs.getString("badgeid"));
                        LocalDateTime originaltimestamp = LocalDateTime.parse(rs.getString("timestamp"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        EventType punchtype = EventType.values()[rs.getInt("eventtypeid")];

                        punch = new Punch(id, terminalid, badge, originaltimestamp, punchtype);
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
        ResultSet keys;
        int id = 0;
        int update = 0;

        EmployeeDAO employeeDAO = new EmployeeDAO(daoFactory);

        int punchTerminalid = punch.getTerminalid();
        int departmentTerminalid = employeeDAO.find(punch.getBadge()).getDepartment().getTerminal_id();

        if (punchTerminalid == departmentTerminalid) {
            try {


                Connection conn = daoFactory.getConnection();

                if (conn.isValid(0)) {
                    ps = conn.prepareStatement(QUERY_CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, punch.getTerminalid()); // terminalid
                    ps.setString(2, punch.getBadge().getId()); // badgeid
                    ps.setString(3, punch.getOriginaltimestamp().toString()); // timestamp
                    ps.setInt(4, punch.getPunchtype().ordinal()); // eventtype

                    update = ps.executeUpdate();

                    if (update == 1) {
                        keys = ps.getGeneratedKeys();
                        if (keys.next()) {
                            id = keys.getInt(1);
                        }
                    }
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
        }

        return id;
    }

    public ArrayList<Punch> list(Badge badge, LocalDate localDate) {
        ArrayList<Punch> punches = new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_LIST);
                ps.setString(1, badge.getId());

                boolean hasresults = ps.execute();

                if (hasresults) {
                    rs = ps.getResultSet();

                    while (rs.next()) {
                        LocalDateTime originaltimestamp = LocalDateTime.parse(rs.getString("timestamp"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        LocalDate date = originaltimestamp.toLocalDate();

                        if (date.equals(localDate)) {
                            int id = rs.getInt("id");
                            int terminalid = rs.getInt("terminalid");
                            EventType punchtype = EventType.values()[rs.getInt("eventtypeid")];

                            Punch punch = new Punch(id, terminalid, badge, originaltimestamp, punchtype);
                            punches.add(punch);
                        }

                        punches.sort(new sortDates());
                    }
                }
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
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
        }
        return punches;
    }
}

class sortDates implements Comparator<Punch> {

    @Override
    public int compare(Punch p1, Punch p2) {
        return p1.getOriginaltimestamp().compareTo(p2.getOriginaltimestamp());
    }
}