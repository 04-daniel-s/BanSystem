package de.lecuutex.bansystem;

import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

public class BanSystem extends Plugin {

    @Getter
    private static BanSystem instance;

    @Override
    public void onEnable() {
        System.out.println("BanSystem is up an running");
    }

    @Override
    public void onDisable() {
        System.out.println("BanSystem has been shut down");
    }
}
