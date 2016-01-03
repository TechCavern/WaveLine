package ml.techcavern.waveline.commands.reference;

/**
 * Created by jzhou on 12/28/2015.
 */

import android.content.Context;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import ml.techcavern.waveline.annots.CMD;
import ml.techcavern.waveline.objects.Command;
import ml.techcavern.waveline.objects.OutputCard;
import ml.techcavern.waveline.utils.GeneralUtils;

@CMD
public class Wiki extends Command {

    public Wiki() {
        super(GeneralUtils.toArray("wiki wi mcwiki mcw mcmodwiki mcmw"), "wiki (result #) [query wiki]", "Searches wikis for something");

    }

    @Override
    public void onCommand(Context context, String[] args) throws Exception {
        int ArrayIndex = 1;
        if (GeneralUtils.isInteger(args[0])) {
            ArrayIndex = Integer.parseInt(args[0]);
            args = ArrayUtils.remove(args, 0);
        }
        String url = "https://en.wikipedia.org/w/";
        String userurl = "https://en.wikipedia.org/wiki/";
        String title = GeneralUtils.getMediaWikiTitle(url, StringUtils.join(args, "%20"), ArrayIndex);
        String content = GeneralUtils.getMediaWikiContentFromTitle(url, title);
        String urljoin = "%20";
        if (args[0].equalsIgnoreCase("mcmodwiki") || args[0].equalsIgnoreCase("mcmw")) {
            url = "http://ftb.gamepedia.com/";
            userurl = url;
            title = GeneralUtils.getMediaWikiTitle(url, StringUtils.join(args, "%20"), ArrayIndex);
            content = GeneralUtils.getMediaWikiContentFromTitle(url, title);
            urljoin = "_";
            if (title == null || content == null) {
                url = "http://ftbwiki.org/";
                title = GeneralUtils.getMediaWikiTitle(url, StringUtils.join(args, "%20"), ArrayIndex);
                content = GeneralUtils.getMediaWikiContentFromTitle(url, title);
            }
        } else if (args[0].equalsIgnoreCase("mcwiki") || args[0].equalsIgnoreCase("mcw")) {
            url = "http://minecraft.gamepedia.com/";
            userurl = url;
            title = GeneralUtils.getMediaWikiTitle(url, StringUtils.join(args, "%20"), ArrayIndex);
            content = GeneralUtils.getMediaWikiContentFromTitle(url, title);
            urljoin = "_";
        }
        if (title != null && content != null) {
            GeneralUtils.addCard(new OutputCard(context, title, content + " Read full article here: " + userurl + title.replace(" ", urljoin)));
        } else if (ArrayIndex > 1) {
            GeneralUtils.addCard(new OutputCard(context, "Error", "Result #" + ArrayIndex + " does not exist"));
        } else {
            GeneralUtils.addCard(new OutputCard(context, "Error", "No Results Found"));
        }
    }

}
