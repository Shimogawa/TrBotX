package academy.littlewitch.bot.utils;

import org.apache.commons.lang3.StringUtils;

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
}
