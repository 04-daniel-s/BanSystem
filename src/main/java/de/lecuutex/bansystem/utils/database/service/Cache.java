package de.lecuutex.bansystem.utils.database.service;

import de.lecuutex.bansystem.BanSystem;
import de.lecuutex.bansystem.utils.MinecraftPlayer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;

/**
 * A class created by yi.dnl - 13.11.2022 / 20:05
 */

public class Cache {

    private final HashMap<String, MinecraftPlayer> players = new HashMap<>();

    private final PlayerService playerService = BanSystem.getInstance().getPlayerService();

    public void cacheNewPlayer(ProxiedPlayer player) {
        if (!isInCache(player.getUniqueId().toString())) {
            players.put(player.getUniqueId().toString(), new MinecraftPlayer(player));
        }
    }

    public void cacheMinecraftPlayer(MinecraftPlayer minecraftPlayer) {
        players.put(minecraftPlayer.getId(), minecraftPlayer);
    }

    public void saveBan(String uuid) {
        playerService.getMinecraftPlayer(uuid).setBanned(true);
    }

    public MinecraftPlayer getPlayer(String uuid) {
        return players.get(uuid);
    }

    public boolean isInCache(String uuid) {
        return players.containsKey(uuid);
    }

    public void removeBan(String uuid) {
        getPlayer(uuid).setBanned(false);
    }

    public void saveMute(String uuid) {
        getPlayer(uuid).setMuted(true);
    }

    public void removeMute(String uuid) {
        getPlayer(uuid).setMuted(false);
    }
}
