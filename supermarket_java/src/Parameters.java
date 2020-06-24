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
    private Double[] LUNCH_TIMES = {6d, 7d};
    private Double[] DINNER_TIMES = {11d, 12.5};
    private double WEEKEND_NICE_WEATHER_MULT = 1.4;
    private Double[] HOLIDAY_MULT = {1.15, 1.40, 0.2};

    private String[] naming = {"D", "D", "D", "D", "D", "D", "D", "D", "D", "D", "D"};
    private Scanner user;

    public Double[] getHOLIDAY_MULT() {
        return HOLIDAY_MULT;
    }

    public double getWEEKEND_NICE_WEATHER_MULT() {
        return WEEKEND_NICE_WEATHER_MULT;
    }

    public Double[] getLUNCH_TIMES() {
        return LUNCH_TIMES;
    }

    public Double[] getDINNER_TIMES() {
        return DINNER_TIMES;
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

    public void editSENIOR_DAY(){
        user = new Scanner(System.in);
        System.out.println("Enter a new senior day 1-7 (current " + this.getSENIOR_DAY() + ")");
        try{
            int day = user.nextInt();
            if(day <= 7 && day >= 1) {
                this.SENIOR_DAY = day;
            }
            else{
                System.out.println("Unaccepted value");
            }
        } catch (InputMismatchException e){
            System.err.println("Wrong input type, accepted default value");
        }
    }

    private void editSENIOR_CHANCE(){
        user = new Scanner(System.in);
        System.out.println("Enter a new senior chance 0-1 (current " + this.getSENIOR_CHANCE() + ")");
        try{
            double chance = user.nextDouble();
            if(chance < 1 && chance > 0) {
                this.SENIOR_CHANCE = chance;
            }
            else{
                System.out.println("Unaccepted value");
            }
        } catch (InputMismatchException e){
            System.err.println("Wrong input type, accepted default value");
        }
    }

    public void editSENIOR_ENTER_TIME(){
        user = new Scanner(System.in);
        System.out.println("Enter a new last senior in time 0-12 (current " + SENIOR_ENTER_TIME[1] + ")");
        try{
            int time = user.nextInt();
            if(time < 13 && time > 0) {
                this.SENIOR_ENTER_TIME = new int[] {0, time};
            }
            else{
                System.out.println("Unaccepted value");
            }
        } catch (InputMismatchException e){
            System.err.println("Wrong input type, accepted default value");
        }
    }

    public void editSENIOR_DISCOUNT_TIME(){
        user = new Scanner(System.in);
        System.out.println("Enter a new senior discount start time 0-13 (current " +
                SENIOR_DISCOUNT_TIME[0] +", discount lasts 2 hours)");
        try{
            int time = user.nextInt();
            if(time < 13 && time > 0) {
                this.SENIOR_DISCOUNT_TIME = new int[] {time, time + 2};
            }
            else{
                System.out.println("Unaccepted value");
            }
        } catch (InputMismatchException e){
            System.err.println("Wrong input type, accepted default value");
        }
    }

    private void editSENIOR_CHANCE_DISCOUNT_DAY(){
        user = new Scanner(System.in);
        System.out.println("Enter a new senior discount day chance 0-1 (current " +
                this.getSENIOR_CHANCE_DISCOUNT_DAY() + ")");
        try{
            double chance = user.nextDouble();
            if(chance < 1 && chance > 0) {
                this.SENIOR_CHANCE_DISCOUNT_DAY = chance;
            }
            else{
                System.out.println("Unaccepted value");
            }
        } catch (InputMismatchException e){
            System.err.println("Wrong input type, accepted default value");
        }
    }

    private void editSENIOR_DISCOUNT_CHANCE(){
        user = new Scanner(System.in);
        System.out.println("Enter a new senior discount time on discount day chance 0-1 (current " +
                this.getSENIOR_DISCOUNT_CHANCE() + ")");
        try{
            double chance = user.nextDouble();
            if(chance < 1 && chance > 0) {
                this.SENIOR_DISCOUNT_CHANCE = chance;
            }
            else{
                System.out.println("Unaccepted value");
            }
        } catch (InputMismatchException e){
            System.err.println("Wrong input type, accepted default value");
        }
    }

    private void editRUSH_CHANCE(){
        user = new Scanner(System.in);
        System.out.println("Enter a new rush chance 0-1 (current " +
                this.getRUSH_CHANCE() + ")");
        try{
            double chance = user.nextDouble();
            if(chance < 1 && chance > 0) {
                this.RUSH_CHANCE = chance;
            }
            else{
                System.out.println("Unaccepted value");
            }
        } catch (InputMismatchException e){
            System.err.println("Wrong input type, accepted default value");
        }
    }

    private void editLUNCH_TIMES(){
        user = new Scanner(System.in);
        System.out.println("Enter a new lunch start time (current " +
                this.getLUNCH_TIMES()[0] + ", lasts 1 hour)");
        try{
            double lunch_time = user.nextDouble();
            if(lunch_time < 14 && lunch_time > 0) {
                this.LUNCH_TIMES = new Double[] {lunch_time, lunch_time + 1};
            }
            else{
                System.out.println("Unaccepted value");
            }
        } catch (InputMismatchException e){
            System.err.println("Wrong input type, accepted default value");
        }
    }

    private void editDINNER_TIMES(){
        user = new Scanner(System.in);
        System.out.println("Enter a new lunch start time (current " +
                this.getDINNER_TIMES()[0] + ", lasts 1.5 hours)");
        try{
            double dinner_time = user.nextDouble();
            if(dinner_time < 13.5 && dinner_time > 0) {
                this.DINNER_TIMES = new Double[] {dinner_time, dinner_time + 1.5};
            }
            else{
                System.out.println("Unaccepted value");
            }
        } catch (InputMismatchException e){
            System.err.println("Wrong input type, accepted default value");
        }
    }

    private void editWEATHER_MULT(){
        user = new Scanner(System.in);
        System.out.println("Enter a new multiplier for nice weather on weekends (current " +
                this.getWEEKEND_NICE_WEATHER_MULT() + ")");
        try{
            double mult = user.nextDouble();
            if(mult > 0) {
                this.WEEKEND_NICE_WEATHER_MULT = mult;
            }
            else{
                System.out.println("Unaccepted value");
            }
        } catch (InputMismatchException e){
            System.err.println("Wrong input type, accepted default value");
        }
    }

    private void editHOLIDAY_MULT(){
        double hol1 = this.getHOLIDAY_MULT()[0];
        double hol2 = this.getHOLIDAY_MULT()[1];
        double hol3 = this.getHOLIDAY_MULT()[2];
        double here;

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

        this.HOLIDAY_MULT = new Double[] {hol1, hol2, hol3};
    }

    public void edit_values(int type){
        if(type != 0) {
            this.naming[type - 1] = "C";
        }
        switch(type) {
            case 1:
                editSENIOR_DAY();
                break;
            case 2:
                editSENIOR_CHANCE();
                break;
            case 3:
                editSENIOR_ENTER_TIME();
                break;
            case 4:
                editSENIOR_DISCOUNT_TIME();
                break;
            case 5:
                editSENIOR_CHANCE_DISCOUNT_DAY();
                break;
            case 6:
                editSENIOR_DISCOUNT_CHANCE();
                break;
            case 7:
                editRUSH_CHANCE();
                break;
            case 8:
                editLUNCH_TIMES();
                break;
            case 9:
                editDINNER_TIMES();
                break;
            case 10:
                editWEATHER_MULT();
                break;
            case 11:
                editHOLIDAY_MULT();
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
                String.valueOf(this.getRUSH_CHANCE()), Arrays.toString(this.getLUNCH_TIMES()),
                Arrays.toString(this.getDINNER_TIMES()), String.valueOf(this.getWEEKEND_NICE_WEATHER_MULT()),
                Arrays.toString(this.getHOLIDAY_MULT())};
    }

}
