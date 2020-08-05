# CS5500

Supermarkets is the project delivery. 
1. Open in any kind of editor.
2. Edit the web.xml file to your sql credentials.
3. Edit any json parameters in parameters.json.
4. Run app using tomcat 8.5.57. 
5. This should open a jsp webpage with more information. 

Json parameter instructions.
1. You can edit the following parameters.
2. Each parameter much be followed in naming and all must be listed as doubles. 
3. Below are the names and default values of parameters. 
STORE_OPEN 0d  
STORE_CLOSE 15d  
NUM_SHOPPERS 0d  
SENIOR_DAY 2d  
SENIOR_CHANCE 0.16  
LAST_SENIOR_IN_TIME 12d
SENIOR_DISCOUNT_TIME_START 4d
SENIOR_DISCOUNT_TIME_END 6d
RUSH_CHANCE 0.80
LUNCH_START 6d
LUNCH_END 7d
DINNER_START 11d
DINNER_END 12.5
WEEKEND_NICE_WEATHER_MULT 1.4
WEEK_HOLIDAY_MULT 1.5
DAY_HOLIDAY_MULT 1.4
HOLIDAY_MULT 0.2
CUSTOM_MULT 1.0

Test metrics and graphs are within the code mr folders inside of the supermarket class.

Known Problems
1. You must enter things into the jsp with care. 
2. Things for numbers must only be numbers and strings must always be strings.
3. If you enter a blank name into table query page, the jsp will crash because there is a null name. 
