import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import static java.time.temporal.ChronoUnit.DAYS;

public class Supermarket {

    private int[] STORE_HOURS = {0, 15};
    private int[] AVG_SHOPPERS = {5000, 800, 1000, 1200, 900, 2500, 4000};
    private double RUSH_CHANCE = 0.75;

    private double[] HOLIDAY_MULT = {1.15, 1.40, 0.2};

    // Time spent
    private int[][] AVG_SHOPPER_WEEKDAY = {{6, 25}, {25, 75}};
    private int[] AVG_SHOPPER_WEEKEND = {50, 70};
    private int[] WEEKEND_SHORT_TIME = {16, 24};
    private int[] SENIOR_TIME_SPENT = {45, 60};
    private int[] LUNCH_RUSH = {6, 14};
    private int[] DINNER_RUSH = {16, 24};

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

    private double set_senior_time_spent() {
        return SENIOR_TIME_SPENT[0] + Math.random() * (SENIOR_TIME_SPENT[1] - SENIOR_TIME_SPENT[0]);
    }

    private double set_lunch_time_spent() {
        if(Math.random() < RUSH_CHANCE) {
            return LUNCH_RUSH[0] + Math.random() * (LUNCH_RUSH[1] - LUNCH_RUSH[0]);
        }
        else{
            return set_weekday_normal();
        }
    }

    private double set_dinner_time_spent() {
        if(Math.random() < RUSH_CHANCE) {
            return DINNER_RUSH[0] + Math.random() * (DINNER_RUSH[1] - DINNER_RUSH[0]);
        }
        else{
            return set_weekday_normal();
        }
    }

    private double set_weekend_normal() {
        return AVG_SHOPPER_WEEKEND[0] + Math.random() * (AVG_SHOPPER_WEEKEND[1] - AVG_SHOPPER_WEEKEND[0]);
    }

    private double set_weekend_short() {
        return WEEKEND_SHORT_TIME[0] + Math.random() * (WEEKEND_SHORT_TIME[1] - WEEKEND_SHORT_TIME[0]);
    }

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

    private void handle_close_to_closing(Shopper shopper) {
        DecimalFormat df = new DecimalFormat("#.##");
        if(Double.valueOf(df.format(shopper.getTime_entered())).intValue() == STORE_HOURS[1]){
            shopper.setTime_entered(14.99);
        }
    }

    private void handle_closed_customers(Shopper shopper) {
        if(shopper.getTime_entered() + (shopper.getTime_spent() / 60) > STORE_HOURS[1]) {
            double time_spent = STORE_HOURS[1] - shopper.getTime_entered();
            DecimalFormat df = new DecimalFormat("#.##");
            shopper.setTime_spent(Double.valueOf(df.format(time_spent)));
        }
    }

    private void check_and_round_times(Shopper shopper) {
        this.handle_closed_customers(shopper);
        this.handle_close_to_closing(shopper);
        DecimalFormat df = new DecimalFormat("#.##");
        shopper.setTime_spent(Double.valueOf(df.format(shopper.getTime_spent())));
        shopper.setTime_entered(Double.valueOf(df.format(shopper.getTime_entered())));
    }

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

    private double get_num_shoppers(LocalDate date, boolean day_nice){
        int day_num = date.getDayOfWeek().getValue();
        double num_shoppers = AVG_SHOPPERS[day_num % 7] * this.handle_holidays(date.getYear(), date);
        if(day_nice && day_num > 5){
            double WEEKEND_NICE_WEATHER_MULT = 1.4;
            num_shoppers = num_shoppers * WEEKEND_NICE_WEATHER_MULT;
        }
        return num_shoppers;
    }

    private void write_data(LocalDate date, boolean day_nice) throws Exception {
        int day_num = date.getDayOfWeek().getValue();
        FileWriter csvWriter = new FileWriter(date.getDayOfWeek() + ".csv");
        csvWriter.append("Time Entered (hr), Time Spend (min), Rushing, Senior\n");

        for(int i = 0; i < this.get_num_shoppers(date, day_nice); i++){
            Shopper shopper_here = this.generate_shopper(day_num, day_nice);

            SQL_helper dao = new SQL_helper();
            dao.writeData(shopper_here.toString());

            csvWriter.append(shopper_here.toString());
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();
    }

    private double mean(ArrayList<Double> table)
    {
        int total = 0;

        for (Double value : table)
        {
            total+= value;
        }
        return total/table.size();
    }

    private double sd(ArrayList<Double> table)
    {
        double mean = mean(table);
        double temp = 0;

        for (Double value : table)
        {
            double squareDiffToMean = Math.pow(value - mean, 2);
            temp += squareDiffToMean;
        }

        double meanOfDiffs = (double) temp / (double) (table.size());
        return Math.sqrt(meanOfDiffs);
    }

    private void read_data(LocalDate date) throws IOException {
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
            Double time_spent = Double.valueOf(data[1]);
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

            Double time_entered = Double.valueOf(data[0]);
            new_customers[time_entered.intValue()] += 1;

            for(int i = time_entered.intValue(); i <= (int)(time_entered + (time_spent / 60)); i++){
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
            csvWriter.append(number + ",");
        }

        csvWriter.append("\nCustomers in store per hour: ,");
        for (int in_store : customers_in_store) {
            csvWriter.append(in_store + ",");
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
        String month = user_month.nextLine();
        Scanner user_day = new Scanner(System.in);
        System.out.println("Enter a day 1-28/29/30/31");
        String day = user_day.nextLine();
        Scanner user_year = new Scanner(System.in);
        System.out.println("Enter a year");
        String year = user_year.nextLine();
        Scanner user_weather = new Scanner(System.in);
        System.out.println("Is the weather nice? y/n");
        String weather = user_weather.nextLine();

        this.write_data(LocalDate.of(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day)), weather.equals("y"));
        this.read_data(LocalDate.of(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day)));
    }

    public static void main(String[] args) throws Exception {
        Supermarket supermarket = new Supermarket();
        supermarket.helper();
        System.out.println("Done creating csv files.");
    }
}
