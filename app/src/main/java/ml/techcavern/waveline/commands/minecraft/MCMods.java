package ml.techcavern.waveline.commands.minecraft;

/**
 * Created by jzhou on 12/28/2015.
 */

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

import ml.techcavern.waveline.annots.CMD;
import ml.techcavern.waveline.objects.Command;
import ml.techcavern.waveline.objects.OutputCard;
import ml.techcavern.waveline.utils.GeneralUtils;

@CMD
public class MCMods extends Command {
    private static Map<JsonObject, String> mcmods = new HashMap<>();

    public MCMods() {
        super(GeneralUtils.toArray("mcmods mcmod mclmod mclm mclma mclmodauthor mcmodauthor mcma mcm"), "mcmods (version) (+)[name]", "Gets info on minecraft mods");
    }

    public static boolean searchMCVersion(String version, String searchPhrase, String modname) throws Exception {
        JsonArray mods = GeneralUtils.getJsonArray("http://bot.notenoughmods.com/" + version + ".json");
        JsonArray mods2 = GeneralUtils.getJsonArray("http://modlist.mcf.li/api/v3/" + version + ".json");
        MCModSearch:
        for (int j = 0; j < mods.size(); j++) {
            JsonObject mod = mods.get(j).getAsJsonObject();
            if (mcmods.size() >= 3) {
                return true;
            }
            for (JsonObject o : mcmods.keySet()) {
                if (mod.get("name").getAsString().equalsIgnoreCase((o).get("name").getAsString()))
                    continue MCModSearch;
            }
            if (mod.get(searchPhrase).getAsString().toLowerCase().replaceAll("[^a-zA-Z0-9]", "").contains(modname)) {
                mcmods.put(mod, version);
            }
        }
        if (mcmods.size() > 0) {
            return true;
        }
        if (mods2.size() < 1) {
            return false;
        }
        McModSearch:
        for (int j = 0; j < mods.size(); j++) {
            JsonObject mod = mods2.get(j).getAsJsonObject();
            if (mcmods.size() >= 3) {
                return true;
            }
            for (JsonObject o : mcmods.keySet()) {
                if (mod.get("name").getAsString().equalsIgnoreCase((o).get("name").getAsString()))
                    continue McModSearch;
            }
            if (mod.get(searchPhrase).isJsonArray()) {
                JsonArray e = mod.get(searchPhrase).getAsJsonArray();
                for (int g = 0; g < e.size(); g++) {
                    if (e.get(g).getAsString().toLowerCase().replaceAll("[^a-zA-Z0-9]", "").contains(modname)) {
                        mcmods.put(mod, version);
                        continue McModSearch;
                    }
                }
            } else if (mod.get(searchPhrase).getAsString().toLowerCase().replaceAll("[^a-zA-Z0-9]", "").contains(modname)) {
                mcmods.put(mod, version);
            }
        }
        return mcmods.size() > 0;
    }


    @Override
    public void onCommand(Context context, String[] args) throws Exception {
        mcmods.clear();
        String command = args[0];
        args = ArrayUtils.remove(args, 0);
        JsonArray versions = GeneralUtils.getJsonArray("http://bot.notenoughmods.com/?json&count");
        boolean isDev = false;
        String version = null;
        if (command.startsWith("mcl")) {
            command = command.replaceFirst("l", "");
            version = args[0];
            args = ArrayUtils.remove(args, 0);
        }
        String modname = StringUtils.join(args).toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
        if (modname.startsWith("+")) {
            isDev = true;
            modname = modname.replaceFirst("\\+", "");
        }
        String searchPhrase = "name";
        if (command.equalsIgnoreCase("mcmodauthor") || command.equalsIgnoreCase("mcma"))
            searchPhrase = "author";
        if (version == null) {
            MCModVersionSearch:
            for (int i = versions.size() - 1; i >= 0; i--) {
                int size = versions.get(i).getAsJsonObject().get("count").getAsInt();
                if (size > 10) {
                    version = versions.get(i).getAsJsonObject().get("name").getAsString();
                    boolean toReturn = searchMCVersion(version, searchPhrase, modname);
                    if (toReturn)
                        break MCModVersionSearch;
                }

            }
        } else {
            searchMCVersion(version, searchPhrase, modname);
        }
        if (mcmods.isEmpty()) {
            GeneralUtils.addCard(new OutputCard(context, "Error", "No mods found"));
        } else {
            String content = "";
            for (JsonObject mod : mcmods.keySet()) {
                boolean isNEM = true;
                if (mod.get("version") == null) {
                    isNEM = false;
                }
                String ModVersion = "";
                if (isNEM) {
                    if (isDev)
                        ModVersion = " " + mod.get("dev").getAsString();
                    else
                        ModVersion = " " + mod.get("version").getAsString();
                }
                String Name = mod.get("name").getAsString();
                String Link;
                if (isNEM) {
                    Link = mod.get("shorturl").getAsString();
                    if (Link != null && Link.isEmpty())
                        Link = mod.get("longurl").getAsString();
                } else {
                    Link = mod.get("link").getAsString();
                }

                String Author;
                if (isNEM)
                    Author = mod.get("author").getAsString();
                else
                    Author = StringUtils.join(mod.get("author").getAsJsonArray(), ", ").replaceAll("\"", "");
                if (Author.isEmpty())
                    content += "[" + mcmods.get(mod) + "] " + Name + ModVersion + " - " + Link + "\n";
                else
                    content += "[" + mcmods.get(mod) + "] " + Name + ModVersion + " by " + Author + " - " + Link + "\n";
            }
            GeneralUtils.addCard(new OutputCard(context, "Found " + mcmods.size() + " mods", content));

        }
    }
}
