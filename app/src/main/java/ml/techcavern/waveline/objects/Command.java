package ml.techcavern.waveline.objects;

import android.content.Context;

/**
 * Created by jzhou on 12/28/2015.
 */
public abstract class Command {

    private final String[] comID;
    private final String desc;
    private final String syntax;

    protected Command(String[] comID, String syntax, String desc) {
        this.comID = comID;
        this.desc = desc;
        this.syntax = syntax;
    }

    public abstract void onCommand(Context context, String... args) throws Exception;

    public String[] getCommandID() {
        return comID;
    }

    public String getCommand() {
        return comID[0];
    }

    public String getSyntax() {
        if (syntax != null) {
            return syntax;
        } else {
            return "";
        }
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "Command(" + comID[0] + ")";
    }
}
