package de.lecuutex.bansystem.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * A class created by yi.dnl - 12.11.2022 / 16:08
 */

@Getter
@AllArgsConstructor
public class MinecraftPlayer {

    private String name;

    private String id;

    private String ipAddress;

    private Long firstJoin;

    @Setter
    private boolean muted = false;

    @Setter
    private boolean banned = false;

    public MinecraftPlayer(ProxiedPlayer player) {
        this.name = player.getName();
        this.id = player.getUniqueId().toString();
        this.ipAddress = player.getPendingConnection().getAddress().getHostString();
        this.firstJoin = System.currentTimeMillis();
    }
}
