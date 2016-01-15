package ml.techcavern.waveline.commands.misc;

/**
 * Created by jzhou on 12/28/2015.
 */

import android.content.Context;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import ml.techcavern.waveline.annots.CMD;
import ml.techcavern.waveline.objects.Command;
import ml.techcavern.waveline.objects.OutputCard;
import ml.techcavern.waveline.utils.DatabaseUtils;
import ml.techcavern.waveline.utils.GeneralUtils;

@CMD
public class Test extends Command {
    private static Map<JsonObject, String> mcmods = new HashMap<>();

    public Test() {
        super(GeneralUtils.toArray("test test2"), "test", "test");
    }


    @Override
    public void onCommand(Context context, String[] args) throws Exception {
        if (args[0].equalsIgnoreCase("test")) {
            DatabaseUtils.addConfig(context, "moo", "moo2");
            GeneralUtils.addCard(new OutputCard(context, DatabaseUtils.getConfig(context, "moo"), DatabaseUtils.getConfig(context, "moo")));

        } else {
            DatabaseUtils.updateConfig(context, "moo", "moo3");
            GeneralUtils.addCard(new OutputCard(context, DatabaseUtils.getConfig(context, "moo"), DatabaseUtils.getConfig(context, "moo")));
        }
    }
}
