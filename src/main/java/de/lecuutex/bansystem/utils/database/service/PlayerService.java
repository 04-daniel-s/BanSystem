package de.lecuutex.bansystem.utils.database.service;

import de.lecuutex.bansystem.BanSystem;
import de.lecuutex.bansystem.utils.MinecraftPlayer;
import de.lecuutex.bansystem.utils.database.repository.PlayerRepository;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A class created by yi.dnl - 13.11.2022 / 22:03
 */

public class PlayerService {

    private final PlayerRepository playerRepository = new PlayerRepository();

    private final PenaltyService penaltyService = BanSystem.getInstance().getPenaltyService();

    private final Cache cache = BanSystem.getInstance().getCache();

    public void postPlayer(ProxiedPlayer player) {
        MinecraftPlayer minecraftPlayer = cache.getPlayer(player.getUniqueId().toString());
        playerRepository.updatePlayer(minecraftPlayer);
    }

    public MinecraftPlayer getMinecraftPlayer(String uuid) {
        if (cache.isInCache(uuid)) {
            return cache.getPlayer(uuid);
        }

        ResultSet resultSet = playerRepository.getPlayer(uuid);

        try {
            if (resultSet.next()) {
                MinecraftPlayer minecraftPlayer = new MinecraftPlayer(resultSet.getString("name"), uuid, resultSet.getString("ip_address"), resultSet.getLong("first_join"), penaltyService.isMuted(uuid), penaltyService.isBanned(uuid));
                cache.cacheMinecraftPlayer(minecraftPlayer);
                return minecraftPlayer;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
