package ml.techcavern.waveline.utils;

import com.google.gson.JsonObject;

public class InfoUtils {
    public static String getWeather(String zipcode) throws Exception{
            JsonObject weather = GeneralUtils.getJsonObject("http://api.wunderground.com/api/c8e3fc38bfc8400c/conditions/q/" + zipcode + ".json").getAsJsonObject("current_observation");
            if (weather != null) {
                String City = weather.get("display_location").getAsJsonObject().get("full").getAsString();
                String Weather = weather.get("weather_card").getAsString();
                String Temp = weather.get("temperature_string").getAsString();
                String Humidity = weather.get("relative_humidity").getAsString() + " humidity";
                String Wind = weather.get("wind_string").getAsString();
                return "[" + City + "] " + Weather + " - " + Temp + " - " + Humidity + " - " + Wind;
            }else{
                return "Invalid Zip Code";
        }
    }

}
