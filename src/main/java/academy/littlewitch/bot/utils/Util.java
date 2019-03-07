package academy.littlewitch.bot.utils;

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
}
