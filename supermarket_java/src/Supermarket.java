import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import static java.time.temporal.ChronoUnit.DAYS;

/**
 * This is the encapsulating supermarket class which creates all shoppers
 */
public class Supermarket {

    private final int[] STORE_HOURS = {0, 15};
    private final int[] AVG_SHOPPERS = {5000, 800, 1000, 1200, 900, 2500, 4000};
    private final double RUSH_CHANCE = 0.75;
    private final double[] HOLIDAY_MULT = {1.15, 1.40, 0.2};
    private final double WEEKEND_NICE_WEATHER_MULT = 1.4;

    // Time spent
    private final int[][] AVG_SHOPPER_WEEKDAY = {{6, 25}, {25, 75}};
    private final int[] AVG_SHOPPER_WEEKEND = {50, 70};
    private final int[] WEEKEND_SHORT_TIME = {16, 24};
    private final int[] SENIOR_TIME_SPENT = {45, 60};
    private final int[] LUNCH_RUSH = {6, 14};
    private final int[] DINNER_RUSH = {16, 24};

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
        if(Math.random() < RUSH_CHANCE) {
            return LUNCH_RUSH[0] + Math.random() * (LUNCH_RUSH[1] - LUNCH_RUSH[0]);
        }
        else{
            return set_weekday_normal();
        }
    }

    /**
     * Sets shopper time for dinner rush shopper
     * @return time dinner rush shopper spends in store as double
     */
    private double set_dinner_time_spent() {
        if(Math.random() < RUSH_CHANCE) {
            return DINNER_RUSH[0] + Math.random() * (DINNER_RUSH[1] - DINNER_RUSH[0]);
        }
        else{
            return set_weekday_normal();
        }
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
            return HOLIDAY_MULT[2];
        }
        else if(holiday_list.contains(date.plusDays(1))){
            return HOLIDAY_MULT[1];
        }
        else if (holiday_list.stream().anyMatch(i -> DAYS.between(date, i) <= 7 && i.isAfter(date))){
            return HOLIDAY_MULT[0];
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
    private Shopper generate_shopper(int day_num, boolean day_nice){
        Shopper shopper = new Shopper(day_num);
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
            num_shoppers = num_shoppers * WEEKEND_NICE_WEATHER_MULT;
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
        sql_connector.writeTable(date.toString());

        for(int i = 0; i < this.get_num_shoppers(date, day_nice); i++){
            Shopper shopper_here = this.generate_shopper(day_num, day_nice);

            // This line writes to mysql
            sql_connector.writeData(date.toString(), shopper_here.toString());

            csvWriter.append(shopper_here.toString());
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();
    }

    /**
     * Generate mean of arraylist
     * @param table ArrayList of doubles
     * @return mean of arraylist
     */
    private double mean(ArrayList<Double> table)
    {
        Double total = 0.0;

        for (Double value : table)
        {
            total+= value;
        }

        double mean = total/table.size();
        DecimalFormat df = new DecimalFormat("#.##");
        df.format(mean);

        return mean;
    }

    /**
     * Generate standard deviation of arraylist
     * @param table ArrayList of doubles
     * @return standard deviation of table
     */
    private double sd(ArrayList<Double> table)
    {
        double mean = mean(table);
        double temp = 0;

        for (Double value : table)
        {
            double squareDiffToMean = Math.pow(value - mean, 2);
            temp += squareDiffToMean;
        }

        double meanOfDiffs = temp / (double) (table.size());
        double sd = Math.sqrt(meanOfDiffs);
        DecimalFormat df = new DecimalFormat("#.##");
        df.format(sd);

        return sd;
    }

    /**
     * This method is for generating a statistics csv for the specific date
     * Customer can call this method if they want
     * @param date LocalDate type of date to be looked at
     * @throws IOException If file does not exist
     */
    private void generate_statistics(LocalDate date) throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader(date. getDayOfWeek() + ".csv"));
        String row;

        int lunch_customers = 0;
        int dinner_customers = 0;
        int senior_customers = 0;
        int normal_customers = 0;

        csvReader.readLine();

        ArrayList<Double> lunch_times = new ArrayList<>();
        ArrayList<Double> dinner_times = new ArrayList<>();
        ArrayList<Double> senior_times = new ArrayList<>();
        ArrayList<Double> normal_times = new ArrayList<>();

        Integer[] new_customers = new Integer[16];
        Integer[] customers_in_store = new Integer[32];
        Arrays.fill(new_customers, 0);
        Arrays.fill(customers_in_store, 0);

        while ((row = csvReader.readLine()) != null){
            String[] data = row.split(",");
            double time_spent = Double.parseDouble(data[1]);
            switch (data[2]){
                case "Lunch":
                    lunch_customers += 1;
                    lunch_times.add(time_spent);
                    break;
                case "Dinner":
                    dinner_customers += 1;
                    dinner_times.add(time_spent);
                    break;
                case "Senior":
                    senior_customers += 1;
                    senior_times.add(time_spent);
                    break;
                default:
                    normal_customers += 1;
                    normal_times.add(time_spent);
            }

            double time_entered = Double.parseDouble(data[0]);
            new_customers[(int) time_entered] += 1;

            for(int i = (int) time_entered; i <= (int)(time_entered + (time_spent / 60)); i++){
                customers_in_store[i] += 1;
            }
        }
        csvReader.close();

        FileWriter csvWriter = new FileWriter(date.getDayOfWeek() + "_statistics.csv");
        int total_customers = 0;

        csvWriter.append(String.valueOf(date)).append(" Statistics\n");
        csvWriter.append("6 to 7,7 to 8,8 to 9,9 to 10,10 to 11," +
                "11 to 12,12 to 1,1 to 2,2 to 3,3 to 4," +
                "4 to 5,5 to 6,6 to 7,7 to 8,8 to 9,Closing\n");

        csvWriter.append("New customers per hour: ,");
        for (int number : new_customers) {
            total_customers += number;
            csvWriter.append(String.valueOf(number)).append(",");
        }

        csvWriter.append("\nCustomers in store per hour: ,");
        for (int in_store : customers_in_store) {
            csvWriter.append(String.valueOf(in_store)).append(",");
        }

        csvWriter.append("\nTotal customers: ,").append(String.valueOf(total_customers));
        csvWriter.append("\nLunch: ,").append(String.valueOf(lunch_customers));
        csvWriter.append("\nDinner: ,").append(String.valueOf(dinner_customers));
        csvWriter.append("\nSenior: ,").append(String.valueOf(senior_customers));
        csvWriter.append("\nNormal: ,").append(String.valueOf(normal_customers));
        csvWriter.append("\nCustomers in store at closing: ,").append(String.valueOf(customers_in_store[customers_in_store.length - 1]));

        csvWriter.append("\nLunch mean: ,").append(String.valueOf(mean(lunch_times)));
        csvWriter.append("\nLunch std: ,").append(String.valueOf(sd(lunch_times)));
        csvWriter.append("\nDinner mean: ,").append(String.valueOf(mean(dinner_times)));
        csvWriter.append("\nDinner std: ,").append(String.valueOf(sd(dinner_times)));
        csvWriter.append("\nSenior mean: ,").append(String.valueOf(mean(senior_times)));
        csvWriter.append("\nSenior std: ,").append(String.valueOf(sd(senior_times)));
        csvWriter.append("\nNormal mean: ,").append(String.valueOf(mean(normal_times)));
        csvWriter.append("\nNormal std: ,").append(String.valueOf(sd(normal_times)));

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

        Scanner whether_statistics = new Scanner(System.in);
        System.out.println("Do you want a statistics csv? y/n");
        boolean statistics = whether_statistics.nextLine().equals("y");

        this.write_data(LocalDate.of(year, month, day), weather);

        if(statistics){
            this.generate_statistics(LocalDate.of(year, month, day));
        }
    }

    public static void main(String[] args) throws Exception {
        Supermarket supermarket = new Supermarket();
        supermarket.helper();
        System.out.println("Done creating csv files.");
        System.out.println("Done updating mysql.");
    }
}
