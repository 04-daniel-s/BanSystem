package de.lecuutex.bansystem.utils.database;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * A class created by yi.dnl - 11.11.2022 / 21:19
 */

public class MySQL {
    private Connection connection;

    public MySQL(Connection connection) {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://193.141.60.34:3306/bansystem?autoReconnect=true", "bansystem", "URilLUOzfzRegwix");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}