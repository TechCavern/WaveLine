package ml.techcavern.waveline.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class GeneralUtils {
    public static String buildMessage(int startint, int finishint, String[] args) {
        String message = "";
        for (int i = startint; i < finishint; i++) {
            if (i == finishint - 1)
                message += args[i];
            else
                message += args[i] + " ";
        }
        return message;
    }


    public static JsonObject getJsonObject(String url) throws Exception {
        String result = parseUrl(url);
        return new JsonParser().parse(result).getAsJsonObject();
    }

    public static JsonElement getJsonElement(String url) throws Exception {
        String result = parseUrl(url);
        return new JsonParser().parse(result);
    }

    public static String getJsonString(JsonArray array, String name) {
        String returning = "";
        for (int i = 0; i < array.size(); i++) {
            if (i == 0) {
                returning = array.get(i).getAsJsonObject().get(name).getAsString();
            } else {
                returning += ", " + array.get(i).getAsJsonObject().get(name).getAsString();
            }
        }
        return returning;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static String parseUrl(String Url) throws Exception {
        URL url1 = new URL(Url);
        URLConnection conn = url1.openConnection();
        conn.addRequestProperty("user-agent", Registry.USER_AGENT);
        String line;
        String result = "";
        BufferedReader buffereader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while ((line = buffereader.readLine()) != null) {
            result += line.replaceAll("\n", " ") + "\n";
        }
        buffereader.close();
        return result;

    }

    public static JsonArray getJsonArray(String url) throws Exception {
        String result = parseUrl(url);
        return new JsonParser().parse(result).getAsJsonArray();
    }



    public static long getMilliSeconds(String input) {
        if (input.toLowerCase().endsWith("h")) {
            return Long.parseLong(input.replace("h", "")) * 60 * 60 * 1000;
        } else if (input.toLowerCase().endsWith("m")) {
            return Long.parseLong(input.replace("m", "")) * 60 * 1000;

        } else if (input.toLowerCase().endsWith("d")) {
            return Long.parseLong(input.replace("d", "")) * 24 * 60 * 60 * 1000;

        } else if (input.toLowerCase().endsWith("w")) {
            return Long.parseLong(input.replace("w", "")) * 7 * 24 * 60 * 60 * 1000;

        } else if (input.toLowerCase().endsWith("s")) {
            return Long.parseLong(input.replace("s", "")) * 1000;
        } else {
            return Long.parseLong(input);
        }
    }


    public static String[] toArray(String input) {
        return StringUtils.split(input, " ");
    }


    public static int readInputStream(InputStream in)
            throws Exception {
        int i = 0;
        int j = 0;
        while (true) {
            int k = in.read();
            i |= (k & 0x7F) << j++ * 7;
            if (j > 5) {
                throw new RuntimeException("VarInt too big");
            }
            if ((k & 0x80) != 128) {
                break;
            }
        }
        return i;
    }

    public static void writeOutputStream(OutputStream out, int i)
            throws Exception {
        while (true) {
            if ((i & 0xFFFFFF80) == 0) {
                out.write(i);
                return;
            }

            out.write(i & 0x7F | 0x80);
            i >>>= 7;
        }
    }

    public static String getTimeFromSeconds(int seconds) {
        if (seconds > 60) {
            int minutes = seconds / 60;
            seconds = seconds % 60;
            if (minutes > 60) {
                int hours = minutes / 60;
                minutes = minutes % 60;
                if (hours > 24) {
                    int days = hours / 24;
                    hours = hours % 24;
                    return days + " days, " + hours + " hours, " + minutes + " minutes, " + seconds + " seconds";
                } else {
                    return hours + " hours, " + minutes + " minutes, " + seconds + " seconds";
                }
            } else {
                return minutes + " minutes, " + seconds + " seconds";
            }
        } else {
            return seconds + " seconds";
        }
    }

    public static String getDateFromSeconds(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(time * 1000);
        Locale locale = Locale.ENGLISH;
        return cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale) + ", " + cal.getDisplayName(Calendar.MONTH, Calendar.LONG, locale) + " " + (cal.get(Calendar.DAY_OF_MONTH)) + ", " + cal.get(Calendar.YEAR) + " at " + cal.get(Calendar.HOUR_OF_DAY) + ":" + formatTime(cal.get(Calendar.MINUTE)) + ":" + formatTime(cal.get(Calendar.SECOND)) + " (UTC)";
    }

    public static String formatTime(int time) {
        if (time < 10) {
            return 0 + String.valueOf(time);
        } else {
            return String.valueOf(time);
        }
    }

    public static String getMediaWikiTitle(String url, String query, int querynumber) {
        try {
            JsonObject results = getJsonObject(url + "api.php?action=query&list=search&format=json&srprop=snippet&srinterwiki=true&srsearch=" + query.replaceAll(" ", "%20")).getAsJsonObject().get("query").getAsJsonObject();
            if (results.get("searchinfo").getAsJsonObject().get("totalhits").getAsInt() >= querynumber) {
                return results.get("search").getAsJsonArray().get(querynumber - 1).getAsJsonObject().get("title").getAsString();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static String getMediaWikiContentFromTitle(String url, String title) {
        try {
            JsonObject results = getJsonObject(url + "api.php?action=parse&format=json&page=" + title.replaceAll(" ", "%20")).getAsJsonObject().get("parse").getAsJsonObject().get("text").getAsJsonObject();
            Document doc = Jsoup.parse(results.get("*").getAsString());
            doc.select(".notaninfobox").remove();
            doc.select(".hatnote").remove();
            doc.select(".atemplate").remove();
            doc.select(".infobox").remove();
            int i = 0;
            while (stripHTML(doc.select("p").get(i).toString()).isEmpty()) {
                i++;
            }
            return stripHTML(doc.select("p").get(i).toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String stripHTML(String htmltext) {
        return htmltext.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ").replaceAll("\\[.*\\]", "").trim().replaceAll(" +", " ").replaceAll(" ,", "").replaceAll(" \\.", "").replaceAll("\n", " ").replaceAll("\r", " ");
    }

    public static String getIP(String input) {
        input = input.replaceAll("http://|https://", "");
        try {
            InetAddress[] addarray = InetAddress.getAllByName(input);
            String add = "";
            for (InetAddress add6 : addarray) {
                if (add6 instanceof Inet6Address)
                    add = add6.getHostAddress();
            }
            if (add != null && !add.isEmpty())
                return add;
            for (InetAddress add4 : addarray) {
                if (add4 instanceof Inet4Address)
                    add = add4.getHostAddress();
            }
            return add;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}




