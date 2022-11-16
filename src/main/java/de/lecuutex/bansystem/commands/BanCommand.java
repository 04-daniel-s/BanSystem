package de.lecuutex.bansystem.commands;

import de.lecuutex.bansystem.commands.manager.AbstractTeamCommand;
import de.lecuutex.bansystem.utils.Utils;
import de.lecuutex.bansystem.utils.penalty.PenaltyReason;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * A class created by yi.dnl - 12.11.2022 / 01:47
 */

public class BanCommand extends AbstractTeamCommand {
    public BanCommand() {
        super("ban");
    }

    @Override
    public void commandBody(ProxiedPlayer player, String[] args) {
        if (getPlayerService().getMinecraftPlayer(player.getUniqueId().toString()).isBanned()) {
            player.sendMessage("Bereits gebannt");
            return;
        }

        PenaltyReason reason = PenaltyReason.getReasonById(Integer.parseInt(args[1]));

        if (targetHasPermission("bansystem.ignore")) {
            player.sendMessage("§cBan §7| §cYou cannot ban this team member!");
        }

        if (reason == PenaltyReason.EXTREME && !player.hasPermission("bansystem.extreme")) {
            player.sendMessage("§cBan §7| §cYou are not allowed to use this reason!");
            return;
        }

        getPenaltyService().postBan(player, getTarget(), reason);
    }

    @Override
    public void sendUsage(ProxiedPlayer player) {
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

    @Override
    public String getPermission() {
        return "bansystem.ban";
    }

    @Override
    public int getMinArgsLength() {
        return 2;
    }
}
