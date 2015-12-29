package ml.techcavern.waveline.commands.reference;

/**
 * Created by jzhou on 12/28/2015.
 */

import android.content.Context;

import com.google.gson.JsonObject;

import ml.techcavern.waveline.annots.CMD;
import ml.techcavern.waveline.objects.Command;
import ml.techcavern.waveline.objects.OutputCard;
import ml.techcavern.waveline.utils.GeneralUtils;
import ml.techcavern.waveline.utils.Registry;

@CMD
    public class Weather extends Command {

        public Weather() {
            super(GeneralUtils.toArray("weather we temperature temp humid humidity wind wunderground wunder"), "weather [zipcode][city]", "Gets Weather in an area from wunderground");
        }

        @Override
        public void onCommand(Context context, String[] args) throws Exception {
            JsonObject weather = GeneralUtils.getJsonObject("http://api.wunderground.com/api/c8e3fc38bfc8400c/conditions/q/" + args[0] + ".json").getAsJsonObject("current_observation");
            if (weather != null) {
                String City = weather.get("display_location").getAsJsonObject().get("full").getAsString();
                String Weather = weather.get("weather").getAsString();
                String Temp = weather.get("temperature_string").getAsString();
                String Humidity = weather.get("relative_humidity").getAsString() + " humidity";
                String Wind = weather.get("wind_string").getAsString();
                Registry.cardList.add(new OutputCard(context, City, Weather + " - " + Temp + " - " + Humidity + " - " + Wind));
            }else{
                Registry.cardList.add(new OutputCard(context, "Invalid City/Zip Code", "Invalid City/Zip Code"));
            }
        }

    }
