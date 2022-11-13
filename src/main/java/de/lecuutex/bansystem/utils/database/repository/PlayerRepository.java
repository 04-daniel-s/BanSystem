package de.lecuutex.bansystem.utils.database.repository;

import de.lecuutex.bansystem.utils.MinecraftPlayer;

import java.sql.ResultSet;

/**
 * A class created by yi.dnl - 13.11.2022 / 20:07
 */

public class PlayerRepository extends AbstractRepository {

    private final String insertString = "INSERT INTO players(uuid, name, ip_address,first_join VALUES (?,?,?,?)";

    private final String updateString = "ON DUPLICATE KEY UPDATE players SET uuid = ?, name = ?, ip_address = ?, first_join = ?";

    public void updatePlayer(MinecraftPlayer player) {
        updateData(insertString + updateString, player.getId(), player.getName(), player.getIpAddress(), player.getFirstJoin());
    }

    public ResultSet getPlayer(String uuid) {
        return queryData("SELECT * FROM players WHERE uuid = ?", uuid);
    }
}
