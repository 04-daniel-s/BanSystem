package de.lecuutex.bansystem.utils.database;

import net.md_5.bungee.api.plugin.Plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A class created by yi.dnl - 11.11.2022 / 21:19
 */

public class MySQL extends Plugin {
    private Connection connection;

    public MySQL() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://193.141.60.34:3306/bansystem?autoReconnect=true", "bansystem", "URilLUOzfzRegwix");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        System.out.println("The database connection has been successfully established");
    }
}
