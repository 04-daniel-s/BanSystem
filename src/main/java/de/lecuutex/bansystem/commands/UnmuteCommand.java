package de.lecuutex.bansystem.commands;

import de.lecuutex.bansystem.commands.manager.AbstractTeamCommand;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * A class created by yi.dnl - 12.11.2022 / 21:07
 */

public class UnmuteCommand extends AbstractTeamCommand {

    public UnmuteCommand() {
        super("unmute");
    }

    @Override
    public void commandBody(ProxiedPlayer player, String[] args) {
        if (!getPenaltyService().isMuted(getTarget())) {
            player.sendMessage("Nicht gemutet");
            return;
        }

        getPenaltyService().removeMute(player, getTarget());
    }

    @Override
    public void sendUsage(ProxiedPlayer player) {
        player.sendMessage("§cBan §7| /unmute <Player>");
    }

    @Override
    public String getPermission() {
        return "bansystem.unmute";
    }

    @Override
    public int getMinArgsLength() {
        return 1;
    }
}
