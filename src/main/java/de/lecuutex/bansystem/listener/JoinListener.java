package de.lecuutex.bansystem.listener;

import de.lecuutex.bansystem.BanSystem;
import de.lecuutex.bansystem.utils.database.service.Cache;
import de.lecuutex.bansystem.utils.database.service.PenaltyService;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * A class created by yi.dnl - 12.11.2022 / 22:23
 */

public class JoinListener implements Listener {

    private final PenaltyService penaltyService = BanSystem.getInstance().getPenaltyService();;

    @EventHandler
    public void onEvent(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();
        new Cache().cacheNewPlayer(player);

        if (penaltyService.isBanned(player.getUniqueId().toString())) {
            player.disconnect("Du bist gebannt");
        }
    }
}