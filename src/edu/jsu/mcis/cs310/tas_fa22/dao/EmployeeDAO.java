package edu.jsu.mcis.cs310.tas_fa22.dao;

import edu.jsu.mcis.cs310.tas_fa22.Badge;
import edu.jsu.mcis.cs310.tas_fa22.Department;
import edu.jsu.mcis.cs310.tas_fa22.Employee;
import edu.jsu.mcis.cs310.tas_fa22.Shift;

import java.beans.beancontext.BeanContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class EmployeeDAO {

    private static final String QUERY_FIND_ID = "SELECT * FROM employee WHERE id = ?";
    private static final String QUERY_FIND_BADGE = "SELECT * FROM employee WHERE badgeid = ?";

    private final DAOFactory daoFactory;

    EmployeeDAO(DAOFactory daoFactory) {

        this.daoFactory = daoFactory;


    }

    public Employee find(int id) {

        Employee employee = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_FIND_ID);
                ps.setInt(1, id);

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {
                        BadgeDAO badgeDAO = new BadgeDAO(daoFactory);
                        ShiftDAO shiftDAO = new ShiftDAO(daoFactory);
                        DepartmentDAO departmentDAO = new DepartmentDAO(daoFactory);

                        String firstname = rs.getString("firstname");
                        String middlename = rs.getString("middlename");
                        String lastname = rs.getString("lastname");
                        LocalDateTime active = LocalDateTime.parse(rs.getString("active"));
                        Badge badge = badgeDAO.find(rs.getString("badgeid"));
                        Department department = departmentDAO.find(rs.getInt("departmentid"));
                        Shift shift = shiftDAO.find(badge);

                        employee = new Employee(id, firstname, middlename, lastname, active, badge, department, shift);
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
                    rs.close();
                } catch (SQLException e) {
                    throw  new DAOException(e.getMessage());
                }
            }


        }
        return employee;
    }

    public Employee find(Badge badge) {
        Employee employee = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_FIND_BADGE);
                ps.setString(1, badge.getId());

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {
                        ShiftDAO shiftDAO = new ShiftDAO(daoFactory);
                        DepartmentDAO departmentDAO = new DepartmentDAO(daoFactory);

                        int id = rs.getInt("id");
                        String firstname = rs.getString("firstname");
                        String middlename = rs.getString("middlename");
                        String lastname = rs.getString("lastname");
                        LocalDateTime active = LocalDateTime.parse(rs.getString("active"));
                        Department department = departmentDAO.find(rs.getInt("departmentid"));
                        Shift shift = shiftDAO.find(rs.getInt("shiftid"));

                        employee = new Employee(id, firstname, middlename, lastname, active, badge, department, shift);
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
                    rs.close();
                } catch (SQLException e) {
                    throw  new DAOException(e.getMessage());
                }
            }


        }
        return employee;
    }
}
