package de.lecuutex.bansystem.commands;

import de.lecuutex.bansystem.BanSystem;
import de.lecuutex.bansystem.utils.Utils;
import de.lecuutex.bansystem.utils.database.service.PenaltyService;
import de.lecuutex.bansystem.utils.penalty.PenaltyReason;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * A class created by yi.dnl - 12.11.2022 / 19:52
 */

public class MuteCommand extends Command {
    private final ProxyServer proxyServer = BanSystem.getInstance().getProxy();

    private final PenaltyService service = new PenaltyService();

    public MuteCommand() {
        super("mute");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (args.length == 2) {
            ProxiedPlayer target = proxyServer.getPlayer(args[0]) != null ? proxyServer.getPlayer(args[0]) : proxyServer.getPlayer(Utils.getUUIDByName(args[0]));

            if (target == null) {
                player.sendMessage("Dieser Spieler ist nicht online");
                return;
            }

            if (service.isMuted(target)) {
                player.sendMessage("Bereits gemutet");
                return;
            }

            if (Integer.parseInt(args[1]) > 3) {
                return;
            }

            PenaltyReason reason = PenaltyReason.getReasonById(Integer.parseInt(args[1]));
            service.postMute(player, target, reason);
            return;
        }

        player.sendMessage("§8§7» ----------- × Mutesystem × ----------- «");
        player.sendMessage("§cBan §7┃ /mute (Player) (Number)");
        player.sendMessage("§cBan §7┃ §a1   §7» §aChat behavior");
        player.sendMessage("§cBan §7┃ §a2   §7» §aBehavior");
        player.sendMessage("§cBan §7┃ §a3   §7» §aAdvertisement");
        player.sendMessage("§8§7» ----------- × Mutesystem × -----------  «");
    }
}
