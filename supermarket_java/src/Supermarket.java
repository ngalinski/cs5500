import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * This is the encapsulating supermarket class which creates all shoppers
 */
public class Supermarket {

    private Parameters parameters;

    private final int[] STORE_HOURS = {0, 15};
    private final int[] AVG_SHOPPERS = {5000, 800, 1000, 1200, 900, 2500, 4000};

    // Time spent
    private final int[][] AVG_SHOPPER_WEEKDAY = {{6, 25}, {25, 75}};
    private final int[] AVG_SHOPPER_WEEKEND = {50, 70};
    private final int[] WEEKEND_SHORT_TIME = {16, 24};
    private final int[] SENIOR_TIME_SPENT = {45, 60};
    private final int[] LUNCH_RUSH = {6, 14};
    private final int[] DINNER_RUSH = {16, 24};

    public Supermarket(){
        this.parameters = new Parameters();
    }

    /**
     * Sets shopper time for a shopper on a weekday
     * Not rushing, not senior
     * @return time shopper spends in store as double
     */
    private double set_weekday_normal() {
        if(Math.random() < 0.5) {
            return AVG_SHOPPER_WEEKDAY[0][0] + Math.random() *
                    (AVG_SHOPPER_WEEKDAY[0][1] - AVG_SHOPPER_WEEKDAY[0][0]);
        }
        else{
            return AVG_SHOPPER_WEEKDAY[1][0] + Math.random() *
                    (AVG_SHOPPER_WEEKDAY[1][1] - AVG_SHOPPER_WEEKDAY[1][0]);
        }
    }

    /**
     * Sets shopper time for senior shopper
     * @return time senior spends in store as double
     */
    private double set_senior_time_spent() {
        return SENIOR_TIME_SPENT[0] + Math.random() * (SENIOR_TIME_SPENT[1] - SENIOR_TIME_SPENT[0]);
    }


    /**
     * Sets shopper time for lunch rush shopper
     * @return time lunch rush shopper spends in store as double
     */
    private double set_lunch_time_spent() {
        return LUNCH_RUSH[0] + Math.random() * (LUNCH_RUSH[1] - LUNCH_RUSH[0]);
    }

    /**
     * Sets shopper time for dinner rush shopper
     * @return time dinner rush shopper spends in store as double
     */
    private double set_dinner_time_spent() {
        return DINNER_RUSH[0] + Math.random() * (DINNER_RUSH[1] - DINNER_RUSH[0]);
    }

    /**
     * Sets shopper time for a normal shopper on a weekend
     * @return time shopper spends in store as double
     */
    private double set_weekend_normal() {
        return AVG_SHOPPER_WEEKEND[0] + Math.random() * (AVG_SHOPPER_WEEKEND[1] - AVG_SHOPPER_WEEKEND[0]);
    }

    /**
     * Sets shopper time for a short shopper on a weekend
     * Specifically will be used for nice weather weekends
     * @return time shopper spends in store as double
     */
    private double set_weekend_short() {
        return WEEKEND_SHORT_TIME[0] + Math.random() * (WEEKEND_SHORT_TIME[1] - WEEKEND_SHORT_TIME[0]);
    }

    /**
     * Handles shopper time for a shopper on weekend
     * This is an encapsulating method for all weekend times
     * @param is_senior boolean whether shopper is senior
     * @param day_nice boolean whether day is nice on weekend
     * @return time shopper spends in store as double
     */
    private double set_weekend_time_spent(boolean is_senior, boolean day_nice) {
        double WEEKEND_SHORT_VISITS = 0.5;
        if(is_senior){
            return this.set_senior_time_spent();
        }
        else if(day_nice && Math.random() < WEEKEND_SHORT_VISITS){
            return set_weekend_short();
        }
        else{
            return set_weekend_normal();
        }
    }

    /**
     * Handles shopper time for a shopper on weekday
     * This is an encapsulating method for all weekday times
     * @param rush String for what type of shopper
     * @return time shopper spends in store as double
     */
    private double set_weekday_time_spent(String rush) {
        switch(rush) {
            case "Lunch":
                return this.set_lunch_time_spent();
            case "Dinner":
                return this.set_dinner_time_spent();
            case "Senior":
                return this.set_senior_time_spent();
            default:
                return this.set_weekday_normal();
        }
    }

    /**
     * This method handles the holiday multiplier
     * Checks today, tomorrow, within a week
     * @param year Integer value of year
     * @param date LocalDate type of date
     * @return Holiday multiplier as a double
     */
    private double handle_holidays(int year, LocalDate date){
        Holidays holiday = new Holidays();
        List<LocalDate> holiday_list = holiday.collect_holidays(year);
        if(holiday_list.contains(date)){
            return parameters.getHOLIDAY_MULT()[2];
        }
        else if(holiday_list.contains(date.plusDays(1))){
            return parameters.getHOLIDAY_MULT()[2];
        }
        else if (holiday_list.stream().anyMatch(i -> DAYS.between(date, i) <= 7 && i.isAfter(date))){
            return parameters.getHOLIDAY_MULT()[2];
        }
        else{
            return 1;
        }
    }

