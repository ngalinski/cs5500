import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Parameters {

    // DEFAULT VALUES
    private int SENIOR_DAY = 2;
    private double SENIOR_CHANCE = 0.16;
    private int[] SENIOR_ENTER_TIME = {0, 12};
    private int[] SENIOR_DISCOUNT_TIME = {4, 6};
    private double SENIOR_CHANCE_DISCOUNT_DAY = 0.40;
    private double SENIOR_DISCOUNT_CHANCE = 0.65;
    private double RUSH_CHANCE = 0.80;
    private Double[] DEFAULT_LUNCH_TIMES = {6d, 7d};
    private Double[] DEFAULT_DINNER_TIMES = {11d, 12.5};
    private double WEEKEND_NICE_WEATHER_MULT = 1.4;
    private Double[] HOLIDAY_MULT = {1.15, 1.40, 0.2};

    private String[] naming = {"D", "D", "D", "D", "D", "D", "D", "D", "D", "D", "D"};
    private Scanner user;

    public Double[] getHOLIDAY_MULT() {
        return HOLIDAY_MULT;
    }

    public void setHOLIDAY_MULT(Double[] HOLIDAY_MULT) {
        this.HOLIDAY_MULT = HOLIDAY_MULT;
    }

    public double getWEEKEND_NICE_WEATHER_MULT() {
        return WEEKEND_NICE_WEATHER_MULT;
    }

    public void setWEEKEND_NICE_WEATHER_MULT(double WEEKEND_NICE_WEATHER_MULT) {
        this.WEEKEND_NICE_WEATHER_MULT = WEEKEND_NICE_WEATHER_MULT;
    }

    public Double[] getDEFAULT_LUNCH_TIMES() {
        return DEFAULT_LUNCH_TIMES;
    }

    public Double[] getDEFAULT_DINNER_TIMES() {
        return DEFAULT_DINNER_TIMES;
    }

    public int getSENIOR_DAY() {
        return SENIOR_DAY;
    }

    public double getRUSH_CHANCE() {
        return RUSH_CHANCE;
    }

    public double getSENIOR_CHANCE() {
        return SENIOR_CHANCE;
    }

    public double getSENIOR_CHANCE_DISCOUNT_DAY() {
        return SENIOR_CHANCE_DISCOUNT_DAY;
    }

    public double getSENIOR_DISCOUNT_CHANCE() {
        return SENIOR_DISCOUNT_CHANCE;
    }

    public int[] getSENIOR_ENTER_TIME() {
        return SENIOR_ENTER_TIME;
    }

    public int[] getSENIOR_DISCOUNT_TIME() {
        return SENIOR_DISCOUNT_TIME;
    }

    public void setSENIOR_ENTER_TIME(int[] SENIOR_ENTER_TIME) {
        this.SENIOR_ENTER_TIME = SENIOR_ENTER_TIME;
    }

    public void setSENIOR_DISCOUNT_TIME(int[] times){
        this.SENIOR_DISCOUNT_TIME = times;
    }

    public void setSENIOR_CHANCE(double chance){
        this.SENIOR_CHANCE = chance;
    }

    // Odds senior entering on discount day
    public void setSENIOR_CHANCE_DISCOUNT_DAY(double chance){
        this.SENIOR_CHANCE_DISCOUNT_DAY = chance;
    }

    // Odds senior enters during discount time on discount day
    public void setSENIOR_DISCOUNT_CHANCE(double chance){
        this.SENIOR_DISCOUNT_CHANCE = chance;
    }

    public void setDEFAULT_LUNCH_TIMES(Double[] times){
        this.DEFAULT_LUNCH_TIMES = times;
    }

    public void setDEFAULT_DINNER_TIMES(Double[] times){
        this.DEFAULT_DINNER_TIMES = times;
    }

    public void setSENIOR_DAY(int day){
        this.SENIOR_DAY = day;
    }

    public void setRUSH_CHANCE(double chance){
        this.RUSH_CHANCE = chance;
    }

    public void editOne(){
        user = new Scanner(System.in);
        System.out.println("Enter a new senior day 1-7 (current " + this.getSENIOR_DAY() + ")");
        try{
            int day = user.nextInt();
            if(day <= 7 && day >= 1) {
                this.setSENIOR_DAY(day);
            }
            else{
                System.out.println("Unaccepted value");
            }
        } catch (InputMismatchException e){
            System.err.println("Wrong input type, accepted default value");
        }
    }

    private void edit2(){
        user = new Scanner(System.in);
        System.out.println("Enter a new senior chance 0-1 (current " + this.getSENIOR_CHANCE() + ")");
        try{
            double chance = user.nextDouble();
            if(chance < 1 && chance > 0) {
                this.setSENIOR_CHANCE(chance);
            }
            else{
                System.out.println("Unaccepted value");
            }
        } catch (InputMismatchException e){
            System.err.println("Wrong input type, accepted default value");
        }
    }

    public void edit3(){
        user = new Scanner(System.in);
        System.out.println("Enter a new last senior in time 0-12 (current " + SENIOR_ENTER_TIME[1] + ")");
        try{
            int time = user.nextInt();
            if(time < 13 && time > 0) {
                this.setSENIOR_ENTER_TIME(new int[] {0, time});
            }
            else{
                System.out.println("Unaccepted value");
            }
        } catch (InputMismatchException e){
            System.err.println("Wrong input type, accepted default value");
        }
    }

    public void edit4(){
        user = new Scanner(System.in);
        System.out.println("Enter a new senior discount start time 0-13 (current " +
                SENIOR_DISCOUNT_TIME[0] +", discount lasts 2 hours)");
        try{
            int time = user.nextInt();
            if(time < 13 && time > 0) {
                this.setSENIOR_DISCOUNT_TIME(new int[] {time, time + 2});
            }
            else{
                System.out.println("Unaccepted value");
            }
        } catch (InputMismatchException e){
            System.err.println("Wrong input type, accepted default value");
        }
    }

    private void edit5(){
        user = new Scanner(System.in);
        System.out.println("Enter a new senior discount day chance 0-1 (current " +
                this.getSENIOR_CHANCE_DISCOUNT_DAY() + ")");
        try{
            double chance = user.nextDouble();
            if(chance < 1 && chance > 0) {
                this.setSENIOR_CHANCE_DISCOUNT_DAY(chance);
            }
            else{
                System.out.println("Unaccepted value");
            }
        } catch (InputMismatchException e){
            System.err.println("Wrong input type, accepted default value");
        }
    }

    private void edit6(){
        user = new Scanner(System.in);
        System.out.println("Enter a new senior discount time on discount day chance 0-1 (current " +
                this.getSENIOR_DISCOUNT_CHANCE() + ")");
        try{
            double chance = user.nextDouble();
            if(chance < 1 && chance > 0) {
                this.setSENIOR_DISCOUNT_CHANCE(chance);
            }
            else{
                System.out.println("Unaccepted value");
            }
        } catch (InputMismatchException e){
            System.err.println("Wrong input type, accepted default value");
        }
    }

    private void edit7(){
        user = new Scanner(System.in);
        System.out.println("Enter a new rush chance 0-1 (current " +
                this.getRUSH_CHANCE() + ")");
        try{
            double chance = user.nextDouble();
            if(chance < 1 && chance > 0) {
                this.setRUSH_CHANCE(chance);
            }
            else{
                System.out.println("Unaccepted value");
            }
        } catch (InputMismatchException e){
            System.err.println("Wrong input type, accepted default value");
        }
    }

    private void edit8(){
        user = new Scanner(System.in);
        System.out.println("Enter a new lunch start time (current " +
                this.getDEFAULT_LUNCH_TIMES()[0] + ", lasts 1 hour)");
        try{
            double lunch_time = user.nextDouble();
            if(lunch_time < 14 && lunch_time > 0) {
                this.setDEFAULT_LUNCH_TIMES(new Double[] {lunch_time, lunch_time + 1});
            }
            else{
                System.out.println("Unaccepted value");
            }
        } catch (InputMismatchException e){
            System.err.println("Wrong input type, accepted default value");
        }
    }

    private void edit9(){
        user = new Scanner(System.in);
        System.out.println("Enter a new lunch start time (current " +
                this.getDEFAULT_DINNER_TIMES()[0] + ", lasts 1.5 hours)");
        try{
            double dinner_time = user.nextDouble();
            if(dinner_time < 13.5 && dinner_time > 0) {
                this.setDEFAULT_DINNER_TIMES(new Double[] {dinner_time, dinner_time + 1.5});
            }
            else{
                System.out.println("Unaccepted value");
            }
        } catch (InputMismatchException e){
            System.err.println("Wrong input type, accepted default value");
        }
    }

    private void edit10(){
        user = new Scanner(System.in);
        System.out.println("Enter a new multiplier for nice weather on weekends (current " +
                this.getWEEKEND_NICE_WEATHER_MULT() + ")");
        try{
            double mult = user.nextDouble();
            if(mult > 0) {
                this.setWEEKEND_NICE_WEATHER_MULT(mult);
            }
            else{
                System.out.println("Unaccepted value");
            }
        } catch (InputMismatchException e){
            System.err.println("Wrong input type, accepted default value");
        }
    }

    private void edit11(){
        double hol1 = this.getHOLIDAY_MULT()[0];
        double hol2 = this.getHOLIDAY_MULT()[1];
        double hol3 = this.getHOLIDAY_MULT()[2];
        double here;
        this.setHOLIDAY_MULT(new Double[] {hol1, hol2, hol3});

        user = new Scanner(System.in);
        System.out.println("Enter a new multiplier week before holiday (current " + this.getHOLIDAY_MULT()[0] + ")");
        try{
            here = user.nextDouble();
            if(here > 0) {
                hol1 = here;
            }
            else{
                System.out.println("Unaccepted value");
            }
        } catch (InputMismatchException e){
            System.err.println("Wrong input type, accepted default value");
        }

        user = new Scanner(System.in);
        System.out.println("Enter a new multiplier for day before holiday (current " + this.getHOLIDAY_MULT()[1] + ")");
        try{
            here = user.nextDouble();
            if(here > 0) {
                hol2 = here;
            }
            else{
                System.out.println("Unaccepted value");
            }
        } catch (InputMismatchException e){
            System.err.println("Wrong input type, accepted default value");
        }

        user = new Scanner(System.in);
        System.out.println("Enter a new multiplier for day of holiday (current " + this.getHOLIDAY_MULT()[2] + ")");
        try{
            here = user.nextDouble();
            if(here > 0) {
                hol3 = here;
            }
            else{
                System.out.println("Unaccepted value");
            }
        } catch (InputMismatchException e){
            System.err.println("Wrong input type, accepted default value");
        }

        this.setHOLIDAY_MULT(new Double[] {hol1, hol2, hol3});
    }

    public void edit_values(int type){
        if(type != 0) {
            this.naming[type - 1] = "C";
        }
        switch(type) {
            case 1:
                editOne();
                break;
            case 2:
                edit2();
                break;
            case 3:
                edit3();
                break;
            case 4:
                edit4();
                break;
            case 5:
                edit5();
                break;
            case 6:
                edit6();
                break;
            case 7:
                edit7();
                break;
            case 8:
                edit8();
                break;
            case 9:
                edit9();
                break;
            case 10:
                edit10();
                break;
            case 11:
                edit11();
                break;
        }
    }

    public String getNaming(){
        return Arrays.toString(this.naming);
    }

    public String[] passNames(){
        return new String[] {"Senior Day", "Senior Chance", "Senior Times", "S. Discount Times",
                "S. Chance Discount Day", "S. Chance Time Day", "Rush Chance",
                "Lunch Times", "Dinner Times", "Weather Mult", "Holiday Mult"};
    }

    public String[] passValues(){
        return new String[] {String.valueOf(this.getSENIOR_DAY()), String.valueOf(this.getSENIOR_CHANCE()),
                Arrays.toString(this.getSENIOR_ENTER_TIME()), Arrays.toString(this.getSENIOR_DISCOUNT_TIME()),
                String.valueOf(this.getSENIOR_CHANCE_DISCOUNT_DAY()), String.valueOf(this.getSENIOR_DISCOUNT_CHANCE()),
                String.valueOf(this.getRUSH_CHANCE()), Arrays.toString(this.getDEFAULT_LUNCH_TIMES()),
                Arrays.toString(this.getDEFAULT_DINNER_TIMES()), String.valueOf(this.getWEEKEND_NICE_WEATHER_MULT()),
                Arrays.toString(this.getHOLIDAY_MULT())};
    }

}
