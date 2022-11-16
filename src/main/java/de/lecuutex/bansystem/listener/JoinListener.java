package de.lecuutex.bansystem.listener;

import de.lecuutex.bansystem.BanSystem;
import de.lecuutex.bansystem.utils.MinecraftPlayer;
import de.lecuutex.bansystem.utils.database.service.PlayerService;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * A class created by yi.dnl - 12.11.2022 / 22:23
 */

public class JoinListener implements Listener {

    @EventHandler
    public void onEvent(PostLoginEvent event) {
        BanSystem.getInstance().getExecutorService().submit(() -> {
            PlayerService playerService = BanSystem.getInstance().getPlayerService();
            ProxiedPlayer player = event.getPlayer();
            String uuid = player.getUniqueId().toString();

            if (!playerService.isPresent(uuid)) {
                BanSystem.getInstance().getCache().cacheMinecraftPlayer(new MinecraftPlayer(player));
                playerService.postPlayer(uuid);
            }

            if (playerService.getMinecraftPlayer(player.getUniqueId().toString()).isBanned()) {
                player.disconnect("Du bist gebannt");
            }
        });
    }
}
