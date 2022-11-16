package de.lecuutex.bansystem.utils.database.service;

import de.lecuutex.bansystem.BanSystem;
import de.lecuutex.bansystem.utils.MinecraftPlayer;
import de.lecuutex.bansystem.utils.database.repository.PenaltyRepository;
import de.lecuutex.bansystem.utils.database.repository.PlayerRepository;
import de.lecuutex.bansystem.utils.penalty.PenaltyType;
import net.md_5.bungee.api.ProxyServer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * A class created by yi.dnl - 13.11.2022 / 22:03
 */

public class PlayerService {

    private final ProxyServer proxyServer = BanSystem.getInstance().getProxy();

    private final PenaltyService penaltyService = BanSystem.getInstance().getPenaltyService();

    private final PlayerRepository playerRepository = new PlayerRepository();

    private final Cache cache = BanSystem.getInstance().getCache();

    public void postPlayer(String uuid) {
        MinecraftPlayer minecraftPlayer = getMinecraftPlayer(uuid);
        playerRepository.updatePlayer(minecraftPlayer);
    }

    public MinecraftPlayer getMinecraftPlayer(String uuid) {
        if (cache.isInCache(uuid)) {
            return cache.getPlayer(uuid);
        }

        MinecraftPlayer player = null;
        if (proxyServer.getPlayer(UUID.fromString(uuid)) != null) {
            player = new MinecraftPlayer(BanSystem.getInstance().getProxy().getPlayer(UUID.fromString(uuid)));
        }

        if (isPresent(uuid)) {
            try {
                ResultSet rs = playerRepository.getPlayer(uuid);
                while (rs.next()) {
                    player = new MinecraftPlayer(rs.getString("name"), rs.getString("uuid"), rs.getString("ip_address"), rs.getLong("first_join"), penaltyService.isMuted(uuid), penaltyService.isBanned(uuid));
                    cache.cacheMinecraftPlayer(player);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return player;
    }

    public boolean isPresent(String uuid) {
        return playerRepository.isPresent(uuid);
    }
}
