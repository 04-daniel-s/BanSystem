package de.lecuutex.bansystem.listener;

import de.lecuutex.bansystem.BanSystem;
import de.lecuutex.bansystem.utils.MinecraftPlayer;
import de.lecuutex.bansystem.utils.database.service.PlayerService;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * A class created by yi.dnl - 12.11.2022 / 22:23
 */

public class JoinListener implements Listener {

    @EventHandler
    public void onEvent(PreLoginEvent event) {
        PlayerService playerService = BanSystem.getInstance().getPlayerService();
        PendingConnection connection = event.getConnection();
        String uuid = connection.getUniqueId().toString();

        if (!playerService.isPresent(uuid)) {
            BanSystem.getInstance().getCache().cacheMinecraftPlayer(new MinecraftPlayer(connection));
            playerService.postPlayer(uuid);
        }

        if (playerService.getMinecraftPlayer(uuid).isBanned()) {
            connection.disconnect("Du bist gebannt");
        }
    }
}
