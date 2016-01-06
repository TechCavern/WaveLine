package ml.techcavern.waveline.commands.reference;

/**
 * Created by jzhou on 12/28/2015.
 */

import android.content.Context;

import com.wolfram.alpha.WAEngine;
import com.wolfram.alpha.WAPlainText;
import com.wolfram.alpha.WAPod;
import com.wolfram.alpha.WAQuery;
import com.wolfram.alpha.WAQueryResult;
import com.wolfram.alpha.WASubpod;
import com.wolfram.alpha.visitor.Visitable;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import ml.techcavern.waveline.annots.CMD;
import ml.techcavern.waveline.objects.Command;
import ml.techcavern.waveline.objects.OutputCard;
import ml.techcavern.waveline.utils.GeneralUtils;

@CMD
public class Question extends Command {

    public Question() {
        super(GeneralUtils.toArray("question q wa wolframalpha"), "question (answer #) [question]", "Ask wolfram alpha a question!");
    }

    @Override
    public void onCommand(Context context, String[] args) throws Exception {
        args = ArrayUtils.remove(args, 0);
        int ArrayIndex = 1;
        if (GeneralUtils.isInteger(args[0])) {
            ArrayIndex = Integer.parseInt(args[0]);
            args = ArrayUtils.remove(args, 0);
        }
        WAEngine engine = new WAEngine();
        engine.setAppID("5P3X6V-LR44XJW9V2");
        engine.addFormat("plaintext");
        WAQuery query = engine.createQuery();
        query.setInput(StringUtils.join(args, " "));
        WAQueryResult queryResult = engine.performQuery(query);
        WAPod[] result = queryResult.getPods();
        if (result.length > 0) {
            if (result.length - 1 >= ArrayIndex) {
                for (WASubpod sub : result[ArrayIndex].getSubpods()) {
                    for (Visitable visitable : sub.getContents()) {
                        if (sub.getTitle().isEmpty())
                            GeneralUtils.addCard(new OutputCard(context, result[ArrayIndex].getTitle(), ((WAPlainText) sub.getContents()[0]).getText().replaceAll("\\n", " - ").replaceAll(" \\| ", ": ")));
                        else
                            GeneralUtils.addCard(new OutputCard(context, result[ArrayIndex].getTitle() + " - " + sub.getTitle(), ((WAPlainText) sub.getContents()[0]).getText().replaceAll("\\n", " - ").replaceAll(" \\| ", ": ")));
                    }
                }
            } else {
                GeneralUtils.addCard(new OutputCard(context, "Answer does not exist", "Answer #" + ArrayIndex + " does not exist"));
            }
        } else {
            GeneralUtils.addCard(new OutputCard(context, "No results", "Question returned no answers"));
        }
    }

}
