package de.lecuutex.bansystem.utils.database.service;

import com.google.common.collect.MultimapBuilder;
import de.lecuutex.bansystem.BanSystem;
import de.lecuutex.bansystem.utils.Utils;
import de.lecuutex.bansystem.utils.database.repository.PenaltyRepository;
import de.lecuutex.bansystem.utils.penalty.BanDuration;
import de.lecuutex.bansystem.utils.penalty.MuteDuration;
import de.lecuutex.bansystem.utils.penalty.PenaltyReason;
import de.lecuutex.bansystem.utils.penalty.PenaltyType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * A class created by yi.dnl - 12.11.2022 / 15:26
 */

public class PenaltyService {

    private final PenaltyRepository repository = new PenaltyRepository();

    private final ProxyServer proxyServer = BanSystem.getInstance().getProxy();

    private final Cache cache = BanSystem.getInstance().getCache();

    private int getPointsInternal(String uuid, PenaltyType type) {
        ResultSet resultSet = repository.getPenaltyListInternal(uuid, type);
        List<PenaltyReason> penalties = new ArrayList<>();

        try {
            while (resultSet.next()) {
                penalties.add(PenaltyReason.valueOf(resultSet.getString("reason")));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (penalties.size() == 0) return 0;
        return penalties.stream().map(p -> type == PenaltyType.BAN ? p.getBanPoints() : p.getWarnPoints()).mapToInt(Integer::intValue).sum();
    }

    public int getBanPoints(String uuid) {
        return getPointsInternal(uuid, PenaltyType.BAN);
    }

    public int getWarnPoints(String uuid) {
        return getPointsInternal(uuid, PenaltyType.WARN);
    }

    public int getWarnAmount(String uuid) {
        return repository.getWarnAmount(uuid);
    }

    public void postBan(ProxiedPlayer creator, String targetUUID, PenaltyReason reason) {
        repository.postPenalty(creator, targetUUID, PenaltyType.BAN, reason, BanDuration.getDurationByReason(reason));
        cache.saveBan(targetUUID);

        proxyServer.getPlayers().stream().forEach(p ->
                p.sendMessage("§cBan §7| §cThe player §c" + Utils.getNameByUUID(targetUUID) + "§e has been banned for §c" + reason.getReason() + "§7 from §e" + creator.getName() + "§7."));
        creator.sendMessage();
        Utils.sendMessageIfOnline(targetUUID, "");
    }

    public void postMute(ProxiedPlayer creator, String targetUUID, PenaltyReason reason) {
        repository.postPenalty(creator, targetUUID, PenaltyType.MUTE, reason, MuteDuration.getDurationByReason(reason));
        cache.saveMute(targetUUID);

        proxyServer.getPlayers().forEach(p ->
                p.sendMessage("§cMute §7| §cThe player §c" + Utils.getNameByUUID(targetUUID) + "§e has been muted for §c" + reason.getReason() + "§7 from §e" + creator.getName() + "§7."));
        creator.sendMessage();
        Utils.sendMessageIfOnline(targetUUID, "");
    }

    public void postWarn(ProxiedPlayer creator, String targetUUID, PenaltyReason reason) {
        repository.postPenalty(creator, targetUUID, PenaltyType.WARN, reason, null);

        proxyServer.getPlayers().forEach(p ->
                p.sendMessage("§cWarn §7| §cThe player §c" + Utils.getNameByUUID(targetUUID) + "§e has been warned for §c" + reason.getReason() + "§7 from §e" + creator.getName() + "§7."));
        creator.sendMessage();

        if (proxyServer.getPlayer(UUID.fromString(targetUUID)).isConnected()) {
            proxyServer.getPlayer(UUID.fromString(targetUUID)).sendMessage("§cWarn §7| §eYou have been warned for " + reason.getReason() + "§e. Please behave properly.");
        }

        if (reason.getId() < 4 && repository.getWarnAmount(targetUUID) > 0) {
            postMute(creator, targetUUID, reason);
        }
    }

    public void removeBan(ProxiedPlayer creator, String targetUUID) {
        repository.removePenalty(targetUUID, PenaltyType.BAN);
        cache.removeBan(targetUUID);

        creator.sendMessage("");
        Utils.sendMessageIfOnline(targetUUID, "");
    }

    public void removeMute(ProxiedPlayer creator, String targetUUID) {
        repository.removePenalty(targetUUID, PenaltyType.MUTE);
        cache.removeMute(targetUUID);

        creator.sendMessage("");
        Utils.sendMessageIfOnline(targetUUID, "");
    }

    public boolean isBanned(String uuid) {
        return repository.getLatestDuration(uuid, PenaltyType.BAN) == -1 || (repository.getLatestTimestamp(uuid, PenaltyType.BAN) + repository.getLatestDuration(uuid, PenaltyType.BAN) - System.currentTimeMillis() > 0);
    }

    public boolean isMuted(String uuid) {
        return repository.getLatestDuration(uuid, PenaltyType.MUTE) == -1 || repository.getLatestTimestamp(uuid, PenaltyType.MUTE) + repository.getLatestDuration(uuid, PenaltyType.MUTE) - System.currentTimeMillis() > 0;
    }

    public String getBanReasons(String uuid) {
        return Utils.penaltyListToString(getReasonsList(uuid, PenaltyType.BAN));
    }

    public String getWarnReasons(String uuid) {
        return Utils.penaltyListToString(getReasonsList(uuid, PenaltyType.WARN));
    }

    private List<PenaltyReason> getReasonsList(String uuid, PenaltyType type) {
        List<PenaltyReason> reasons = new ArrayList<>();
        ResultSet rs = repository.getPenaltyListInternal(uuid, type);

        try {
            while (rs.next()) {
                reasons.add(PenaltyReason.valueOf(rs.getString("reason")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return reasons;
    }
}
