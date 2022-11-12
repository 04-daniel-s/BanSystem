package de.lecuutex.bansystem.utils.database.repository;

import de.lecuutex.bansystem.utils.penalty.*;
import jdk.jfr.internal.PlatformRecorder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A class created by yi.dnl - 12.11.2022 / 13:30
 */

public class PenaltyRepository extends AbstractRepository {

    public void postPenalty(ProxiedPlayer creator, ProxiedPlayer target, PenaltyType type, PenaltyReason reason, DefaultDuration defaultDuration) {
        new Thread(() -> {
            long duration = defaultDuration != null ? (defaultDuration instanceof MuteDuration ? defaultDuration.getDuration(target) : ((BanDuration) defaultDuration).getDuration()) : 0;

            try {
                PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO penalties(uuid, creator_uuid, penalty_type,reason, duration_milliseconds,timestamp) VALUES (?,?,?,?,?,?)");
                preparedStatement.setString(1, target.getUniqueId().toString());
                preparedStatement.setString(2, creator.getUniqueId().toString());
                preparedStatement.setString(3, type.toString());
                preparedStatement.setString(4, reason.toString());
                preparedStatement.setLong(5, duration);
                preparedStatement.setLong(6, System.currentTimeMillis());
                preparedStatement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void removePenalty(ProxiedPlayer player, PenaltyType type) {
        new Thread(() -> {
            try {
                int id = -1;

                PreparedStatement statement = getConnection().prepareStatement("SELECT id FROM penalties WHERE uuid = ? AND penalty_type = ? ORDER BY timestamp DESC LIMIT 1");
                statement.setString(1, player.getUniqueId().toString());
                statement.setString(2, type.toString());

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    id = resultSet.getInt("id");
                }

                PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE penalties SET duration_milliseconds = 0 WHERE id = ?");
                preparedStatement.setInt(1, id);
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public ResultSet getPenaltyListInternal(ProxiedPlayer player, PenaltyType type) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM penalties WHERE uuid = ? AND penalty_type = ?");
            preparedStatement.setString(1, player.getUniqueId().toString());
            preparedStatement.setString(2, type.toString());

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public int getWarnAmount(ProxiedPlayer player) {
        int amount = 0;

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT COUNT(*) as amount FROM penalties WHERE uuid = ? AND penalty_type = ?");
            preparedStatement.setString(1, player.getUniqueId().toString());
            preparedStatement.setString(2, PenaltyType.WARN.toString());
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                amount = rs.getInt("amount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return amount;
    }

    public Long getLatestTimestamp(ProxiedPlayer player, PenaltyType type) {
        long latestTimestamp = 0;
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT timestamp FROM penalties WHERE uuid = ? AND penalty_type = ? ORDER BY timestamp DESC LIMIT 1");
            preparedStatement.setString(1, player.getUniqueId().toString());
            preparedStatement.setString(2, type.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                latestTimestamp = resultSet.getLong("timestamp");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return latestTimestamp;
    }

    public Long getLatestDuration(ProxiedPlayer player, PenaltyType type) {
        long latestDuration = 0;
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT duration_milliseconds FROM penalties WHERE uuid = ? AND penalty_type = ? ORDER BY duration_milliseconds DESC LIMIT 1");
            preparedStatement.setString(1, player.getUniqueId().toString());
            preparedStatement.setString(2, type.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                latestDuration = resultSet.getLong("duration_milliseconds");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return latestDuration;
    }
}
