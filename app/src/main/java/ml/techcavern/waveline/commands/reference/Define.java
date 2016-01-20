package ml.techcavern.waveline.commands.reference;

/**
 * Created by jzhou on 12/28/2015.
 */

import android.content.Context;

import com.wordnik.client.api.WordApi;
import com.wordnik.client.model.Definition;
import com.wordnik.client.model.ExampleUsage;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.util.List;

import ml.techcavern.waveline.annots.CMD;
import ml.techcavern.waveline.objects.Command;
import ml.techcavern.waveline.objects.OutputCard;
import ml.techcavern.waveline.utils.DatabaseUtils;
import ml.techcavern.waveline.utils.GeneralUtils;

@CMD
public class Define extends Command {

    public Define() {
        super(GeneralUtils.toArray("define d def"), "define (def #) [word]", "Defines a word");

    }

    @Override
    public void onCommand(Context context, String[] args) throws Exception {
        args = ArrayUtils.remove(args, 0);
        int ArrayIndex = 0;
        if (GeneralUtils.isInteger(args[0])) {
            ArrayIndex = Integer.parseInt(args[0]) - 1;
            args = ArrayUtils.remove(args, 0);
        }
        String wordnikapikey;
        if (DatabaseUtils.getConfig(context, "wordnikapikey") != null)
            wordnikapikey = DatabaseUtils.getConfig(context, "wordnikapikey");
        else {
            GeneralUtils.addCard(new OutputCard(context, "API key undefined", "Please define wordnikapikey in Config"));
            return;
        }
        WordApi api = new WordApi();
        api.getInvoker().addDefaultHeader("api_key", wordnikapikey);
        List<Definition> Defs = api.getDefinitions(args[0].toLowerCase(), null, null, null, null, null, null);
        if (Defs.size() > 0) {
            if (Defs.size() - 1 >= ArrayIndex) {
                String word = WordUtils.capitalizeFully(Defs.get(ArrayIndex).getWord());
                String definition = Defs.get(ArrayIndex).getText();
                List<ExampleUsage> examples = Defs.get(ArrayIndex).getExampleUses();
                if (examples.size() > 0) {
                    definition += "\n\n Example: " + examples.get(0).getText();
                }
                GeneralUtils.addCard(new OutputCard(context, "Definition of " + word, definition));
            } else {
                ArrayIndex = ArrayIndex + 1;
                GeneralUtils.addCard(new OutputCard(context, "Error", "Def #" + ArrayIndex + " does not exist"));
            }
        } else {
            GeneralUtils.addCard(new OutputCard(context, "Not Defined", "Undefined"));
        }
    }

}
