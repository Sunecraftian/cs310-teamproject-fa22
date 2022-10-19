package edu.jsu.mcis.cs310.tas_fa22.dao;

import edu.jsu.mcis.cs310.tas_fa22.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentDAO {
    private static final String QUERY = "SELECT * FROM department WHERE id = ?";

    private final DAOFactory daoFactory;

    public DepartmentDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Department find(int id){
        Department department = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)){
               ps = conn.prepareStatement(QUERY);
               ps.setInt(1, id);

               boolean hasResults = ps.execute();

               if(hasResults){
                   rs = ps.getResultSet();
                   while (rs.next()){
                       String description = rs.getString("department");
                       Integer terminal_id = rs.getInt("terminalid");
                       department = new Department(id, description, terminal_id);
                   }
               }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return department;

    }

}
