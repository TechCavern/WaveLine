package ml.techcavern.waveline.commands.media;

/**
 * Created by jzhou on 12/28/2015.
 */

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Random;

import ml.techcavern.waveline.annots.CMD;
import ml.techcavern.waveline.objects.Command;
import ml.techcavern.waveline.objects.OutputCard;
import ml.techcavern.waveline.utils.GeneralUtils;

@CMD
public class Reddit extends Command {

    public Reddit() {
        super(GeneralUtils.toArray("reddit re subreddit"), "reddit [subreddit]", "gets a random message from a subreddit");
    }

    @Override
    public void onCommand(Context context, String[] args) throws Exception {
        JsonArray results = GeneralUtils.getJsonObject("http://api.reddit.com/r/" + args[1] + "/?limit=25").get("data").getAsJsonObject().get("children").getAsJsonArray();
        JsonObject result = results.get(new Random().nextInt(results.size() - 1)).getAsJsonObject().get("data").getAsJsonObject();
        GeneralUtils.addCard(new OutputCard(context, result.get("title").getAsString() + " by " + result.get("author").getAsString(), result.get("selftext").getAsString() + " - " + result.get("url").getAsString()));
    }
}
