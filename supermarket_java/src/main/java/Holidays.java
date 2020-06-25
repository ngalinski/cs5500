import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * Creates a list of holidays
 * Don't worry too much about how this works, since it does
 */
public class Holidays{

    private final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    private LocalDate new_years (int year){
        return LocalDate.of(year,1,1);
    }

    private LocalDate valentines_day (int year){
        return LocalDate.of(year,2,14);
    }

    private LocalDate mlk_day (int year)
    {
        int month = 1;
        return getLocalDate(year, month);
    }

    private LocalDate presidents_day (int year)
    {
        int month = 2;
        return getLocalDate(year, month);
    }

    private LocalDate getLocalDate(int year, int month) {
        LocalDate dtD = LocalDate.of(year, month, 1);
        DayOfWeek day = dtD.getDayOfWeek();
        switch(Arrays.asList(DAYS).indexOf(day))
        {
            case 0 : // Sunday
                return LocalDate.of(year, month, 16);
            case 1 : // Monday
                return LocalDate.of(year, month, 15);
            case 2 : // Tuesday
                return LocalDate.of(year, month, 21);
            case 3 : // Wednesday
                return LocalDate.of(year, month, 20);
            case 4 : // Thursday
                return LocalDate.of(year, month, 19);
            case 5 : // Friday
                return LocalDate.of(year, month, 18);
            default : // Saturday
                return LocalDate.of(year, month, 17);
        }
    }

    private LocalDate memorial_day (int year)
    {
        int month = 5;
        LocalDate dtD = LocalDate.of(year, month, 31);
        DayOfWeek day = dtD.getDayOfWeek();
        switch(Arrays.asList(DAYS).indexOf(day))
        {
            case 0 : // Sunday
                return LocalDate.of(year, month, 25);
            case 1 : // Monday
                return LocalDate.of(year, month, 31);
            case 2 : // Tuesday
                return LocalDate.of(year, month, 30);
            case 3 : // Wednesday
                return LocalDate.of(year, month, 29);
            case 4 : // Thursday
                return LocalDate.of(year, month, 28);
            case 5 : // Friday
                return LocalDate.of(year, month, 27);
            default : // Saturday
                return LocalDate.of(year, month, 26);
        }
    }

    private LocalDate independence_day (int year){
        return LocalDate.of(year,7,4);
    }

    private LocalDate labor_day (int year)
    {
        int month = 9;
        LocalDate dtD = LocalDate.of(year, month, 1);
        DayOfWeek day = dtD.getDayOfWeek();
        switch(Arrays.asList(DAYS).indexOf(day))
        {
            case 0 : // Sunday
                return LocalDate.of(year, month, 2);
            case 1 : // Monday
                return LocalDate.of(year, month, 1);
            case 2 : // Tuesday
                return LocalDate.of(year, month, 7);
            case 3 : // Wednesday
                return LocalDate.of(year, month, 6);
            case 4 : // Thursday
                return LocalDate.of(year, month, 5);
            case 5 : // Friday
                return LocalDate.of(year, month, 4);
            default : // Saturday
                return LocalDate.of(year, month, 3);
        }
    }

    private LocalDate columbus_day (int year)
    {
        int month = 10;
        LocalDate dtD = LocalDate.of(year, month, 1);
        DayOfWeek day = dtD.getDayOfWeek();
        switch(Arrays.asList(DAYS).indexOf(day))
        {
            case 0 : // Sunday
                return LocalDate.of(year, month, 9);
            case 1 : // Monday
                return LocalDate.of(year, month, 8);
            case 2 : // Tuesday
                return LocalDate.of(year, month, 14);
            case 3 : // Wednesday
                return LocalDate.of(year, month, 13);
            case 4 : // Thursday
                return LocalDate.of(year, month, 12);
            case 5 : // Friday
                return LocalDate.of(year, month, 11);
            default : // Saturday
                return LocalDate.of(year, month, 10);
        }
    }

    private LocalDate halloween (int year){
        return LocalDate.of(year,10,31);
    }

    private LocalDate veterans_day (int year){
        return LocalDate.of(year,11,11);
    }

    private LocalDate thanksgiving(int year)
    {
        int month = 11;
        LocalDate dtD = LocalDate.of(year, month, 1);
        DayOfWeek day = dtD.getDayOfWeek();
        switch(Arrays.asList(DAYS).indexOf(day))
        {
            case 0 : // Sunday
                return LocalDate.of(year, month, 26);
            case 1 : // Monday
                return LocalDate.of(year, month, 25);
            case 2 : // Tuesday
                return LocalDate.of(year, month, 24);
            case 3 : // Wednesday
                return LocalDate.of(year, month, 23);
            case 4 : // Thursday
                return LocalDate.of(year, month, 22);
            case 5 : // Friday
                return LocalDate.of(year, month, 28);
            default : // Saturday
                return LocalDate.of(year, month, 27);
        }
    }

    private LocalDate christmas_day (int year){
        return LocalDate.of(year,12,25);
    }

    private LocalDate new_years_eve (int year){
        return LocalDate.of(year,12,31);
    }

    public List<LocalDate> collect_holidays(int year){
        List<LocalDate> holidays = new ArrayList<>();
        holidays.add(this.new_years(year));
        holidays.add(this.valentines_day(year));
        holidays.add(this.mlk_day(year));
        holidays.add(this.presidents_day(year));
        holidays.add(this.memorial_day(year));
        holidays.add(this.independence_day(year));
        holidays.add(this.labor_day(year));
        holidays.add(this.columbus_day(year));
        holidays.add(this.halloween(year));
        holidays.add(this.veterans_day(year));
        holidays.add(this.thanksgiving(year));
        holidays.add(this.christmas_day(year));
        holidays.add(this.new_years_eve(year));
        return holidays;
    }
}

