package de.lecuutex.bansystem;

import de.lecuutex.bansystem.commands.BanCommand;
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
        mySQL = new MySQL();

        getProxy().getPluginManager().registerCommand(this, new BanCommand());
        System.out.println("BanSystem is up an running");
    }

    @Override
    public void onDisable() {
    }
}
