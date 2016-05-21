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
public class ISup extends Command {

    public ISup() {
        super(GeneralUtils.toArray("isup isupme"), "isup [domain]", "Looks up a domain on isup.me to see if it is up");
    }

    @Override
    public void onCommand(Context context, String[] args) throws Exception {
        args = ArrayUtils.remove(args, 0);
        if (!args[0].startsWith("http://") && !args[0].startsWith("https://")) {
            args[0] = "http://" + args[0];
        }
        Document doc = Jsoup.connect("http://www.isup.me/" + args[0]).userAgent(Registry.USER_AGENT).get();
        String c = doc.select("#container").text();
        if (c.contains("not just you")) {
            c = "It's not just you! " + args[0] + " looks down from here too. (Please note that isup.me - the service we use - lacks IPv6 support, so this might not be entirely accurate)";
        } else if (c.contains("just you")) {
            c = "It's just you. " + args[0] + " looks fine from here.";
        } else {
            c = "isup.me can't find " + args[0] + " on the interwho. This might be because isup.me lacks IPv6 support or simply because you put in an invalid url.";
        }
        GeneralUtils.addCard(new OutputCard(context, args[0], c));
    }
}
