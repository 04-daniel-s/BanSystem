package de.lecuutex.bansystem.utils.database.repository;

import de.lecuutex.bansystem.utils.penalty.*;
import jdk.jfr.internal.PlatformRecorder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.security.InvalidKeyException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A class created by yi.dnl - 12.11.2022 / 13:30
 */

public class PenaltyRepository extends AbstractRepository {

    private String insertString = "INSERT INTO penalties(uuid, creator_uuid, penalty_type,reason, duration_milliseconds,timestamp) VALUES (?,?,?,?,?,?)";

    public void postPenalty(ProxiedPlayer creator, String targetUUID, PenaltyType type, PenaltyReason reason, DefaultDuration defaultDuration) {
        long duration = defaultDuration != null ? (defaultDuration instanceof MuteDuration ? defaultDuration.getDuration(targetUUID) : ((BanDuration) defaultDuration).getDuration()) : 0;
        updateData(insertString, targetUUID, creator.getUniqueId().toString(), type.toString(), reason.toString(), duration, System.currentTimeMillis());
    }

    public void removePenalty(String uuid, PenaltyType type) {
        int id = -1;
        ResultSet resultSet = queryData("SELECT id FROM penalties WHERE uuid = ? AND penalty_type = ? ORDER BY timestamp DESC LIMIT 1", uuid, type.toString());

        try {
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateData("UPDATE penalties SET duration_milliseconds = 0 WHERE id = ?", id);
    }

    public ResultSet getPenaltyListInternal(ProxiedPlayer player, PenaltyType type) {
        return queryData("SELECT * FROM penalties WHERE uuid = ? AND penalty_type = ?", player.getUniqueId().toString(), type.toString());
    }

    public int getWarnAmount(String uuid) {
        int amount = 0;
        ResultSet rs = queryData("SELECT COUNT(*) as amount FROM penalties WHERE uuid = ? AND penalty_type = ?", uuid, PenaltyType.WARN.toString());

        try {
            if (rs.next()) {
                amount = rs.getInt("amount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return amount;
    }

    public Long getLatestTimestamp(String uuid, PenaltyType type) {
        long latestTimestamp = 0;
        ResultSet resultSet = queryData("SELECT timestamp FROM penalties WHERE uuid = ? AND penalty_type = ? ORDER BY timestamp DESC LIMIT 1", uuid, type.toString());

        try {
            if (resultSet.next()) {
                latestTimestamp = resultSet.getLong("timestamp");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return latestTimestamp;
    }

    public Long getLatestDuration(String uuid, PenaltyType type) {
        long latestDuration = 0;
        ResultSet resultSet = queryData("SELECT duration_milliseconds FROM penalties WHERE uuid = ? AND penalty_type = ? ORDER BY duration_milliseconds DESC LIMIT 1", uuid, type.toString());

        try {
            if (resultSet.next()) {
                latestDuration = resultSet.getLong("duration_milliseconds");
            }
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
        return latestDuration;
    }
}
