package ml.techcavern.waveline.utils;

import ml.techcavern.waveline.commands.dnsinfo.GeoIP;
import ml.techcavern.waveline.commands.reference.Calculate;
import ml.techcavern.waveline.commands.reference.Weather;
import ml.techcavern.waveline.commands.reference.Wiki;
import ml.techcavern.waveline.objects.Command;

/**
 * Created by jzhou on 12/26/2015.
 */
public class LoadUtils {
    public static void registerCommands() {
        /**
        Set<Class<?>> classes = Registry.wavelineReflection.getTypesAnnotatedWith(CMD.class);
        for (Class<?> clss : classes) {
            try {
                Command command = (Command) clss.newInstance();
         registerCommand(cmd)
            } catch (Exception e) {
         e.printStackTrace()
            }
        }
         **/
        registerCommand(new Weather());
        registerCommand(new GeoIP());
        registerCommand(new Wiki());
        registerCommand(new Calculate());
    }

    public static void registerCommand(Command cmd) {
        Registry.commandList.add(cmd);
        String[] comids = cmd.getCommandID();
        for (String comid : comids) {
            Registry.commands.put(comid, cmd);
        }
    }
}
