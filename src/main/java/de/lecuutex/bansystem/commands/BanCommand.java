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
 * A class created by yi.dnl - 12.11.2022 / 01:47
 */

public class BanCommand extends Command {
    private final ProxyServer proxyServer = BanSystem.getInstance().getProxy();

    private final PenaltyService service = new PenaltyService();

    public BanCommand() {
        super("ban");
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

            if(service.isBanned(target)) {
                player.sendMessage("Bereits gebannt");
                return;
            }

            PenaltyReason reason = PenaltyReason.getReasonById(Integer.parseInt(args[1]));
            service.postBan(player, target, reason);
            return;
        }

        player.sendMessage("§8§7» ----------- × Bansystem × ----------- «");
        player.sendMessage("§cBan §7┃ /ban (Player) (Number)");
        player.sendMessage("§cBan §7┃ §a1   §7» §aChat behavior §7┃ 14 Days");
        player.sendMessage("§cBan §7┃ §a2   §7» §aBehavior §7┃ 14 Days");
        player.sendMessage("§cBan §7┃ §a3   §7» §aAdvertisement §7┃ 14 Days");
        player.sendMessage("§cBan §7┃ §a4   §7» §aTrolling §7┃ 7 Days");
        player.sendMessage("§cBan §7┃ §a5   §7» §aTeaming §7┃ 7 Days");
        player.sendMessage("§cBan §7┃ §a6   §7» §aSkin §7┃ 14 Days");
        player.sendMessage("§cBan §7┃ §a7   §7» §aName §7┃ 30 Days");
        player.sendMessage("§cBan §7┃ §a8   §7» §aRank utilization §7┃ 30 Days");
        player.sendMessage("§cBan §7┃ §a9   §7» §aHacking §7┃ Permanent");
        player.sendMessage("§cBan §7┃ §a10  §7» §aBan bypass §7┃ Permanent");
        player.sendMessage("§cBan §7┃ §a11  §7» §aExtreme §7┃ Permanent");
        player.sendMessage("§8§7» ----------- × Bansystem × -----------  «");
    }
}
