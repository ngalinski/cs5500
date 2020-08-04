package Writers;

import Model.Shopper;
import Model.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SQL_Reader {
    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private Connection jdbcConnection;

    private String[] sql_parts = {null, null, null, null};

    public SQL_Reader(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

    protected void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(
                    jdbcURL, jdbcUsername, jdbcPassword);
        }
    }

    protected void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

    // Might need to change these depending on the user
    private static final String url = "jdbc:mysql://localhost:3306/supermarket_logistics";
    private static final String user = "root";
    private static final String pass = "";

    /**
     * This method is for reading the database, just disregard
     */
    public List<Shopper> listAllShoppers(String name, String[] parameters) throws SQLException {
        List<Shopper> listShopper = new ArrayList<>();

        String sql = "SELECT * FROM " + name;

        String part1 = time_entered(parameters[0], parameters[1]);
        String part2 = time_spent(parameters[2], parameters[3]);
        String part3 = rush(parameters[4]);
        String part4 = senior(parameters[5]);

        String joined = Stream.of(part1, part2, part3, part4)
                .filter(s-> s != null && !s.isEmpty())
                .collect(Collectors.joining(" AND "));

        if(!joined.isEmpty()){
            sql += " WHERE " + joined;
        }

        connect();

        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            double time_entered = resultSet.getDouble(2);
            double time_spent = resultSet.getDouble(3);
            String rush = resultSet.getString(4);
            boolean senior = resultSet.getBoolean(5);

            Shopper shopper = new Shopper(id, time_entered, time_spent, rush, senior);
            listShopper.add(shopper);
        }

        resultSet.close();
        statement.close();
        disconnect();

        return listShopper;
    }

    public List<Table> listTables() throws SQLException {
        List<Table> tableNames = new ArrayList<>();

        String sql = "SHOW TABLES";

        connect();

        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            Table table = new Table(resultSet.getString(1));
            tableNames.add(table);
        }

        resultSet.close();
        statement.close();
        disconnect();

        return tableNames;
    }

    private String time_entered(String before, String after){
        String sql;
        if(before != null && after != null){
            sql = "time_entered BETWEEN " + after + " AND " + before;
        }

        else if(after != null){
            sql = "time_entered > " + after;
        }

        else if(before != null){
            sql = "time_entered < " + before;
        }

        else{
            sql = "";
        }

        return sql;
    }

    private String time_spent(String min, String max){
        String sql;
        if(min != null && max != null){
            sql = "time_spent BETWEEN " + min + " AND " + max;
        }
        else if(min != null){
            sql = "time_spent > " + min;
        }
        else if(max != null){
            sql = "time_spent < " + max;
        }
        else{
            sql = "";
        }
        return sql;
    }

    private String rush(String rush){
        String sql;
        if(rush == null){
            return "";
        } else if ("lunch".equals(rush)) {
            sql = "rush='Lunch'";
        } else if ("dinner".equals(rush)) {
            sql = "rush='Dinner'";
        } else if ("senior".equals(rush)) {
            sql = "rush='Senior'";
        } else if ("normal".equals(rush)) {
            sql = "rush='Normal'";
        } else {
            sql = "";
        }
        return sql;
    }

    private String senior(String senior){
        String sql;
        if(senior == null){
            return "";
        } else if(senior.equals("y")){
            sql = "senior=1";
        } else if(senior.equals("n")){
            sql = "senior=0";
        } else{
            sql = "";
        }
        return sql;
    }
}
