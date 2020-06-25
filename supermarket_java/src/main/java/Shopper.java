//Shopper Class File

/*
This shopper class function builds a random shopper.
The shopper has a random chance at being a senior.
 */
import java.util.Random;
import java.util.*;


/**
 * Shopper class for an individual shopper
 */
public class Shopper {

    private Json_reader json_parameters;

    private Double[] LUNCH_TIMES;
    private Double[] DINNER_TIMES;
    private Double[][] WEEKDAY_TIMES;

    private final Double[] STORE_HOURS = {0d, 15d};

    private final int day;
    private double time_spent, time_entered;
    private boolean is_senior;
    private String rush;
    private final Parameters parameters = new Parameters();

    /**
     * Shopper object has time entered, time spent
     * Also has rush type and whether a senior
     * @param day Integer number of day in week
     */
    public Shopper(int day, Json_reader passed_parameters){
        this.json_parameters = passed_parameters;
        this.setTimes();
        this.day = day;
        this.senior();
        this.initial_time_entered();
        this.setRush();
    }

    public void setTimes(){
        LUNCH_TIMES = (new Double[] {json_parameters.getValue("LUNCH_START"),
                json_parameters.getValue("LUNCH_END")});
        DINNER_TIMES = (new Double[] {json_parameters.getValue("DINNER_START"),
                json_parameters.getValue("DINNER_END")});
        WEEKDAY_TIMES = new Double[][] {{STORE_HOURS[0], LUNCH_TIMES[0]},
                {LUNCH_TIMES[0], LUNCH_TIMES[1]}, {LUNCH_TIMES[1], DINNER_TIMES[0]},
                {DINNER_TIMES[0], DINNER_TIMES[1]}, {DINNER_TIMES[1], STORE_HOURS[1]}};
    }

    /**
     * Method decides if shopper is a senior
     * Higher chance of being senior on tuesday
     */
    private void senior(){
        if(this.day == parameters.getSENIOR_DAY()){
            this.is_senior = Math.random() < parameters.getSENIOR_CHANCE_DISCOUNT_DAY();
        }
        else{
            this.is_senior = Math.random() < parameters.getSENIOR_CHANCE();
        }
    }

    /**
     * Set shopper time entered initially
     */
    private void initial_time_entered() {
        if(this.getIs_senior()){
            this.time_entered = this.setTIme_entered_senior();
        }
        else{
            this.time_entered = this.setTime_entered_regular();
        }
    }

    /**
     * Setter for time entered
     * This is here in case shopper enters too late
     * @param time_entered Double time entered store
     */
    void setTime_entered(double time_entered){
        this.time_entered = time_entered;
    }

    /**
     * Setter for time entered if a senior
     * @return Double senior time
     */
    private double setTIme_entered_senior() {
        if(this.day == parameters.getSENIOR_DAY() && Math.random() < parameters.getSENIOR_DISCOUNT_CHANCE()) {
            return parameters.getSENIOR_DISCOUNT_TIME()[0] + Math.random() *
                    (parameters.getSENIOR_DISCOUNT_TIME()[1] - parameters.getSENIOR_DISCOUNT_TIME()[0]);
        }
        else{
            return Math.random() * parameters.getSENIOR_ENTER_TIME()[1];
        }
    }

    /**
     * Sets time entered for a regular shopper
     * @return Double time entered
     */
    private double setTime_entered_regular() {
        Random rand = new Random();
        if(this.day < 5) {
            Double[] times = WEEKDAY_TIMES[rand.nextInt(5)];
            return times[0] + Math.random() * (times[1] - times[0]);
        }
        else{
            return Math.random() * this.STORE_HOURS[1];
        }
    }

    /**
     * Setter for time spent in case shopper stays past closing
     * @param time_spent Double time spent in store
     */
    void setTime_spent(double time_spent) {
        this.time_spent = time_spent;
    }

    /**
     * Setter for rush type of shopper
     * Types include lunch, dinner, senior, normal
     */
    private void setRush() {
        if(6 <= this.getTime_entered() && this.getTime_entered() <= 7
                && Math.random() <= parameters.getRUSH_CHANCE()) {
            this.rush = "Lunch";
        }

        else if(11 <= this.getTime_entered() && this.getTime_entered() <= 12.5
                && Math.random() <= parameters.getRUSH_CHANCE()){
            this.rush = "Dinner";
        }

        else if(this.getIs_senior()){
            this.rush = "Senior";
        }

        else{
            this.rush = "Normal";
        }
    }

    /**
     * Getter for whether senior
     * @return Boolean whether senior
     */
    boolean getIs_senior() {
        return is_senior;
    }

    /**
     * Getter for time spent
     * @return Double time spent
     */
    double getTime_spent() {
        return time_spent;
    }

    /**
     * Getter for time entered
     * @return Double time entered
     */
    double getTime_entered() {
        return time_entered;
    }

    /**
     * Getter for rush type
     * @return String rush type
     */
    String getRush() {
        return rush;
    }

    /**
     * This will be used for entering data of shoppers
     * @return String shopper info
     */
    @Override
    public String toString() {
        return this.getTime_entered() + "," + this.getTime_spent() + "," + this.getRush() + "," + this.getIs_senior();
    }
}
