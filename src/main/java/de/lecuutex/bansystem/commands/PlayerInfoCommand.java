package de.lecuutex.bansystem.commands;

import de.lecuutex.bansystem.commands.manager.AbstractTeamCommand;
import de.lecuutex.bansystem.utils.Utils;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * A class created by yi.dnl - 13.11.2022 / 20:29
 */

public class PlayerInfoCommand extends AbstractTeamCommand {

    public PlayerInfoCommand() {
        super("pinfo");
    }

    @Override
    public void commandBody(ProxiedPlayer player, String[] args) {
        if (getCloudNetDriver().getPermissionManagement().getUser(Utils.getUUIDByName(args[0])).hasPermission("bansystem.ignore").asBoolean()) {
        }
    }

    @Override
    public void sendUsage(ProxiedPlayer player) {
        player.sendMessage("/pinfo <Player>");
    }

    @Override
    public String getPermission() {
        return "bansystem.playerinfo";
    }

    @Override
    public int getMinArgsLength() {
        return 1;
    }
}
