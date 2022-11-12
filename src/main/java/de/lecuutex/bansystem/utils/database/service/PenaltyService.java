package de.lecuutex.bansystem.utils.database.service;

import de.lecuutex.bansystem.utils.database.repository.PenaltyRepository;
import de.lecuutex.bansystem.utils.penalty.PenaltyReason;
import de.lecuutex.bansystem.utils.penalty.PenaltyType;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * A class created by yi.dnl - 12.11.2022 / 15:26
 */

public class PenaltyService {
    
    private final PenaltyRepository repository = new PenaltyRepository();

    private int getPointsInternal(ProxiedPlayer player, PenaltyType type) {
        ResultSet resultSet = repository.getPenaltyListInternal(player, type);
        List<PenaltyReason> penalties = new ArrayList<>();

        try {
            while (resultSet.next()) {
                penalties.add(PenaltyReason.valueOf(resultSet.getString("reason")));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return penalties.stream().map(p -> type == PenaltyType.BAN ? p.getBanPoints() : p.getWarnPoints()).mapToInt(Integer::intValue).sum();
    }

    public int getBanPoints(ProxiedPlayer player) {
        return getPointsInternal(player, PenaltyType.BAN);
    }

    public int getWarnPoints(ProxiedPlayer player) {
        return getPointsInternal(player, PenaltyType.WARN);
    }

    public int getWarnAmount(ProxiedPlayer player) {
        return repository.getWarnAmount(player);
    }
}
