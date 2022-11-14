package de.lecuutex.bansystem.utils.database;

import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * A class created by yi.dnl - 11.11.2022 / 21:19
 */

public class MySQL {
    @Getter
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
            this.connection.prepareStatement("CREATE TABLE IF NOT EXISTS penalties(id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY, uuid VARCHAR(64), creator_uuid VARCHAR(64), penalty_type VARCHAR(64), reason VARCHAR(64), duration_milliseconds BIGINT, timestamp BIGINT)").execute();
            this.connection.prepareStatement("CREATE TABLE IF NOT EXISTS badwords(id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY, penalty_type VARCHAR(64), message VARCHAR(64))").execute();
            this.connection.prepareStatement("CREATE TABLE IF NOT EXISTS players(uuid VARCHAR(64) PRIMARY KEY, name VARCHAR(64), ip_address VARCHAR(64), first_join BIGINT)").execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
