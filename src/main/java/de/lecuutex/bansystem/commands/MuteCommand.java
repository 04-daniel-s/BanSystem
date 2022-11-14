package de.lecuutex.bansystem.commands;

import de.lecuutex.bansystem.commands.manager.AbstractTeamCommand;
import de.lecuutex.bansystem.utils.penalty.PenaltyReason;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * A class created by yi.dnl - 12.11.2022 / 19:52
 */

public class MuteCommand extends AbstractTeamCommand {

    public MuteCommand() {
        super("mute");
    }

    @Override
    public void commandBody(ProxiedPlayer player, String[] args) {
        if (getPenaltyService().isMuted(getTarget())) {
            player.sendMessage("§cMute §7| §cThis player is already muted.");
            return;
        }

        if (Integer.parseInt(args[1]) > 3) {
            sendUsage(player);
            return;
        }

        PenaltyReason reason = PenaltyReason.getReasonById(Integer.parseInt(args[1]));

        if (targetHasPermission("bansystem.ignore")) {
            player.sendMessage("§cMute §7| §cYou cannot mute this team member!");
            return;
        }

        if(reason == PenaltyReason.EXTREME && !player.hasPermission("bansystem.extreme")) {
            player.sendMessage("§cMute §7| §cYou are not allowed to use this reason!");
            return;
        }

        getPenaltyService().postMute(player, getTarget(), reason);
    }

    @Override
    public void sendUsage(ProxiedPlayer player) {
        player.sendMessage("§8§7» ----------- × Mutesystem × ----------- «");
        player.sendMessage("§cBan §7┃ /mute (Player) (Number)");
        player.sendMessage("§cBan §7┃ §a1   §7» §aChat behavior");
        player.sendMessage("§cBan §7┃ §a2   §7» §aBehavior");
        player.sendMessage("§cBan §7┃ §a3   §7» §aAdvertisement");
        player.sendMessage("§8§7» ----------- × Mutesystem × -----------  «");
    }

    @Override
    public String getPermission() {
        return "bansystem.mute";
    }

    @Override
    public int getMinArgsLength() {
        return 2;
    }
}
