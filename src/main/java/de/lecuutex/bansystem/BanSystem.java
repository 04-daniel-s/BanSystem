package de.lecuutex.bansystem;

import de.lecuutex.bansystem.commands.BanCommand;
import de.lecuutex.bansystem.commands.MuteCommand;
import de.lecuutex.bansystem.commands.WarnCommand;
import de.lecuutex.bansystem.utils.database.MySQL;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

@Getter
public class BanSystem extends Plugin {

    @Getter
    private static BanSystem instance;

    private MySQL mySQL;

    @Override
    public void onEnable() {
        instance = this;
        mySQL = new MySQL();

        getProxy().getPluginManager().registerCommand(this, new BanCommand());
        getProxy().getPluginManager().registerCommand(this, new MuteCommand());
        getProxy().getPluginManager().registerCommand(this, new WarnCommand());
        System.out.println("BanSystem is up an running");
    }

    @Override
    public void onDisable() {
    }
}
