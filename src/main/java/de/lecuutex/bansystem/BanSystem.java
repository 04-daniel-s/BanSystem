package de.lecuutex.bansystem;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.lecuutex.bansystem.commands.*;
import de.lecuutex.bansystem.listener.ChatListener;
import de.lecuutex.bansystem.listener.JoinListener;
import de.lecuutex.bansystem.utils.database.MySQL;
import de.lecuutex.bansystem.utils.database.service.Cache;
import de.lecuutex.bansystem.utils.database.service.PenaltyService;
import de.lecuutex.bansystem.utils.database.service.PlayerService;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

@Getter
public class BanSystem extends Plugin {

    @Getter
    private static BanSystem instance;

    private MySQL mySQL;

    private Cache cache;

    private PlayerService playerService;

    private PenaltyService penaltyService;

    CloudNetDriver cloudNetDriver;

    @Override
    public void onEnable() {
        instance = this;
        cloudNetDriver = CloudNetDriver.getInstance();
        mySQL = new MySQL();
        cache = new Cache();
        playerService = new PlayerService();
        penaltyService = new PenaltyService();

        getProxy().getPluginManager().registerCommand(this, new BanCommand());
        getProxy().getPluginManager().registerCommand(this, new PlayerInfoCommand());
        getProxy().getPluginManager().registerCommand(this, new UnbanCommand());
        getProxy().getPluginManager().registerCommand(this, new MuteCommand());
        getProxy().getPluginManager().registerCommand(this, new UnmuteCommand());
        getProxy().getPluginManager().registerCommand(this, new WarnCommand());
        getProxy().getPluginManager().registerListener(this, new ChatListener());
        getProxy().getPluginManager().registerListener(this, new JoinListener());
        System.out.println("BanSystem is up an running");
    }

    @Override
    public void onDisable() {
    }
}