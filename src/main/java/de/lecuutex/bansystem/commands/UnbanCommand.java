package de.lecuutex.bansystem.commands;

import de.lecuutex.bansystem.commands.manager.AbstractTeamCommand;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * A class created by yi.dnl - 12.11.2022 / 21:05
 */

public class UnbanCommand extends AbstractTeamCommand {

    public UnbanCommand() {
        super("unban");
    }

    @Override
    public void commandBody(ProxiedPlayer player, String[] args) {
        if (!getPlayerService().getMinecraftPlayer(getTarget()).isBanned()) {
            player.sendMessage(getBanPrefix() + "§eThis player is not banned.");
            return;
        }
        getPenaltyService().removeBan(player, getTarget());
    }

    public void sendUsage(ProxiedPlayer player) {
        player.sendMessage(getBanPrefix() + "/unban <Player>");
    }

    @Override
    public String getPermission() {
        return "bansystem.unban";
    }

    @Override
    public int getMinArgsLength() {
        return 1;
    }
}
