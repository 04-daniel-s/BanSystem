package de.lecuutex.bansystem.utils;

import com.google.gson.Gson;
import de.lecuutex.bansystem.BanSystem;
import de.lecuutex.bansystem.utils.penalty.PenaltyReason;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * A class created by yi.dnl - 11.11.2022 / 21:18
 */

public class Utils {
    private static final Gson gson = new Gson();

    public static void sendMessageIfOnline(String uuid, String message) {
        if (BanSystem.getInstance().getProxy().getPlayer(UUID.fromString(uuid)).isConnected()) {
            BanSystem.getInstance().getProxy().getPlayer(UUID.fromString(uuid)).sendMessage(message);
        }
    }

    public static String getNameByUUID(String uuid) {
        return BanSystem.getInstance().getCloudNetDriver().getPermissionManagement().getUser(UUID.fromString(uuid)).getName();
    }

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

    public static String getDateByMilliseconds(Long millis) {
        return new SimpleDateFormat("dd.MM.yyyy").format(new Date(millis));
    }

    public static String penaltyListToString(List<PenaltyReason> list) {
        if (list.size() == 0) {
            return "Has no penalties";
        }

        StringBuilder builder = new StringBuilder();
        list.forEach(r -> builder.append(r.getReason()).append(","));
        return builder.substring(builder.toString().length() - 1);
    }


    public static void sendTeamMessage(String message) {
        BanSystem.getInstance().getProxy().getPlayers().stream().filter(p -> p.hasPermission("bansystem.team")).forEach(p -> p.sendMessage(message));
    }
}
