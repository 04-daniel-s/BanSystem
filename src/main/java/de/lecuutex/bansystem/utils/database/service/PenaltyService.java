package de.lecuutex.bansystem.utils.database.service;

import de.lecuutex.bansystem.utils.database.repository.PenaltyRepository;
import de.lecuutex.bansystem.utils.penalty.BanDuration;
import de.lecuutex.bansystem.utils.penalty.MuteDuration;
import de.lecuutex.bansystem.utils.penalty.PenaltyReason;
import de.lecuutex.bansystem.utils.penalty.PenaltyType;
import jdk.jfr.internal.PlatformRecorder;
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

    public void postBan(ProxiedPlayer creator, ProxiedPlayer target, PenaltyReason reason) {
        repository.postPenalty(creator, target, PenaltyType.BAN, reason, BanDuration.getDurationByReason(reason));

        //TODO send team message
        creator.sendMessage();
        target.sendMessage();
    }

    public void postMute(ProxiedPlayer creator, ProxiedPlayer target, PenaltyReason reason) {
        repository.postPenalty(creator, target, PenaltyType.MUTE, reason, MuteDuration.getDurationByReason(reason));

        //TODO send team message
        creator.sendMessage();
        target.sendMessage();
    }

    public void postWarn(ProxiedPlayer creator, ProxiedPlayer target, PenaltyReason reason) {
        repository.postPenalty(creator, target, PenaltyType.WARN, reason, null);

        //TODO send team message
        creator.sendMessage();
        target.sendMessage();

        if (reason.getId() < 4 && repository.getWarnAmount(target) > 0) {
            postMute(creator, target, reason);
        }
    }

    public void removeBan(ProxiedPlayer creator, ProxiedPlayer target) {
        repository.removePenalty(target, PenaltyType.BAN);

        creator.sendMessage("");
        target.sendMessage("");
    }

    public void removeMute(ProxiedPlayer creator, ProxiedPlayer target) {
        repository.removePenalty(target, PenaltyType.MUTE);

        creator.sendMessage("");
        target.sendMessage("");
    }

    public boolean isBanned(ProxiedPlayer player) {
        return repository.getLatestTimestamp(player, PenaltyType.BAN) + repository.getLatestDuration(player, PenaltyType.BAN) - System.currentTimeMillis() > 0;
    }

    public boolean isMuted(ProxiedPlayer player) {
        return repository.getLatestTimestamp(player, PenaltyType.MUTE) + repository.getLatestDuration(player, PenaltyType.MUTE) - System.currentTimeMillis() > 0;
    }
}
