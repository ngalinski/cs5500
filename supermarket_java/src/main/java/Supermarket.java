import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * This is the encapsulating supermarket class which creates all shoppers
 */
public class Supermarket {

    private final Parameters parameters;
    private final Json_reader json_parameters = new Json_reader();

    private final Double[] STORE_HOURS = {json_parameters.getValue("STORE_OPEN"),
            json_parameters.getValue("STORE_CLOSE")};
    private final int[] AVG_SHOPPERS = {5000, 800, 1000, 1200, 900, 2500, 4000};

    public Supermarket(){
        this.parameters = new Parameters();
    }

    /**
     * Generates a single shopper variable
     * @param day_num Integer number of day
     * @param day_nice Boolean whether day is nice
     * @return Shopper variable
     */
    private Shopper generate_shopper(int day_num, boolean day_nice) {
        return new Single_shopper(day_num, day_nice, json_parameters).gen_shopper();
    }

    /**
     * Get total number of shoppers for a specific date
     * Will factor in weather for weekends
     * Will factor in holidays
     * @param date LocalDate of date
     * @param day_nice Boolean whether day nice
     * @return Int number of shoppers
     */
    private int get_num_shoppers(LocalDate date, boolean day_nice){
        return new Multipliers(date, AVG_SHOPPERS[date.getDayOfWeek().getValue() % 7],
                day_nice, json_parameters).get_num_shoppers();
    }

    /**
     * This method writes data to csv and to mysql
     * Writes shopper data for specific date
     * Connects to sql using sql helper class
     * @param date LocalDate of date
     * @param day_nice Boolean whether day nice
     * @throws Exception SQL exceptions
     */
    private void write_data(LocalDate date, boolean day_nice) throws Exception {
        int day_num = date.getDayOfWeek().getValue();
        FileWriter csvWriter = new FileWriter(date.getDayOfWeek() + ".csv");
        csvWriter.append("Time Entered (hr), Time Spend (min), Rushing, Senior\n");

        // These lines connect to mysql and create a new table for the date
        SQL_helper sql_connector = new SQL_helper();
        String table_name = date.toString() + java.time.LocalTime.now();

        // Create parameters table
        sql_connector.writeParametersTable(table_name);
        String[] parameter_names = parameters.passNames();
        String[] parameter_values = parameters.passValues();

        for(int i = 0; i < parameter_names.length; i++){
            sql_connector.writeParameters(table_name, parameter_names[i], parameter_values[i]);
        }

        // Create shopper table
        sql_connector.writeTable(table_name + parameters.getNaming());

        for(int i = 0; i < this.get_num_shoppers(date, day_nice); i++){
            Shopper shopper_here = this.generate_shopper(day_num, day_nice);

            // This line writes to mysql
            sql_connector.writeData(table_name + parameters.getNaming(), shopper_here.toString());

            csvWriter.append(shopper_here.toString());
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();
    }




    private void helper() throws Exception {
        Scanner user_month = new Scanner(System.in);
        System.out.println("Enter a month 1-12");
        int month = Integer.parseInt(user_month.nextLine());

        Scanner user_day = new Scanner(System.in);
        System.out.println("Enter a day 1-28/29/30/31");
        int day = Integer.parseInt(user_day.nextLine());

        Scanner user_year = new Scanner(System.in);
        System.out.println("Enter a year");
        int year = Integer.parseInt(user_year.nextLine());

        Scanner user_weather = new Scanner(System.in);
        System.out.println("Is the weather nice? y/n");
        boolean weather = user_weather.nextLine().equals("y");

        LocalDate date = LocalDate.of(year, month, day);

        this.write_data(date, weather);

        Scanner whether_statistics = new Scanner(System.in);
        System.out.println("Do you want a statistics csv? y/n");
        boolean statistics = whether_statistics.nextLine().equals("y");

        if(statistics){
            Stats_writer stats_writer = new Stats_writer();
            stats_writer.generate_statistics(date);
        }
    }

    public static void main(String[] args) throws Exception {
        Supermarket supermarket = new Supermarket();
        supermarket.helper();
        System.out.println("Done creating csv files.");
        System.out.println("Done updating mysql.");
    }
}
