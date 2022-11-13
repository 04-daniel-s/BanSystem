package de.lecuutex.bansystem.listener;

import de.lecuutex.bansystem.BanSystem;
import de.lecuutex.bansystem.utils.database.service.PenaltyService;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * A class created by yi.dnl - 12.11.2022 / 22:19
 */

public class ChatListener implements Listener {

    private final PenaltyService penaltyService = BanSystem.getInstance().getPenaltyService();

    @EventHandler
    public void onEvent(ChatEvent event) {
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();

        if (event.isCommand() || event.isProxyCommand()) return;

        if (penaltyService.isMuted(player.getUniqueId().toString())) {
            player.sendMessage("Du bist gemutet");
            event.setCancelled(true);
        }
    }
}
