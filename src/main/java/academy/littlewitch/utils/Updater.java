package academy.littlewitch.utils;

import academy.littlewitch.bot.TrBotX;
import academy.littlewitch.utils.net.HttpUtils;
import academy.littlewitch.utils.net.httpobj.ResponseInfo;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class Updater {

    private static String currentJar;
    private static String newJar;

    static {
        try {
            File t = new File(TrBotX.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI());
            currentJar = t.getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void update() {
        ResponseInfo ri = HttpUtils.sendGetHttp("https://api.github.com/repos/Shimogawa/TrBotX/releases");
        String respTxt = ri.getResponseText();
        if (StringUtils.isEmpty(respTxt)) {
            System.err.println("Error connecting to server.");
            return;
        }
        JsonArray jarr = new JsonParser().parse(ri.getResponseText()).getAsJsonArray();
        JsonObject jobj = null;
        for (JsonElement je : jarr) {
            jobj = je.getAsJsonObject();
            if (jobj.get("assets").getAsJsonArray().size() != 0) {
                break;
            }
        }
        if (jobj == null) {
            System.out.println("No updates found.");
            return;
        }
        Version v = Version.parse(jobj.get("tag_name").getAsString());
        if (v.compareTo(TrBotX.version) <= 0) {
            System.out.println("You are already up to date.");
            return;
        }

        System.out.println("Downloading " + v.versionString());
        jobj = jobj.get("assets").getAsJsonArray().get(0).getAsJsonObject();
        String downloadUrl = jobj.get("browser_download_url").getAsString();
        long size = jobj.get("size").getAsLong();
        newJar = jobj.get("name").getAsString();
        try {
            saveUrl(newJar, downloadUrl, size);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("Download complete. Please restart.");

        try {
            if (SystemUtils.IS_OS_WINDOWS) {
                pauseWindows();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        System.exit(0);
    }

    private static void pauseWindows() throws IOException {
        Runtime.getRuntime()
                .exec("cmd /c ping localhost -n 2 > nul " +
                        "&& del " + currentJar);
    }

    private static void pauseLinux() throws IOException {

    }

    private static void saveUrl(final String filename,
                                final String urlString,
                                final long size)
            throws IOException {
        try (
                BufferedInputStream in =
                        new BufferedInputStream(new URL(urlString).openStream());
                FileOutputStream fout = new FileOutputStream(filename)) {
            final byte data[] = new byte[1024];
            int count;
            long total = 0;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
                total += count;
                System.out.print("Downloaded: " +
                        String.format(
                                "%10s / %10s",
                                Util.humanReadableByteCount(total, false),
                                Util.humanReadableByteCount(size, false))
                        + "\r");
            }
            System.out.println();
        }
    }
}
