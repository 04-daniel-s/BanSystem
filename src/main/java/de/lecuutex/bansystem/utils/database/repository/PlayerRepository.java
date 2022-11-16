package de.lecuutex.bansystem.utils.database.repository;

import com.google.gson.Gson;
import de.lecuutex.bansystem.utils.MinecraftPlayer;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A class created by yi.dnl - 13.11.2022 / 20:07
 */

public class PlayerRepository extends AbstractRepository {

    private final String insertString = "INSERT INTO players(uuid, name, ip_address, first_join) VALUES (?,?,?,?)";

    private final String updateString = "ON DUPLICATE KEY UPDATE uuid = ?, name = ?, ip_address = ?, first_join = ?";

    public void updatePlayer(MinecraftPlayer player) {
        updateData(insertString + " " + updateString, player.getId(), player.getName(), player.getIpAddress(), player.getFirstJoin());
    }

    public ResultSet getPlayer(String uuid) {
        return queryData("SELECT * FROM players WHERE uuid = ?", uuid);
    }

    public boolean isPresent(String uuid) {
        ResultSet rs = queryData("SELECT * FROM players WHERE uuid = ?", uuid);
        try {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
