package academy.littlewitch.bot.utils;

import java.util.Random;

public class Util {
    public static Random random = new Random();

    public static String toCQValidString(String s) {
        return s.replace("&", "&amp;")
                .replace("[", "&#91;")
                .replace("]", "&#93;")
                .replace(",", "&#44;");
    }
}
