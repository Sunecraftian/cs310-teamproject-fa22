package edu.jsu.mcis.cs310.tas_fa22.dao;

import edu.jsu.mcis.cs310.tas_fa22.Badge;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DepartmentDAO {
    private static final String QUERY = "SELECT * FROM department WHERE id = ?";

    private final DAOFactory daoFactory;

    public DepartmentDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }


}
