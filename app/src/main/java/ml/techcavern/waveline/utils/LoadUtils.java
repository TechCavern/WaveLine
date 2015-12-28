package ml.techcavern.waveline.utils;

import java.util.Set;

import ml.techcavern.waveline.annots.CMD;
import ml.techcavern.waveline.objects.Command;

/**
 * Created by jzhou on 12/26/2015.
 */
public class LoadUtils {
    public static void registerCommands() {
        Set<Class<?>> classes = Registry.wavelineReflection.getTypesAnnotatedWith(CMD.class);
        for (Class<?> clss : classes) {
            try {
                Command command = (Command) clss.newInstance();
                Registry.commandList.add(command);
                String[] comids = command.getCommandID();
                for (String comid : comids) {
                    Registry.commands.put(comid, command);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
