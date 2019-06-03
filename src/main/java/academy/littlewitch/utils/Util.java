package academy.littlewitch.utils;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

public class Util {
    public static Random random = new Random();

    public static final String BAD_STRING = "\u8371\u8372\u8373\u0001bx*#82CC";

    public static String toCQValidString(String s) {
        return s.replace("&", "&amp;")
                .replace("[", "&#91;")
                .replace("]", "&#93;")
                .replace(",", "&#44;");
    }

    public static String unescapeCQString(String s) {
        return s.replace("&amp;", "&")
                .replace("&#91;", "[")
                .replace("&#93;", "]");
    }

    public static ArrayList<String> splitAllWhitespace(ArrayList<String> args) {
        ArrayList<String> copy = new ArrayList<>();
        for (String s : args) {
            String[] ss = s.split("\\s");
            for (String p : ss) {
                if (StringUtils.isNotBlank(p)) {
                    copy.add(p);
                }
            }
        }
        return copy;
    }

    public static ArrayList<String> splitByWhitespace(String arg) {
        ArrayList<String> copy = new ArrayList<>();
        for (String s : arg.split("\\s")) {
            if (StringUtils.isNotBlank(s)) {
                copy.add(s);
            }
        }
        return copy;
    }

    /**
     * https://stackoverflow.com/questions/3758606/how-to-convert-byte-size-into-human-readable-format-in-java
     * @param bytes
     * @param si
     * @return
     */
    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public static String toDateString(long milli) {
        return toDateString("%02d:%02d:%02d.%d", milli);
    }

    public static String toDateString(String format, long milli) {
        long millis = milli % 1000;
        long second = (milli / 1000) % 60;
        long minute = (milli / (1000 * 60)) % 60;
        long hour = (milli / (1000 * 60 * 60)) % 24;

        return String.format(format, hour, minute, second, millis);
    }

    /**
     * Default UTF-8 charset.
     * @param is
     * @return
     * @throws IOException
     */
    public static String readAll(InputStream is) throws IOException {
        return readAll(is, StandardCharsets.UTF_8);
    }

    /**
     * https://stackoverflow.com/questions/309424/how-do-i-read-convert-an-inputstream-into-a-string-in-java
     * @param is
     * @param cs
     * @return
     * @throws IOException
     */
    public static String readAll(InputStream is, Charset cs) throws IOException {
        try (ByteArrayOutputStream result = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString(cs.name());
        }
    }
}
