package ml.techcavern.waveline.commands.dnsinfo;

/**
 * Created by jzhou on 12/28/2015.
 */

import android.content.Context;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import ml.techcavern.waveline.annots.CMD;
import ml.techcavern.waveline.objects.Command;
import ml.techcavern.waveline.objects.OutputCard;
import ml.techcavern.waveline.utils.GeneralUtils;
import ml.techcavern.waveline.utils.Registry;

@CMD
public class GeoIP extends Command {

    public GeoIP() {
        super(GeneralUtils.toArray("findip locate fip find loc geo geoip"), "findip (+)(IP)(domain)(user)", "GeoIPs a user");
    }

    @Override
    public void onCommand(Context context, String[] args) throws Exception {
        String IP = args[0];
        IP = GeneralUtils.getIP(IP);
        if (IP == null) {
            Registry.cardList.add(new OutputCard(context, "Invalid", "Invalid IP"));
            return;
        }
        JsonObject objectJson = GeneralUtils.getJsonObject("http://ip-api.com/json/" + IP);
        List<String> results = new ArrayList<>();
        if (objectJson.get("status").getAsString().equalsIgnoreCase("success")) {
            results.add(objectJson.get("city").getAsString());
            results.add(objectJson.get("regionName").getAsString());
            results.add(objectJson.get("country").getAsString());
            results.add(objectJson.get("zip").getAsString());
            results.add(objectJson.get("isp").getAsString());
            results.add(objectJson.get("timezone").getAsString());
            String message = "";
            for (String res : results) {
                if (!res.isEmpty()) {
                    if (message.isEmpty()) {
                        message = res;
                    } else {
                        message += ", " + res;
                    }
                }
            }
            if (!message.isEmpty()) {
                Registry.cardList.add(new OutputCard(context, "IP", message));
            } else {

                Registry.cardList.add(new OutputCard(context, "Error", "Unable to determine location (or you entered an invalid ip)"));
            }
        } else {
            Registry.cardList.add(new OutputCard(context, "Error", "Unable to determine location (or you entered an invalid ip)"));
        }


    }
}
