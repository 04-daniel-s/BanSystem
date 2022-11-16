package de.lecuutex.bansystem.commands.manager;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.lecuutex.bansystem.BanSystem;
import de.lecuutex.bansystem.utils.Utils;
import de.lecuutex.bansystem.utils.database.service.PenaltyService;
import de.lecuutex.bansystem.utils.database.service.PlayerService;
import lombok.Getter;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A class created by yi.dnl - 12.11.2022 / 21:08
 */

@Getter
public abstract class AbstractTeamCommand extends Command {

    public AbstractTeamCommand(String command) {
        super(command);
    }

    private final String banPrefix = "§cBan §7| §c ";

    private final String infoPrefix = "§cInfo §7| §7 ";

    private final CloudNetDriver cloudNetDriver = BanSystem.getInstance().getCloudNetDriver();

    private final ProxyServer proxyServer = BanSystem.getInstance().getProxy();

    private final PenaltyService penaltyService = BanSystem.getInstance().getPenaltyService();

    private final PlayerService playerService = BanSystem.getInstance().getPlayerService();

    private String target = null;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (!player.hasPermission(getPermission())) {
            player.sendMessage("§6Akerus §7| §cYou cannot use this command.");
            return;
        }

        if (args.length == getMinArgsLength()) {
            target = proxyServer.getPlayer(args[0]) != null ? proxyServer.getPlayer(args[0]).getUniqueId().toString() : String.valueOf(Utils.getUUIDByName(args[0]));

            if (target == null) {
                player.sendMessage("Dieser Spieler war noch nie auf dem Server");
                return;
            }

            executorService.submit(() -> commandBody(player, args));
            return;
        }

        sendUsage(player);
    }

    public boolean targetHasPermission(String permission) {
        return cloudNetDriver.getPermissionManagement().getUser(UUID.fromString(target)).hasPermission(permission).asBoolean();
    }

    public abstract void commandBody(ProxiedPlayer player, String[] args);

    public abstract void sendUsage(ProxiedPlayer player);

    public abstract String getPermission();

    public abstract int getMinArgsLength();
}
