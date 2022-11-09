package edu.jsu.mcis.cs310.tas_fa22.dao;

import edu.jsu.mcis.cs310.tas_fa22.Absenteeism;
import edu.jsu.mcis.cs310.tas_fa22.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AbsenteeismDAO {

    private static final String QUERY_FIND = "SELECT * FROM absenteeism WHERE employeeid = ? AND payperiod = ?";
    private static final String QUERY_CREATE = "INSERT INTO absenteeism (employeeid, payperiod, percentage) VALUES (?, ?, ?)";

    private final DAOFactory daoFactory;

    public AbsenteeismDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Absenteeism find(Employee employee, LocalDate payperiod) {
        Absenteeism absenteeism = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();
            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, employee.getId());
                ps.setString(2, payperiod.toString());

                boolean hasResults = ps.execute();

                if (hasResults) {
                    rs = ps.getResultSet();
                    while (rs.next()) {
                        Double percentage = rs.getDouble("percentage");

                        absenteeism = new Absenteeism(employee, payperiod, percentage);
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
        return absenteeism;
    }

    public int create(Absenteeism absenteeism) {
        PreparedStatement ps = null;
        int update = 0;

        try {
            Connection conn = daoFactory.getConnection();
            if(conn.isValid(0)){
                ps = conn.prepareStatement(QUERY_CREATE);
                ps.setInt(1, absenteeism.getEmployee().getId());
                ps.setString(2, absenteeism.getPayperiod().toString());
                ps.setDouble(3, absenteeism.getPercentage());

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