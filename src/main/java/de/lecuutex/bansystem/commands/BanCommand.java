package de.lecuutex.bansystem.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * A class created by yi.dnl - 12.11.2022 / 01:47
 */

public class BanCommand extends Command {
    public BanCommand() {
        super("ban");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (args.length == 2) {
            //TODO Ban
            return;
        }

        player.sendMessage("§8§7» ----------- × Bansystem × ----------- «");
        player.sendMessage("§cBan §7┃ /ban(Player) (Number)");
        player.sendMessage("§cBan §7┃ §a1  §7» §aHacking §7┃ Permanent");
        player.sendMessage("§cBan §7┃ §a2  §7» §aBehavior §7┃ 14 Days");
        player.sendMessage("§cBan §7┃ §a3  §7» §aChat behavior §7┃ 14 Days");
        player.sendMessage("§cBan §7┃ §a4  §7» §aTrolling §7┃ 7 Days");
        player.sendMessage("§cBan §7┃ §a5  §7» §aTeaming §7┃ 7 Days");
        player.sendMessage("§cBan §7┃ §a6  §7» §aBan bypass §7┃ Permanent");
        player.sendMessage("§cBan §7┃ §a7  §7» §aSkin §7┃ 14 Days");
        player.sendMessage("§cBan §7┃ §a8  §7» §aName §7┃ 30 Days");
        player.sendMessage("§cBan §7┃ §a9  §7» §aAdvertisement §7┃ 14 Days");
        player.sendMessage("§cBan §7┃ §a10 §7» §aRank utilization §7┃ 30 Days");
        player.sendMessage("§cBan §7┃ §a11 §7» §aHausverbot §7┃ Permanent");
        player.sendMessage("§8§7» ----------- × Bansystem × -----------  «");
    }
}
