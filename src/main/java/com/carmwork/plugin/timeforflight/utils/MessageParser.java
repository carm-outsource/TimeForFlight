package com.carmwork.plugin.timeforflight.utils;

import net.md_5.bungee.api.chat.BaseComponent;

public class MessageParser {

    public static String parseColor(String text) {
        return text.replaceAll("&", "§").replace("§§", "&");
    }


}
