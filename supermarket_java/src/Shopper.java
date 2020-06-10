//Shopper Class File

/*
This shopper class function builds a random shopper.
The shopper has a random chance at being a senior.
 */
import java.util.Random;
import java.util.*;


public class Shopper {

    private double RUSH = 0.80;
    private double SENIOR_CHANCE = 0.16;
    private double SENIOR_CHANCE_TUESDAY = 0.40;
    private double SENIOR_DISCOUNT_CHANCE = 0.65;
    private int[] SENIOR_ENTER_TIME = {0, 12};
    private int[] SENIOR_DISCOUNT_TIME = {4, 6};
    private int[] STORE_HOURS = {0, 15};
    private double[][] WEEKDAY_TIMES = {{0, 6}, {6, 7}, {7, 11}, {11, 12.5}, {12.5, 15}};

    private int day;
    private double time_spent, time_entered;
    private boolean is_senior, is_tuesday;
    private String rush;

    public Shopper(int day){
        this.day = day;
        this.senior();
        this.initial_time_entered();
        this.setRush();
    }

    private void senior(){
        if(this.day == 2){
            this.is_senior = Math.random() < this.SENIOR_CHANCE_TUESDAY;
        }
        else{
            this.is_senior = Math.random() < this.SENIOR_CHANCE;
        }
    }

    private void initial_time_entered() {
        if(this.getIs_senior()){
            this.time_entered = this.setTIme_entered_senior();
        }
        else{
            this.time_entered = this.setTime_entered_regular();
        }
    }

    void setTime_entered(double time_entered){
        this.time_entered = time_entered;
    }

    private double setTIme_entered_senior() {
        if(this.day == 2 && Math.random() < SENIOR_DISCOUNT_CHANCE) {
            return SENIOR_DISCOUNT_TIME[0] + Math.random() *
                    (SENIOR_DISCOUNT_TIME[1] - SENIOR_DISCOUNT_TIME[0]);
        }
        else{
            return Math.random() * this.SENIOR_ENTER_TIME[1];
        }
    }

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

    void setTime_spent(double time_spent) {
        this.time_spent = time_spent;
    }

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

    boolean getIs_senior() {
        return is_senior;
    }


    double getTime_spent() {
        return time_spent;
    }

    double getTime_entered() {
        return time_entered;
    }

    String getRush() {
        return rush;
    }

    @Override
    public String toString() {
        return this.getTime_entered() + "," + this.getTime_spent() + "," + this.getRush() + "," + this.getIs_senior();
    }
}
