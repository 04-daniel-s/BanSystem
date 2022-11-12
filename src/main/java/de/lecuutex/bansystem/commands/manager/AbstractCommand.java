package de.lecuutex.bansystem.commands.manager;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

/**
 * A class created by yi.dnl - 12.11.2022 / 21:08
 */

public abstract class AbstractCommand extends Command {
    public AbstractCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

    }
}
