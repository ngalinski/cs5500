package Writers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL_helper {
    private Connection connect = null;
    private Statement statement = null;

    // Might need to change these depending on the user
    private static final String url = "jdbc:mysql://localhost:3306/supermarket_logistics";
    private static final String user = "root";
    private static final String pass = "";

    /**
     * This method is for reading the database, just disregard
     */
    public void readDataBase() {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager.getConnection(url, user, pass);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();

            // Result set get the result of the SQL query
            ResultSet resultSet = statement.executeQuery("SELECT * FROM date_here");

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                System.out.println(id + " " + name);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method writes a new table for entering shopper data into
     * @param name String value of date
     */
    public void writeTable(String name) {
        try {

            // This connects the code to the sql server
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, user, pass);

            statement = connect.createStatement();

            /*
            // This gets rid of an existing table if it has the same name
            String sql = "DROP TABLE IF EXISTS `" + date + "`";
            statement.executeUpdate(sql);
             */

            // This creates the table with what we want inside
            String sql = "CREATE TABLE `" + name +
                    "` (ID INT NOT NULL AUTO_INCREMENT, " +
                    "Time_entered DOUBLE DEFAULT 0 not NULL, " +
                    " Time_spent DOUBLE DEFAULT 0 not NULL, " +
                    " Rush TINYTEXT not NULL, " +
                    " Senior BOOLEAN not NULL, PRIMARY KEY (ID))";

            // This pushes the sql into the mysql database server
            statement.executeUpdate(sql);
            connect.close();
            System.out.println("Created shopper table in given database...");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method writes a new table for entering parameter information
     * @param name String value of date
     */
    public void writeParametersTable(String name) {
        try {

            // This connects the code to the sql server
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, user, pass);

            statement = connect.createStatement();

            // This creates the table with what we want inside
            String sql = "CREATE TABLE `" + name +
                    "` (Parameter_names TINYTEXT not NULL, " +
                    " Parameter_values TINYTEXT not NULL)";

            // This pushes the sql into the mysql database server
            statement.executeUpdate(sql);
            connect.close();
            System.out.println("Created parameters table in given database...");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method writes parameter values for current date and parameter situation
     * @param sql_table String name of sql table
     * @param parameter_name String parameter name
     * @param parameter_value String parameter value
     */
    public void writeParameters(String sql_table, String parameter_name, String parameter_value) {
        try {
            // Connect to mysql database
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, user, pass);
            statement = connect.createStatement();

            // Connect to sql table and prepare for value entry
            // I know that there is an error here but it's only syntactical
            PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO `" + sql_table +
                    "`(Parameter_names, Parameter_values) " +
                    "VALUES (?, ?)");

            // Set up values to be pushed
            preparedStatement.setString(1, parameter_name);
            preparedStatement.setString(2, parameter_value);

            // Push values to sql server
            preparedStatement.executeUpdate();
            connect.close();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * This method writes one shopper's info into the sql table
     * @param sql_table String name of sql table
     * @param shopper_info String of shopper info to be written
     */
    public void writeData(String sql_table, String shopper_info) {
        String[] values = shopper_info.split(",");
        double time_entered = Double.parseDouble(values[0]);
        double time_spent = Double.parseDouble(values[1]);
        String rush = values[2];
        boolean senior = Boolean.parseBoolean(values[3]);

        try {
            // Connect to mysql database
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, user, pass);
            statement = connect.createStatement();

            // Connect to sql table and prepare for value entry
            // I know that there is an error here but it's only syntactical
            PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO `" + sql_table +
                    "`(Time_entered, Time_spent, Rush, Senior) " +
                    "VALUES (?, ?, ?, ?)");

            // Set up values to be pushed
            preparedStatement.setDouble(1, time_entered);
            preparedStatement.setDouble(2, time_spent);
            preparedStatement.setString(3, rush);
            preparedStatement.setBoolean(4, senior);

            // Push values to sql server
            preparedStatement.executeUpdate();
            connect.close();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}