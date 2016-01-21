package ml.techcavern.waveline.commands.minecraft;

/**
 * Created by jzhou on 12/28/2015.
 */

import android.content.Context;

import com.google.gson.JsonArray;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import ml.techcavern.waveline.annots.CMD;
import ml.techcavern.waveline.objects.Command;
import ml.techcavern.waveline.objects.OutputCard;
import ml.techcavern.waveline.utils.GeneralUtils;

@CMD
public class MCModStats extends Command {

    public MCModStats() {
        super(GeneralUtils.toArray("mcmodstats mcms"), "mcmodstats", "Gets mod stats, ignores MC Versions with less than 10 mods");
    }

    @Override
    public void onCommand(Context context, String[] args) throws Exception {
        JsonArray versions = GeneralUtils.getJsonArray("http://bot.notenoughmods.com/?json&count");
        List<String> mcModStats = new ArrayList<>();
        for (int i = versions.size() - 1; i >= 0; i--) {
            int size = versions.get(i).getAsJsonObject().get("count").getAsInt();
            if (size > 10)
                mcModStats.add(versions.get(i).getAsJsonObject().get("name").getAsString() + ": " + size + " mods");
        }
        GeneralUtils.addCard(new OutputCard(context, "Mod Stats", StringUtils.join(mcModStats, " - ")));
    }
}