    /**
     * This method specifically handles customers who come in too close to closing
     * If they do, then the rounder will round them to enter at 15.00
     * This method will make sure they do not enter at 15.00
     * Changes time entered to 14.99
     * @param shopper Shopper variable to be checked
     */
    private void handle_close_to_closing(Shopper shopper) {
        DecimalFormat df = new DecimalFormat("#.##");
        if(Double.valueOf(df.format(shopper.getTime_entered())).intValue() == STORE_HOURS[1]){
            shopper.setTime_entered(14.99);
        }
    }

    /**
     * This method specifically handles customers who stay past closing
     * Changes time spent to a viable time so they leave at closing
     * @param shopper Shopper variable to be checked
     */
    private void handle_closed_customers(Shopper shopper) {
        if(shopper.getTime_entered() + (shopper.getTime_spent() / 60) > STORE_HOURS[1]) {
            double time_spent = STORE_HOURS[1] - shopper.getTime_entered();
            DecimalFormat df = new DecimalFormat("#.##");
            shopper.setTime_spent(Double.parseDouble(df.format(time_spent)));
        }
    }

    /**
     * Encapsulating method for checking and rounding shopper times
     * Makes those times manageable and feasible
     * @param shopper Shopper variable to be checked
     */
    private void check_and_round_times(Shopper shopper) {
        this.handle_close_to_closing(shopper);
        this.handle_closed_customers(shopper);

        DecimalFormat df = new DecimalFormat("#.##");
        shopper.setTime_spent(Double.parseDouble(df.format(shopper.getTime_spent())));
        shopper.setTime_entered(Double.parseDouble(df.format(shopper.getTime_entered())));
    }

    /**
     * Generates a single shopper variable
     * @param day_num Integer number of day
     * @param day_nice Boolean whether day is nice
     * @return Shopper variable
     */
    private Shopper generate_shopper(int day_num, boolean day_nice) {
        Shopper shopper = new Shopper(day_num, parameters);
        if(day_num < 5){
            shopper.setTime_spent(this.set_weekday_time_spent(shopper.getRush()));
        }
        else{
            shopper.setTime_spent(this.set_weekend_time_spent(shopper.getIs_senior(), day_nice));
        }
        this.check_and_round_times(shopper);
        return shopper;
    }

    /**
     * Get total number of shoppers for a specific date
     * Will factor in weather for weekends
     * Will factor in holidays
     * @param date LocalDate of date
     * @param day_nice Boolean whether day nice
     * @return Integer number of shoppers for date
     */
    private int get_num_shoppers(LocalDate date, boolean day_nice){
        int day_num = date.getDayOfWeek().getValue();
        double num_shoppers = AVG_SHOPPERS[day_num % 7] * this.handle_holidays(date.getYear(), date);
        if(day_nice && day_num > 5){
            num_shoppers = num_shoppers * parameters.getWEEKEND_NICE_WEATHER_MULT();
        }
        return (int)num_shoppers;
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

        sql_connector.writeTable(table_name + parameters.getNaming());
        sql_connector.writeParametersTable(table_name);

        String[] parameter_names = parameters.passNames();
        String[] parameter_values = parameters.passValues();

        for(int i = 0; i < parameter_names.length; i++){
            sql_connector.writeParameters(table_name, parameter_names[i], parameter_values[i]);
        }

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

    /**
     * This method changed parameters for the program.
     * Read below to see what changed what.
     */
    private void changeParameters(){
        int index = 100;
        while(index != 0) {
            Scanner change = new Scanner(System.in);
            System.out.println("Would you like to change parameters? Enter 0-11 (mod 12).\n" +
                    "1. Senior discount day\n" +
                    "2. Senior chance on regular day\n" +
                    "3. Senior enter hours\n" +
                    "4. Senior discount time\n" +
                    "5. Senior chance on discount day\n" +
                    "6. Senior chance in discount time on discount day\n" +
                    "7. Rush chance\n" +
                    "8. Lunch times\n" +
                    "9. Dinner times\n" +
                    "10. Nice weather weekend multiplier\n" +
                    "11. Holiday multiplier\n" +
                    "0 to exit");

            try{
                index = change.nextInt() % 12;
                parameters.edit_values(index);
            } catch (InputMismatchException e){
                System.err.println("Input an integer.");
            }
        }
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

        this.changeParameters();
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
