package postman;

import javax.persistence.*;

@Entity
@Table(name="july_07_2020_created_07_08_2020_18_58_54")
public class Shopper {

    private Integer ID;
    private double time_entered;
    private double time_spent;
    private String rush;
    private boolean senior;

    public Shopper() {
    }

    public Shopper(Integer id, double time_entered, double time_spent, String rush, boolean senior) {
        this.ID = id;
        this.time_entered = time_entered;
        this.time_spent = time_spent;
        this.rush = rush;
        this.senior = senior;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return ID;
    }

    public void setId(Integer id) {
        this.ID = id;
    }

    public double getTime_entered() {
        return time_entered;
    }

    public void setTime_entered(double time_entered) {
        this.time_entered = time_entered;
    }

    public double getTime_spent() {
        return time_spent;
    }

    public void setTime_spent(double time_spent) {
        this.time_spent = time_spent;
    }

    public String getRush() {
        return rush;
    }

    public void setRush(String rush) {
        this.rush = rush;
    }

    public boolean isSenior() {
        return senior;
    }

    public void setSenior(boolean senior) {
        this.senior = senior;
    }
}



