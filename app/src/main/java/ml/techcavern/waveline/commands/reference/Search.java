package ml.techcavern.waveline.commands.reference;

/**
 * Created by jzhou on 12/28/2015.
 */

import android.content.Context;

import com.google.gson.JsonArray;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import ml.techcavern.waveline.annots.CMD;
import ml.techcavern.waveline.objects.Command;
import ml.techcavern.waveline.objects.OutputCard;
import ml.techcavern.waveline.utils.GeneralUtils;

@CMD
public class Search extends Command {

    public Search() {
        super(GeneralUtils.toArray("search google g goog gsearch"), "search (result #) [string to google]", "Googles something");

    }

    @Override
    public void onCommand(Context context, String[] args) throws Exception {
        args = ArrayUtils.remove(args, 0);
        int ArrayIndex = 0;
        if (GeneralUtils.isInteger(args[0])) {
            ArrayIndex = Integer.parseInt(args[0]) - 1;
            args = ArrayUtils.remove(args, 0);
        }
        JsonArray results = GeneralUtils.getJsonObject("https://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=" + StringUtils.join(args, "%20")).getAsJsonObject("responseData").getAsJsonArray("results");
        if (results.size() > 0) {
            if (results.size() - 1 >= ArrayIndex) {
                String title = results.get(ArrayIndex).getAsJsonObject().get("titleNoFormatting").getAsString();
                String content = GeneralUtils.stripHTML(results.get(ArrayIndex).getAsJsonObject().get("content").getAsString());
                String url = results.get(ArrayIndex).getAsJsonObject().get("unescapedUrl").getAsString();
                GeneralUtils.addCard(new OutputCard(context, title, content + " Read full article here: " + url));
            } else {
                ArrayIndex = ArrayIndex + 1;
                GeneralUtils.addCard(new OutputCard(context, "Error", "Search #" + ArrayIndex + " does not exist"));
            }
        } else {
            GeneralUtils.addCard(new OutputCard(context, "Error", "No Results Found"));
        }
    }

}
