package de.lecuutex.bansystem.utils.database.repository;

import de.lecuutex.bansystem.BanSystem;
import lombok.Getter;

import java.sql.Connection;

/**
 * A class created by yi.dnl - 12.11.2022 / 13:31
 */

@Getter
public abstract class AbstractRepository {
    private final Connection connection = BanSystem.getInstance().getMySQL().getConnection();
}
