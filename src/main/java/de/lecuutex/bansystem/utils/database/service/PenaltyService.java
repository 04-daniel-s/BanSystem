package de.lecuutex.bansystem.utils.database.service;

import de.lecuutex.bansystem.BanSystem;
import de.lecuutex.bansystem.utils.MinecraftPlayer;
import de.lecuutex.bansystem.utils.Utils;
import de.lecuutex.bansystem.utils.database.repository.PenaltyRepository;
import de.lecuutex.bansystem.utils.penalty.Penalty;
import de.lecuutex.bansystem.utils.penalty.ban.BanDuration;
import de.lecuutex.bansystem.utils.penalty.mute.MuteDuration;
import de.lecuutex.bansystem.utils.penalty.PenaltyReason;
import de.lecuutex.bansystem.utils.penalty.PenaltyType;
import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * A class created by yi.dnl - 12.11.2022 / 15:26
 */

public class PenaltyService {

    @Getter
    private final PenaltyRepository repository = new PenaltyRepository();

    private final ProxyServer proxyServer = BanSystem.getInstance().getProxy();

    private final PlayerService playerService = new PlayerService();

    private final PenaltyService penaltyService = new PenaltyService();

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

        Utils.sendTeamMessage("§cBan §7| §eThe player §6" + Utils.getNameByUUID(targetUUID) + "§e has been §cbanned §efor §c" + reason.getReason() + "§e by §c" + creator.getName() + "§e.");
        creator.sendMessage("Du hast xy für xy gebannt");

        if (proxyServer.getPlayer(UUID.fromString(targetUUID)) != null) {
            Penalty penalty = penaltyService.getActivePenalty(playerService.getMinecraftPlayer(targetUUID), PenaltyType.BAN);
            proxyServer.getPlayer(UUID.fromString(targetUUID)).disconnect(Utils.getBanScreen(penalty));
        }
    }

    public void postMute(ProxiedPlayer creator, String targetUUID, PenaltyReason reason) {
        repository.postPenalty(creator, targetUUID, PenaltyType.MUTE, reason, MuteDuration.getDurationByReason(reason));
        cache.saveMute(targetUUID);

        Utils.sendTeamMessage("§cMute §7| §eThe player §6" + Utils.getNameByUUID(targetUUID) + "§e has been §cmuted §efor §c" + reason.getReason() + "§e by " + creator.getName() + ".");
        creator.sendMessage("§cMute §7| §eYou have muted §6" + Utils.getNameByUUID(targetUUID) + " §efor §c" + reason.getReason() + "§e.");
        Utils.sendMessageIfOnline(targetUUID, "§cMute §7| §eYou have been muted for §c" + reason.getReason() + " §eby §c" + creator.getName() + "§e.");
    }

    public void postWarn(ProxiedPlayer creator, String targetUUID, PenaltyReason reason) {
        repository.postPenalty(creator, targetUUID, PenaltyType.WARN, reason, null);

        Utils.sendTeamMessage("§cWarn §7| §eThe player §6" + Utils.getNameByUUID(targetUUID) + "§e has been §cwarned §efor §c" + reason.getReason() + "§e by " + creator.getName() + ".");
        creator.sendMessage();

        Utils.sendMessageIfOnline(targetUUID, "§cWarn §7| §eYou have been §cwarned §efor §c" + reason.getReason() + "§e. Please behave properly.");

        if (reason.getId() < 4 && repository.getWarnAmount(targetUUID) > 0) {
            postMute(creator, targetUUID, reason);
        }
    }

    public void removeBan(ProxiedPlayer creator, String targetUUID) {
        repository.removePenalty(targetUUID, PenaltyType.BAN);
        cache.removeBan(targetUUID);

        creator.sendMessage("§cBan §7| §eYou have removed the ban from §6" + playerService.getMinecraftPlayer(targetUUID).getName());
    }

    public void removeMute(ProxiedPlayer creator, String targetUUID) {
        repository.removePenalty(targetUUID, PenaltyType.MUTE);
        cache.removeMute(targetUUID);

        Utils.sendTeamMessage("§cMute §7| §eThe mute of §6" + Utils.getNameByUUID(targetUUID) + " §ehas been removed by §c" + creator.getName() + "§e.");
        creator.sendMessage("§cMute §7| §eThe mute of" + playerService.getMinecraftPlayer(targetUUID).getName() + "has been removed.");
        Utils.sendMessageIfOnline(targetUUID, "§cMute §7| §eYour mute has been removed.");
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
            e.printStackTrace();
        }

        return reasons;
    }

    public Penalty getActivePenalty(MinecraftPlayer minecraftPlayer, PenaltyType type) {
        try {
            ResultSet rs = repository.getActivePenalty(minecraftPlayer.getId(), type);
            if (rs.next()) {

                PenaltyReason reason = PenaltyReason.valueOf(rs.getString("reason"));
                String by = rs.getString("creator_uuid");
                Long latestTimestamp = repository.getLatestTimestamp(minecraftPlayer.getId(), type);
                Long duration = latestTimestamp - (repository.getLatestDuration(minecraftPlayer.getId(), type) == -1 ? latestTimestamp + 1 : repository.getLatestDuration(minecraftPlayer.getId(), type));

                return new Penalty(reason, Utils.getDateByMilliseconds(latestTimestamp), Utils.getDateByMilliseconds(duration), by);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
