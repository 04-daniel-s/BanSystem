package de.lecuutex.bansystem.utils.database;

import net.md_5.bungee.api.plugin.Plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * A class created by yi.dnl - 11.11.2022 / 21:19
 */

public class MySQL {
    private Connection connection;

    public MySQL() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://193.141.60.34:3306/bansystem?autoReconnect=true", "bansystem", "URilLUOzfzRegwix");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        createTables();
        System.out.println("The database connection has been successfully established");
    }

    public void createTables() {
        try {
            PreparedStatement penalties = this.connection.prepareStatement("CREATE TABLE penalties(uuid VARCHAR(64) PRIMARY KEY, creator_uuid VARCHAR(64), penalty_type VARCHAR(64), reason VARCHAR(64), duration_minutes BIGINT, timestamp BIGINT)");
            penalties.execute();

            PreparedStatement badwords = this.connection.prepareStatement("CREATE TABLE badwords(id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY, penalty_type VARCHAR(64), message VARCHAR(64))");
            badwords.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
