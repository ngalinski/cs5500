import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Json_reader {

    private static HashMap<String, Double> parameters = new HashMap<>();

    public Json_reader(){
        this.fillParameters();
        this.read_json();
    }

    public Double getValue(String key){
        return parameters.get(key);
    }
    
    private void fillParameters() {
        parameters.put("STORE_OPEN", 0d);
        parameters.put("STORE_CLOSE", 15d);
        parameters.put("NUM_SHOPPERS", 0d);
        parameters.put("SENIOR_DAY", 2d);
        parameters.put("SENIOR_CHANCE", 0.16);
        parameters.put("LAST_SENIOR_IN_TIME", 12d);
        parameters.put("SENIOR_DISCOUNT_TIME_START", 4d);
        parameters.put("SENIOR_DISCOUNT_TIME_END", 6d);

        parameters.put("RUSH_CHANCE", 0.80);
        parameters.put("LUNCH_START", 6d);
        parameters.put("LUNCH_END", 7d);
        parameters.put("DINNER_START", 11d);
        parameters.put("DINNER_END", 12.5);

        parameters.put("WEEKEND_NICE_WEATHER_MULT", 1.4);
        parameters.put("WEEK_HOLIDAY_MULT", 1.5);
        parameters.put("DAY_HOLIDAY_MULT", 1.4);
        parameters.put("HOLIDAY_MULT", 0.2);
        parameters.put("CUSTOM_MULT", 1.0);
    }
    
    private void read_json() {
        Json_reader json_reader = new Json_reader();
        json_reader.fillParameters();

        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try {
            FileReader reader = new FileReader("parameters.json");
            //Read JSON file
            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            Set<String> keys = obj.keySet();
            for(String key : keys){
                if(parameters.containsKey(key)){
                    parameters.replace(key, (Double) obj.get(key));
                }
            }
            for(String key : parameters.keySet()){
                System.out.println(key + ": " + parameters.get(key).toString());
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }



}
