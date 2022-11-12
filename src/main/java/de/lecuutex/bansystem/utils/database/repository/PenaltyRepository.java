package de.lecuutex.bansystem.utils.database.repository;

import de.lecuutex.bansystem.utils.penalty.DefaultDuration;
import de.lecuutex.bansystem.utils.penalty.PenaltyReason;
import de.lecuutex.bansystem.utils.penalty.PenaltyType;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A class created by yi.dnl - 12.11.2022 / 13:30
 */

public class PenaltyRepository extends AbstractRepository {
    public void postPenalty(ProxiedPlayer player, ProxiedPlayer target, PenaltyType type, PenaltyReason reason, DefaultDuration defaultDuration) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO penalties(uuid, creator_uuid, penalty_type,reason, duration_milliseconds,timestamp) VALUES (?,?,?,?,?,?)");
            preparedStatement.setString(1, player.getUniqueId().toString());
            preparedStatement.setString(2, target.getUniqueId().toString());
            preparedStatement.setString(3, type.toString());
            preparedStatement.setString(4, reason.toString());
            preparedStatement.setLong(5, defaultDuration.getDuration(player));
            preparedStatement.setLong(6, System.currentTimeMillis());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removePenalty() {

    }

    public int getWarnPoints(ProxiedPlayer player) {
        AtomicInteger points = new AtomicInteger();

        new Thread(() -> {
            try {
                PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT COUNT(*) as points FROM penalties WHERE uuid = ?");
                preparedStatement.setString(1, player.getUniqueId().toString());
                ResultSet rs = preparedStatement.executeQuery();

                if (rs.next()) {
                    points.set(rs.getInt("points"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return points.get();
    }
}
