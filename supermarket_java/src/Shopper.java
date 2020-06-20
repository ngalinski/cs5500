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

    private final double RUSH = 0.80;
    private final double SENIOR_CHANCE = 0.16;
    private final double SENIOR_CHANCE_TUESDAY = 0.40;
    private final double SENIOR_DISCOUNT_CHANCE = 0.65;
    private final int[] SENIOR_ENTER_TIME = {0, 12};
    private final int[] SENIOR_DISCOUNT_TIME = {4, 6};
    private final int[] STORE_HOURS = {0, 15};
    private final double[][] WEEKDAY_TIMES = {{0, 6}, {6, 7}, {7, 11}, {11, 12.5}, {12.5, 15}};

    private final int day;
    private double time_spent, time_entered;
    private boolean is_senior;
    private String rush;

    /**
     * Shopper object has time entered, time spent
     * Also has rush type and whether a senior
     * @param day Integer number of day in week
     */
    public Shopper(int day){
        this.day = day;
        this.senior();
        this.initial_time_entered();
        this.setRush();
    }

    /**
     * Method decides if shopper is a senior
     * Higher chance of being senior on tuesday
     */
    private void senior(){
        if(this.day == 2){
            this.is_senior = Math.random() < this.SENIOR_CHANCE_TUESDAY;
        }
        else{
            this.is_senior = Math.random() < this.SENIOR_CHANCE;
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
        if(this.day == 2 && Math.random() < SENIOR_DISCOUNT_CHANCE) {
            return SENIOR_DISCOUNT_TIME[0] + Math.random() *
                    (SENIOR_DISCOUNT_TIME[1] - SENIOR_DISCOUNT_TIME[0]);
        }
        else{
            return Math.random() * this.SENIOR_ENTER_TIME[1];
        }
    }

    /**
     * Sets time entered for a regular shopper
     * @return Double time entered
     */
    private double setTime_entered_regular() {
        Random rand = new Random();
        if(this.day < 5) {
            double[] times = WEEKDAY_TIMES[rand.nextInt(5)];
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
        if(6 <= this.getTime_entered() && this.getTime_entered() <= 7 && Math.random() <= RUSH) {
            this.rush = "Lunch";
        }

        else if(11 <= this.getTime_entered() && this.getTime_entered() <= 12.5 && Math.random() <= RUSH){
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
