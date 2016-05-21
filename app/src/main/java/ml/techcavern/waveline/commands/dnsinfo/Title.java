package ml.techcavern.waveline.commands.dnsinfo;

/**
 * Created by jzhou on 12/28/2015.
 */

import android.content.Context;

import org.apache.commons.lang3.ArrayUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import ml.techcavern.waveline.annots.CMD;
import ml.techcavern.waveline.objects.Command;
import ml.techcavern.waveline.objects.OutputCard;
import ml.techcavern.waveline.utils.GeneralUtils;
import ml.techcavern.waveline.utils.Registry;

@CMD
public class Title extends Command {

    public Title() {
        super(GeneralUtils.toArray("title ti"), "title [url]", "gets title");
    }

    @Override
    public void onCommand(Context context, String[] args) throws Exception {
        args = ArrayUtils.remove(args, 0);
        if (args[0].startsWith("http://")) {
            args[0].replace("http://", "");
        } else if (args[0].startsWith("https://")) {
            args[0].replace("https://", "");
        }
        Document doc = Jsoup.connect(args[0]).userAgent(Registry.USER_AGENT).get();
        GeneralUtils.addCard(new OutputCard(context, "Title of " + args[0], doc.title()));
    }
}

