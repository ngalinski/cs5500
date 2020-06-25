import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class ParametersTest {

    // Default values
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

    private int[] test;
    private Double[] test2;

    @org.junit.jupiter.api.Test
    void edit_values() {
        Parameters parameters = new Parameters();
        System.setIn(new StringBufferInputStream("4"));
        parameters.edit_values(1);
        assertEquals(parameters.getSENIOR_DAY(), 4);

        System.setIn(new StringBufferInputStream("0.2"));
        parameters.edit_values(2);
        assertEquals(parameters.getSENIOR_CHANCE(), 0.2);

        System.setIn(new StringBufferInputStream("3"));
        parameters.edit_values(3);
        assertArrayEquals(parameters.getSENIOR_ENTER_TIME(), new int[] {0, 3});

        System.setIn(new StringBufferInputStream("7"));
        parameters.edit_values(4);
        assertArrayEquals(parameters.getSENIOR_DISCOUNT_TIME(), new int[] {7, 9});

        System.setIn(new StringBufferInputStream("0.7"));
        parameters.edit_values(5);
        assertEquals(parameters.getSENIOR_CHANCE_DISCOUNT_DAY(), 0.7);

        System.setIn(new StringBufferInputStream("0.44"));
        parameters.edit_values(6);
        assertEquals(parameters.getSENIOR_DISCOUNT_CHANCE(), 0.44);

        System.setIn(new StringBufferInputStream("0.15"));
        parameters.edit_values(7);
        assertEquals(parameters.getRUSH_CHANCE(), 0.15);

        System.setIn(new StringBufferInputStream("5"));
        parameters.edit_values(8);
        assertArrayEquals(parameters.getLUNCH_TIMES(), new Double[] {5d, 6d});

        System.setIn(new StringBufferInputStream("12"));
        parameters.edit_values(9);
        assertArrayEquals(parameters.getDINNER_TIMES(), new Double[] {12d, 13.5d});

        System.setIn(new StringBufferInputStream("2.5"));
        parameters.edit_values(10);
        assertEquals(parameters.getWEEKEND_NICE_WEATHER_MULT(), 2.5);
    }

    @Test
    void check_defaults() {
        Parameters parameters = new Parameters();
        assertEquals(parameters.getSENIOR_DAY(), SENIOR_DAY);
        assertEquals(parameters.getSENIOR_CHANCE(), SENIOR_CHANCE);
        assertArrayEquals(parameters.getSENIOR_ENTER_TIME(), SENIOR_ENTER_TIME);
        assertArrayEquals(parameters.getSENIOR_DISCOUNT_TIME(), SENIOR_DISCOUNT_TIME);
        assertEquals(parameters.getSENIOR_CHANCE_DISCOUNT_DAY(), SENIOR_CHANCE_DISCOUNT_DAY);
        assertEquals(parameters.getSENIOR_DISCOUNT_CHANCE(), SENIOR_DISCOUNT_CHANCE);
        assertEquals(parameters.getRUSH_CHANCE(), RUSH_CHANCE);
        assertArrayEquals(parameters.getLUNCH_TIMES(), LUNCH_TIMES);
        assertArrayEquals(parameters.getDINNER_TIMES(), DINNER_TIMES);
        assertEquals(parameters.getWEEKEND_NICE_WEATHER_MULT(), WEEKEND_NICE_WEATHER_MULT);
        assertArrayEquals(parameters.getHOLIDAY_MULT(), HOLIDAY_MULT);
    }
}