package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private static Repository INSTANCE;
    private static final String url = "jdbc:mysql://localhost/equations";
    private static final String username = "root";
    private static final String password = "100304";
    private Connection connection;
    private Repository(Connection connection) {
        this.connection = connection;
    }

    public static Repository getInstance() throws SQLException {
        if(INSTANCE == null) {
            INSTANCE = new Repository(DriverManager.getConnection(url, username, password));
        }

        return INSTANCE;
    }

    public void saveEquation(String equation) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO equations (equation) VALUES (?)");
        preparedStatement.setString(1, equation);
        preparedStatement.executeUpdate();
    }
    public void addSolution(String equation, double x) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE equations SET x = ? WHERE equation = ?;");
        preparedStatement.setDouble(1, x);
        preparedStatement.setString(2, equation);
        preparedStatement.execute();
    }
    public List<Equation> findAllBySolution(double x) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM equations WHERE x = ?;");
        preparedStatement.setDouble(1, x);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Equation> list = new ArrayList<>();
        while (resultSet.next()){
            list.add(new Equation(resultSet.getInt("id"), resultSet.getString("equation"), resultSet.getDouble("x")));
        }
        return list;

    }
}
