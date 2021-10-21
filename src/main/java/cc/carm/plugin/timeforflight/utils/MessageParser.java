package cc.carm.plugin.timeforflight.utils;

public class MessageParser {

    public static String parseColor(String text) {
        return text.replaceAll("&", "§").replace("§§", "&");
    }


}
