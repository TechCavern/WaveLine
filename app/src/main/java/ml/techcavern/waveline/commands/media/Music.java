package ml.techcavern.waveline.commands.media;

/**
 * Created by jzhou on 12/28/2015.
 */

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import ml.techcavern.waveline.annots.CMD;
import ml.techcavern.waveline.objects.Command;
import ml.techcavern.waveline.objects.OutputCard;
import ml.techcavern.waveline.utils.DatabaseUtils;
import ml.techcavern.waveline.utils.GeneralUtils;

@CMD
public class Music extends Command {

    public Music() {
        super(GeneralUtils.toArray("music song artist ar al so album"), "music (result #) [query]", "Searches for music - result # parameter only useful for artist/album querying ");
    }

    @Override
    public void onCommand(Context context, String[] args) throws Exception {
        String lastfmapikey;
        if (DatabaseUtils.getConfig(context, "lastfmapikey") != null)
            lastfmapikey = DatabaseUtils.getConfig(context, "lastfmapikey");
        else {
            GeneralUtils.addCard(new OutputCard(context, "API key undefined", "Please define lastfmapikey in Config"));
            return;
        }
        String command = args[0];
        args = ArrayUtils.remove(args, 0);
        int ArrayIndex = 0;
        if (GeneralUtils.isInteger(args[0])) {
            ArrayIndex = Integer.parseInt(args[0]) - 1;
            args = ArrayUtils.remove(args, 0);
        }
        if (command.equalsIgnoreCase("album")) {
            JsonArray albumlist = GeneralUtils.getJsonObject("http://ws.audioscrobbler.com/2.0/?method=album.search&album=" + StringUtils.join(args, "%20") + "&api_key=" + lastfmapikey + "&format=json").get("results").getAsJsonObject().get("albummatches").getAsJsonObject().get("album").getAsJsonArray();
            if (albumlist.size() - 1 >= ArrayIndex) {
                JsonObject album = albumlist.get(ArrayIndex).getAsJsonObject();
                try {
                    JsonArray albumtracks = GeneralUtils.getJsonObject("http://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=" + lastfmapikey + "&artist=" + album.get("artist").getAsString().replaceAll(" ", "%20") + "&album=" + album.get("name").getAsString().replaceAll(" ", "%20") + "&format=json").get("album").getAsJsonObject().get("tracks").getAsJsonObject().get("track").getAsJsonArray();
                    List<String> results = new ArrayList<>();
                    for (int i = 0; i < 3; i++) {
                        try {
                            results.add("[" + album.get("name").getAsString() + "] " + albumtracks.get(i).getAsJsonObject().get("name").getAsString() + " by " + albumtracks.get(i).getAsJsonObject().get("artist").getAsJsonObject().get("name").getAsString());
                        } catch (ArrayIndexOutOfBoundsException e) {
                            return;
                        }
                    }
                    GeneralUtils.addCard(new OutputCard(context, StringUtils.join(args, " "), StringUtils.join(results, "\n")));
                } catch (IllegalStateException e) {
                    JsonObject song = GeneralUtils.getJsonObject("http://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=" + lastfmapikey + "&artist=" + album.get("artist").getAsString().replaceAll(" ", "%20") + "&album=" + album.get("name").getAsString().replaceAll(" ", "%20") + "&format=json").get("album").getAsJsonObject().get("tracks").getAsJsonObject().get("track").getAsJsonObject();
                    GeneralUtils.addCard(new OutputCard(context, StringUtils.join(args, " "), "[" + album.get("name").getAsString() + "] " + song.get("name").getAsString() + " by " + album.get("artist").getAsString()));
                }
            } else {
                ArrayIndex = ArrayIndex + 1;
                GeneralUtils.addCard(new OutputCard(context, StringUtils.join(args, " "), "result " + ArrayIndex + " does not exist"));
            }
        } else if (command.equalsIgnoreCase("artist")) {
            JsonArray artistlist = GeneralUtils.getJsonObject("http://ws.audioscrobbler.com/2.0/?method=artist.search&artist=" + StringUtils.join(args, "%20") + "&api_key=" + lastfmapikey + "&format=json").get("results").getAsJsonObject().get("artistmatches").getAsJsonObject().get("artist").getAsJsonArray();
            if (artistlist.size() - 1 >= ArrayIndex) {
                JsonObject artist = artistlist.get(ArrayIndex).getAsJsonObject();
                JsonArray toptracks = GeneralUtils.getJsonObject("http://ws.audioscrobbler.com/2.0/?method=artist.gettoptracks&artist=" + artist.get("name").getAsString().replaceAll(" ", "%20") + "&api_key=" + lastfmapikey + "&format=json").get("toptracks").getAsJsonObject().get("track").getAsJsonArray();
                List<String> results = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    try {
                        results.add(toptracks.get(i).getAsJsonObject().get("name").getAsString() + " by " + artist.get("name").getAsString());
                    } catch (ArrayIndexOutOfBoundsException e) {
                        return;
                    }
                }
                GeneralUtils.addCard(new OutputCard(context, StringUtils.join(args, " "), StringUtils.join(results, "\n")));
            } else {
                ArrayIndex = ArrayIndex + 1;
                GeneralUtils.addCard(new OutputCard(context, StringUtils.join(args, " "), "result " + ArrayIndex + " does not exist"));
            }
        } else {
            JsonArray tracklist = GeneralUtils.getJsonObject("http://ws.audioscrobbler.com/2.0/?method=track.search&track=" + StringUtils.join(args, "%20") + "&api_key=" + lastfmapikey + "&format=json").get("results").getAsJsonObject().get("trackmatches").getAsJsonObject().get("track").getAsJsonArray();
            List<String> results = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                try {
                    results.add(tracklist.get(i).getAsJsonObject().get("name").getAsString() + " by " + tracklist.get(i).getAsJsonObject().get("artist").getAsString());
                } catch (ArrayIndexOutOfBoundsException e) {
                    return;
                }
            }
            GeneralUtils.addCard(new OutputCard(context, StringUtils.join(args, " "), StringUtils.join(results, "\n")));
        }

    }
}
