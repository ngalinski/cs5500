import java.text.DecimalFormat;

public class Single_shopper {

    private final Json_reader parameters;
    private final Double[] store_hours;

    // Time spent
    private final int[][] AVG_SHOPPER_WEEKDAY = {{6, 25}, {25, 75}};
    private final int[] AVG_SHOPPER_WEEKEND = {50, 70};
    private final int[] WEEKEND_SHORT_TIME = {16, 24};
    private final int[] SENIOR_TIME_SPENT = {45, 60};
    private final int[] LUNCH_RUSH = {6, 14};
    private final int[] DINNER_RUSH = {16, 24};

    private int day_num;
    private boolean day_nice;

    public Single_shopper(int day_num, boolean day_nice, Json_reader parameters){
        this.parameters = parameters;
        this.store_hours = new Double[]{parameters.getValue("STORE_OPEN"),
                parameters.getValue("STORE_CLOSE")};
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
     * This method specifically handles customers who come in too close to closing
     * If they do, then the rounder will round them to enter at 15.00
     * This method will make sure they do not enter at 15.00
     * Changes time entered to 14.99
     * @param shopper Shopper variable to be checked
     */
    private void handle_close_to_closing(Shopper shopper) {
        DecimalFormat df = new DecimalFormat("#.##");
        if(Double.valueOf(df.format(shopper.getTime_entered())).intValue() == store_hours[1]){
            shopper.setTime_entered(14.99);
        }
    }

    /**
     * This method specifically handles customers who stay past closing
     * Changes time spent to a viable time so they leave at closing
     * @param shopper Shopper variable to be checked
     */
    private void handle_closed_customers(Shopper shopper) {
        if(shopper.getTime_entered() + (shopper.getTime_spent() / 60) > store_hours[1]) {
            double time_spent = store_hours[1] - shopper.getTime_entered();
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

    public Shopper gen_shopper(){
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
}
