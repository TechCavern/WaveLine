package ml.techcavern.waveline.commands.minecraft;

/**
 * Created by jzhou on 12/28/2015.
 */

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.List;

import ml.techcavern.waveline.annots.CMD;
import ml.techcavern.waveline.objects.Command;
import ml.techcavern.waveline.objects.OutputCard;
import ml.techcavern.waveline.utils.GeneralUtils;

@CMD
public class MCStatus extends Command {

    public MCStatus() {
        super(GeneralUtils.toArray("mcstatus mcs"), null, "Checks status of mojang servers");
    }

    @Override
    public void onCommand(Context context, String[] args) throws Exception {
        List<String> result = new ArrayList<>();
        JsonArray mcstatus = GeneralUtils.getJsonArray("https://status.mojang.com/check");
        String title = "Everything's Ok!";
        for (JsonElement status : mcstatus) {
            String name = status.getAsJsonObject().entrySet().iterator().next().getKey().toString();
            if (name.equalsIgnoreCase("minecraft.net")) {
                name = "Website";
            } else if (name.equalsIgnoreCase("api.mojang.com")) {
                name = "API";
            } else if (name.equalsIgnoreCase("authserver.mojang.com")) {
                name = "AuthServer";
            } else if (name.equalsIgnoreCase("sessionserver.mojang.com")) {
                name = "SessionServer";
            } else {
                name = WordUtils.capitalize(name.replace(".minecraft.net", "").replace(".mojang.com", ""));
            }
            String value = status.getAsJsonObject().entrySet().iterator().next().getValue().getAsString();
            if (value.equalsIgnoreCase("green")) {
                value = "Online";
            } else if (value.equalsIgnoreCase("yellow")) {
                value = "Overloaded";
                title = "Uh Oh! Overloaded";
            } else {
                title = "Uh Oh! Something's Down";
                value = "Offline";
            }
            result.add(name + ": " + value);
        }
        if (result != null) {
            GeneralUtils.addCard(new OutputCard(context, title, StringUtils.join(result, " - ")));

        } else {
            GeneralUtils.addCard(new OutputCard(context, "Failed to get Status", "MC status currently unavailable"));
        }

    }
}
