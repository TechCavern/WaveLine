package ml.techcavern.waveline.commands.reference;

/**
 * Created by jzhou on 12/28/2015.
 */

import android.content.Context;

import net.objecthunter.exp4j.ExpressionBuilder;

import org.apache.commons.lang3.ArrayUtils;

import ml.techcavern.waveline.annots.CMD;
import ml.techcavern.waveline.objects.Command;
import ml.techcavern.waveline.objects.OutputCard;
import ml.techcavern.waveline.utils.GeneralUtils;

@CMD
public class Calculate extends Command {

    public Calculate() {
        super(GeneralUtils.toArray("calculate calc c math"), "calculate [expression]", "calculates a math expression");
    }

    @Override
    public void onCommand(Context context, String[] args) throws Exception {
        args = ArrayUtils.remove(args, 0);
        ExpressionBuilder expr = new ExpressionBuilder(args[0]);
        String answer = Double.toString(expr.build().evaluate());
        GeneralUtils.addCard(new OutputCard(context, args[0], answer));
    }

}
