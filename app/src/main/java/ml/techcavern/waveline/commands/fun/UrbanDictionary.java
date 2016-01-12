package ml.techcavern.waveline.commands.fun;

/**
 * Created by jzhou on 12/28/2015.
 */

import android.content.Context;

import com.google.gson.JsonObject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import ml.techcavern.waveline.annots.CMD;
import ml.techcavern.waveline.objects.Command;
import ml.techcavern.waveline.objects.OutputCard;
import ml.techcavern.waveline.utils.GeneralUtils;

@CMD
public class UrbanDictionary extends Command {

    public UrbanDictionary() {
        super(GeneralUtils.toArray("urbandictionary ub urban urb ud"), "urbandictionary (def #) [what to define]", "Defines a term in the urban dictionary");

    }

    @Override
    public void onCommand(Context context, String[] args) throws Exception {
        args = ArrayUtils.remove(args, 0);
        int ArrayIndex = 0;
        if (GeneralUtils.isInteger(args[0])) {
            ArrayIndex = Integer.parseInt(args[0]) - 1;
            args = ArrayUtils.remove(args, 0);
        }
        JsonObject objectJSON = GeneralUtils.getJsonObject("http://api.urbandictionary.com/v0/define?term=" + StringUtils.join(args).replaceAll(" ", "%20"));
        if (objectJSON.getAsJsonArray("list").size() > 0) {
            if (objectJSON.getAsJsonArray("list").size() - 1 >= ArrayIndex) {
                String Word = WordUtils.capitalizeFully(objectJSON.getAsJsonArray("list").get(ArrayIndex).getAsJsonObject().get("word").getAsString().replaceAll("\\n|\\r|\\t", " ").replaceAll("  ", " "));
                String Definition = objectJSON.getAsJsonArray("list").get(ArrayIndex).getAsJsonObject().get("definition").getAsString().replaceAll("\\n|\\r|\\t", " ").replaceAll("  ", " ");
                String Examples = objectJSON.getAsJsonArray("list").get(ArrayIndex).getAsJsonObject().get("example").getAsString().replaceAll("\\n|\\r|\\t", " ").replaceAll("  ", " ");
                if (!Examples.isEmpty()) {
                    GeneralUtils.addCard(new OutputCard(context, Word, Definition + "\n\nExamples: " + Examples));
                } else {
                    GeneralUtils.addCard(new OutputCard(context, Word, Definition));

                }
            } else {
                ArrayIndex = ArrayIndex + 1;
                GeneralUtils.addCard(new OutputCard(context, "Error", "Def #" + ArrayIndex + " does not exist"));
            }
        } else {
            GeneralUtils.addCard(new OutputCard(context, "Error", "Not defined in the urban dictionary"));
        }

    }
}
