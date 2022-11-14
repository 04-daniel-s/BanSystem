package de.lecuutex.bansystem.commands;

import de.lecuutex.bansystem.commands.manager.AbstractTeamCommand;
import de.lecuutex.bansystem.utils.MinecraftPlayer;
import de.lecuutex.bansystem.utils.Utils;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.UUID;

/**
 * A class created by yi.dnl - 13.11.2022 / 20:29
 */

public class PlayerInfoCommand extends AbstractTeamCommand {

    public PlayerInfoCommand() {
        super("pinfo");
    }

    @Override
    public void commandBody(ProxiedPlayer player, String[] args) {
        UUID uuid = Utils.getUUIDByName(args[0]);
        MinecraftPlayer minecraftPlayer = getPlayerService().getMinecraftPlayer(uuid.toString());

        if (minecraftPlayer == null) {
            player.sendMessage(getInfoPrefix() + "This player has never been on the server!");
            return;
        }

        player.sendMessage(getInfoPrefix() + "Name: §a" + minecraftPlayer.getName());
        player.sendMessage(getInfoPrefix() + "First Join: §a" + Utils.getDateByMilliseconds(minecraftPlayer.getFirstJoin()));
        player.sendMessage(getInfoPrefix() + "Status: §a" + (minecraftPlayer.isBanned() ? "§4Banned" : (getProxyServer().getPlayer(args[0]) != null ? "§aOnline, in " + getProxyServer().getPlayer(args[0]).getServer().getInfo().getName() : "§cOffline")));
        player.sendMessage(getInfoPrefix() + "Banpoints: §a" + getPenaltyService().getBanPoints(uuid.toString()));
        player.sendMessage(getInfoPrefix() + "Banreasons: §a" + getPenaltyService().getBanReasons(uuid.toString()));
        player.sendMessage(getInfoPrefix() + "Warnpoints: §a" + getPenaltyService().getWarnPoints(uuid.toString()));
        player.sendMessage(getInfoPrefix() + "Warnreasons: §a" + getPenaltyService().getWarnReasons(uuid.toString()));

        //TODO: Since 24 hours
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
