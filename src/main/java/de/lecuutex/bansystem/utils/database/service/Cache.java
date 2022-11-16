package de.lecuutex.bansystem.utils.database.service;

import com.sun.org.apache.xalan.internal.xsltc.dom.SortingIterator;
import de.lecuutex.bansystem.BanSystem;
import de.lecuutex.bansystem.utils.MinecraftPlayer;

import java.util.HashMap;

/**
 * A class created by yi.dnl - 13.11.2022 / 20:05
 */

public class Cache {

    private final HashMap<String, MinecraftPlayer> players = new HashMap<>();

    public void cacheMinecraftPlayer(MinecraftPlayer minecraftPlayer) {
        players.put(minecraftPlayer.getId(), minecraftPlayer);
    }

    protected void saveBan(String uuid) {
        getPlayer(uuid).setBanned(true);
    }

    protected MinecraftPlayer getPlayer(String uuid) {
        return players.get(uuid);
    }

    protected boolean isInCache(String uuid) {
        return players.containsKey(uuid);
    }

    protected void removeBan(String uuid) {
        getPlayer(uuid).setBanned(false);
    }

    protected void saveMute(String uuid) {
        getPlayer(uuid).setMuted(true);
    }

    protected void removeMute(String uuid) {
        getPlayer(uuid).setMuted(false);
    }
}
