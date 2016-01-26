package ml.techcavern.waveline.utils;

import ml.techcavern.waveline.commands.dnsinfo.DNSInfo;
import ml.techcavern.waveline.commands.dnsinfo.GeoIP;
import ml.techcavern.waveline.commands.dnsinfo.ISup;
import ml.techcavern.waveline.commands.dnsinfo.Title;
import ml.techcavern.waveline.commands.fun.FMyLife;
import ml.techcavern.waveline.commands.fun.UrbanDictionary;
import ml.techcavern.waveline.commands.media.Reddit;
import ml.techcavern.waveline.commands.minecraft.MCModStats;
import ml.techcavern.waveline.commands.minecraft.MCMods;
import ml.techcavern.waveline.commands.minecraft.MCStatus;
import ml.techcavern.waveline.commands.misc.Config;
import ml.techcavern.waveline.commands.misc.Test;
import ml.techcavern.waveline.commands.reference.Calculate;
import ml.techcavern.waveline.commands.reference.Define;
import ml.techcavern.waveline.commands.reference.Question;
import ml.techcavern.waveline.commands.reference.Search;
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
        registerCommand(new Question());
        registerCommand(new Reddit());
        registerCommand(new Define());
        registerCommand(new FMyLife());
        registerCommand(new Search());
        registerCommand(new UrbanDictionary());
        registerCommand(new MCStatus());
        registerCommand(new MCMods());
        registerCommand(new Test());
        registerCommand(new Config());
        registerCommand(new MCModStats());
        registerCommand(new Title());
        registerCommand(new DNSInfo());
        registerCommand(new ISup());
    }

    public static void registerCommand(Command cmd) {
        Registry.commandList.add(cmd);
        String[] comids = cmd.getCommandID();
        for (String comid : comids) {
            Registry.commands.put(comid, cmd);
        }
    }
}
