package ml.techcavern.waveline.commands.fun;

/**
 * Created by jzhou on 12/28/2015.
 */

import android.content.Context;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import ml.techcavern.waveline.annots.CMD;
import ml.techcavern.waveline.objects.Command;
import ml.techcavern.waveline.objects.OutputCard;
import ml.techcavern.waveline.utils.GeneralUtils;
import ml.techcavern.waveline.utils.Registry;

@CMD
public class FMyLife extends Command {

    public FMyLife() {
        super(GeneralUtils.toArray("fmylife fml"), "fmylife", "Sends random fml");

    }

    @Override
    public void onCommand(Context context, String[] args) throws Exception {
        Document doc = Jsoup.connect("http://m.fmylife.com/random").userAgent(Registry.USER_AGENT).get();
        Elements FML = doc.select(".text");
        Elements cf = doc.select(".cf");
        String title = cf.get(1).select(".date").toString().replace("<br>", " - ");
        String fmylife = FML.get(0).toString();
        GeneralUtils.addCard(new OutputCard(context, GeneralUtils.stripHTML(title), GeneralUtils.stripHTML(fmylife)));

    }
}
