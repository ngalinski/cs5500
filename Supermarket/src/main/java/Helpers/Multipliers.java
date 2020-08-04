package Helpers;

import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class Multipliers {

    private final Json_reader parameters;
    private final LocalDate date;
    private int num_Shoppers;
    private final boolean day_nice;

    public Multipliers(LocalDate date, int num_shoppers, boolean day_nice,  Json_reader parameters){
        this.parameters = parameters;
        this.date = date;
        this.num_Shoppers = num_shoppers;
        this.day_nice = day_nice;
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
            return parameters.getValue("HOLIDAY_MULT");
        }
        else if(holiday_list.contains(date.plusDays(1))){
            return parameters.getValue("DAY_HOLIDAY_MULT");
        }
        else if (holiday_list.stream().anyMatch(i -> DAYS.between(date, i) <= 7 && i.isAfter(date))){
            return parameters.getValue("WEEK_HOLIDAY_MULT");
        }
        else{
            return 1;
        }
    }

    /**
     * Get total number of shoppers for a specific date
     * Will factor in weather for weekends
     * Will factor in holidays
     * @return Integer number of shoppers for date
     */
    public int get_num_shoppers(){
        int day_num = date.getDayOfWeek().getValue();
        double num_shoppers = num_Shoppers * this.handle_holidays(date.getYear(), date);
        if(day_nice && day_num > 5){
            num_shoppers = num_shoppers * parameters.getValue("WEEKEND_NICE_WEATHER_MULT");
        }
        return (int)num_shoppers;
    }
}
