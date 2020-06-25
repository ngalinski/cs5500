import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class Stats_writer {

    public Stats_writer() {
    }

    /**
     * Generate mean of arraylist
     * @param table ArrayList of doubles
     * @return mean of arraylist
     */
    private double mean(ArrayList<Double> table)
    {
        Double total = 0.0;

        for (Double value : table)
        {
            total+= value;
        }

        double mean = total/table.size();
        DecimalFormat df = new DecimalFormat("#.##");
        df.format(mean);

        return mean;
    }

    /**
     * Generate standard deviation of arraylist
     * @param table ArrayList of doubles
     * @return standard deviation of table
     */
    private double sd(ArrayList<Double> table)
    {
        double mean = mean(table);
        double temp = 0;

        for (Double value : table)
        {
            double squareDiffToMean = Math.pow(value - mean, 2);
            temp += squareDiffToMean;
        }

        double meanOfDiffs = temp / (double) (table.size());
        double sd = Math.sqrt(meanOfDiffs);
        DecimalFormat df = new DecimalFormat("#.##");
        df.format(sd);

        return sd;
    }

    /**
     * This method is for generating a statistics csv for the specific date
     * Customer can call this method if they want
     * @param date LocalDate type of date to be looked at
     * @throws IOException If file does not exist
     */
    void generate_statistics(LocalDate date) throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader(date.getDayOfWeek() + ".csv"));
        String row;

        int lunch_customers = 0;
        int dinner_customers = 0;
        int senior_customers = 0;
        int normal_customers = 0;

        csvReader.readLine();

        ArrayList<Double> lunch_times = new ArrayList<>();
        ArrayList<Double> dinner_times = new ArrayList<>();
        ArrayList<Double> senior_times = new ArrayList<>();
        ArrayList<Double> normal_times = new ArrayList<>();

        Integer[] new_customers = new Integer[16];
        Integer[] customers_in_store = new Integer[16];
        Arrays.fill(new_customers, 0);
        Arrays.fill(customers_in_store, 0);

        while ((row = csvReader.readLine()) != null){
            String[] data = row.split(",");
            double time_spent = Double.parseDouble(data[1]);
            switch (data[2]){
                case "Lunch":
                    lunch_customers += 1;
                    lunch_times.add(time_spent);
                    break;
                case "Dinner":
                    dinner_customers += 1;
                    dinner_times.add(time_spent);
                    break;
                case "Senior":
                    senior_customers += 1;
                    senior_times.add(time_spent);
                    break;
                default:
                    normal_customers += 1;
                    normal_times.add(time_spent);
            }

            double time_entered = Double.parseDouble(data[0]);
            new_customers[(int) time_entered] += 1;

            for(int i = (int) time_entered; i <= (int)(time_entered + (time_spent / 60)); i++){
                customers_in_store[i] += 1;
            }
        }
        csvReader.close();

        FileWriter csvWriter = new FileWriter(date.getDayOfWeek() + "_statistics.csv");
        int total_customers = 0;

        csvWriter.append(String.valueOf(date)).append(" Statistics\n");
        csvWriter.append("6 to 7,7 to 8,8 to 9,9 to 10,10 to 11," +
                "11 to 12,12 to 1,1 to 2,2 to 3,3 to 4," +
                "4 to 5,5 to 6,6 to 7,7 to 8,8 to 9,Closing\n");

        csvWriter.append("New customers per hour: ,");
        for (int number : new_customers) {
            total_customers += number;
            csvWriter.append(String.valueOf(number)).append(",");
        }

        csvWriter.append("\nCustomers in store per hour: ,");
        for (int in_store : customers_in_store) {
            csvWriter.append(String.valueOf(in_store)).append(",");
        }

        csvWriter.append("\nTotal customers: ,").append(String.valueOf(total_customers));
        csvWriter.append("\nLunch: ,").append(String.valueOf(lunch_customers));
        csvWriter.append("\nDinner: ,").append(String.valueOf(dinner_customers));
        csvWriter.append("\nSenior: ,").append(String.valueOf(senior_customers));
        csvWriter.append("\nNormal: ,").append(String.valueOf(normal_customers));
        csvWriter.append("\nCustomers in store at closing: ,").append(String.valueOf(customers_in_store[customers_in_store.length - 1]));

        csvWriter.append("\nLunch mean: ,").append(String.valueOf(mean(lunch_times)));
        csvWriter.append("\nLunch std: ,").append(String.valueOf(sd(lunch_times)));
        csvWriter.append("\nDinner mean: ,").append(String.valueOf(mean(dinner_times)));
        csvWriter.append("\nDinner std: ,").append(String.valueOf(sd(dinner_times)));
        csvWriter.append("\nSenior mean: ,").append(String.valueOf(mean(senior_times)));
        csvWriter.append("\nSenior std: ,").append(String.valueOf(sd(senior_times)));
        csvWriter.append("\nNormal mean: ,").append(String.valueOf(mean(normal_times)));
        csvWriter.append("\nNormal std: ,").append(String.valueOf(sd(normal_times)));

        csvWriter.flush();
        csvWriter.close();
    }
}
