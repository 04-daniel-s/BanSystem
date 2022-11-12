package de.lecuutex.bansystem.commands;

import de.lecuutex.bansystem.BanSystem;
import de.lecuutex.bansystem.utils.Utils;
import de.lecuutex.bansystem.utils.database.service.PenaltyService;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * A class created by yi.dnl - 12.11.2022 / 21:07
 */

public class UnmuteCommand extends Command {

    private final ProxyServer proxyServer = BanSystem.getInstance().getProxy();

    private final PenaltyService service = new PenaltyService();

    public UnmuteCommand() {
        super("unmute");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (args.length == 1) {
            ProxiedPlayer target = proxyServer.getPlayer(args[0]) != null ? proxyServer.getPlayer(args[0]) : proxyServer.getPlayer(Utils.getUUIDByName(args[0]));

            if (target == null) {
                player.sendMessage("Dieser Spieler ist nicht online");
                return;
            }

            if(!service.isMuted(target)) {
                player.sendMessage("Nicht gemutet");
                return;
            }

            service.removeMute(player, target);
            return;
        }

        player.sendMessage("/unmute <Player>");
    }
}
