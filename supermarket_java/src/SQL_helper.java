import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class SQL_helper {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    private static String url = "jdbc:mysql://localhost:3306/supermarket_logistics";
    private static String user = "root", pass = "";

    public void readDataBase() throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager.getConnection(url, user, pass);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();

            // Result set get the result of the SQL query
            resultSet = statement.executeQuery("select * from date_here_1");

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                System.out.println(id + " " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeData(String shopper_info) throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, user, pass);
            String query = " insert into date_here_1 (Time_entered, Time_spent, Status, Senior)"
                    + " values (?, ?, ?, ?)";

            String[] values = shopper_info.split(",");

            PreparedStatement statement = connect.prepareStatement(query);
            statement.setDouble(1, Double.parseDouble(values[0]));
            statement.setDouble(2, Double.parseDouble(values[1]));
            statement.setString(3, values[2]);
            statement.setBoolean(4, Boolean.parseBoolean(values[3]));

            statement.execute();
            connect.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}