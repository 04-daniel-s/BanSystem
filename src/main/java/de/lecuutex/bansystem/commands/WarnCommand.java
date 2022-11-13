package de.lecuutex.bansystem.commands;

import de.lecuutex.bansystem.commands.manager.AbstractTeamCommand;
import de.lecuutex.bansystem.utils.penalty.PenaltyReason;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.lang.annotation.Target;
import java.util.UUID;

/**
 * A class created by yi.dnl - 12.11.2022 / 19:51
 */

public class WarnCommand extends AbstractTeamCommand {

    public WarnCommand() {
        super("warn");
    }

    @Override
    public void commandBody(ProxiedPlayer player, String[] args) {
        PenaltyReason reason = PenaltyReason.getReasonById(Integer.parseInt(args[1]));

        if (getCloudNetDriver().getPermissionManagement().getUser(UUID.fromString(getTarget())).hasPermission("bansystem.ignore").asBoolean()) {
            player.sendMessage("§cBan §7| §cYou are not allowed to affect this player!");
            return;
        }

        if (reason == PenaltyReason.EXTREME && !player.hasPermission("bansystem.extreme")) {
            player.sendMessage("§cWarn §7| §cYou are not allowed to use this reason!");
            return;
        }

        getService().postWarn(player, getTarget(), reason);
    }

    @Override
    public void sendUsage(ProxiedPlayer player) {
        player.sendMessage("§8§7» ----------- × Warnsystem × ----------- «");
        player.sendMessage("§cBan §7┃ /warn (Player) (Number)");
        player.sendMessage("§cBan §7┃ §a1   §7» §aChat behavior");
        player.sendMessage("§cBan §7┃ §a2   §7» §aBehavior");
        player.sendMessage("§cBan §7┃ §a3   §7» §aAdvertisement");
        player.sendMessage("§cBan §7┃ §a4   §7» §aTrolling");
        player.sendMessage("§cBan §7┃ §a5   §7» §aTeaming");
        player.sendMessage("§cBan §7┃ §a6   §7» §aSkin");
        player.sendMessage("§cBan §7┃ §a7   §7» §aName");
        player.sendMessage("§cBan §7┃ §a8   §7» §aRank utilization");
        player.sendMessage("§8§7» ----------- × Warnsystem × -----------  «");
    }

    @Override
    public String getPermission() {
        return "bansystem.warn";
    }

    @Override
    public int getMinArgsLength() {
        return 2;
    }
}
