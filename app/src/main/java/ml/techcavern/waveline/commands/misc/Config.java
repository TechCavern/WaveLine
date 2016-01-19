package ml.techcavern.waveline.commands.misc;

/**
 * Created by jzhou on 12/28/2015.
 */

import android.content.Context;

import org.apache.commons.lang3.ArrayUtils;

import ml.techcavern.waveline.annots.CMD;
import ml.techcavern.waveline.objects.Command;
import ml.techcavern.waveline.objects.OutputCard;
import ml.techcavern.waveline.utils.DatabaseUtils;
import ml.techcavern.waveline.utils.GeneralUtils;

@CMD
public class Config extends Command {

    public Config() {
        super(GeneralUtils.toArray("config"), "config (+)(-)[property] (value)", "test");
    }


    @Override
    public void onCommand(Context context, String[] args) throws Exception {
        args = ArrayUtils.remove(args, 0);
        String property;
        boolean isModify = false;
        boolean isDelete = false;
        boolean viewonly = false;
        if (args.length < 2) {
            viewonly = true;
        }
        if (args[0].startsWith("-")) {
            property = args[0].replaceFirst("-", "");
            isDelete = true;
        } else if (args[0].startsWith("+")) {
            property = args[0].replaceFirst("\\+", "");
            isModify = true;
        } else {
            property = args[0];
        }
        if (DatabaseUtils.getConfig(context, property) != null && (isDelete || isModify)) {
            if (isDelete) {
                DatabaseUtils.deleteConfig(context, property);
                GeneralUtils.addCard(new OutputCard(context, "Deleted", "Property Deleted"));
            } else if (isModify) {
                if (viewonly)
                    GeneralUtils.addCard(new OutputCard(context, property, DatabaseUtils.getConfig(context, property)));
                else {
                    DatabaseUtils.updateConfig(context, property, args[1]);
                    GeneralUtils.addCard(new OutputCard(context, "Modified", "Property Modified"));
                }
            }
        } else if (DatabaseUtils.getConfig(context, property) == null && !isDelete && !isModify) {
            DatabaseUtils.addConfig(context, property, args[1]);
            GeneralUtils.addCard(new OutputCard(context, "Added", "Property Added"));
        } else {
            GeneralUtils.addCard(new OutputCard(context, "Error", "property already exists (If you were adding) or property does not exist"));
        }

    }
}
