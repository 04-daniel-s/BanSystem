package de.lecuutex.bansystem.utils;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.UUID;

/**
 * A class created by yi.dnl - 11.11.2022 / 21:18
 */

public class Utils {
    private static final Gson gson = new Gson();

    public static UUID getUUIDByName(String name) {
        String url = "https://api.mojang.com/users/profiles/minecraft/" + name;
        String uuidJson = getStringFromURL(url);
        if (uuidJson.isEmpty()) return null;
        uuidJson = uuidJson.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");
        return UUID.fromString(gson.fromJson(uuidJson, MinecraftPlayer.class).getId());
    }

    private static String getStringFromURL(String url) {
        StringBuilder text = new StringBuilder();
        try {
            Scanner scanner = new Scanner(new URL(url).openStream());
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                while (line.startsWith(" ")) {
                    line = line.substring(1);
                }
                text.append(line);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }
}
