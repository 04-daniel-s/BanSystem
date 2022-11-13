package de.lecuutex.bansystem.utils.database.repository;

import de.lecuutex.bansystem.BanSystem;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * A class created by yi.dnl - 12.11.2022 / 13:31
 */

@Getter
public abstract class AbstractRepository {

    private final ExecutorService es = Executors.newSingleThreadExecutor();

    private final Connection connection = BanSystem.getInstance().getMySQL().getConnection();

    public void updateData(String sql, Object... data) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < data.length; i++) {
                preparedStatement.setObject(i + 1, data[i]);
                if (sql.contains("ON DUPLICATE KEY UPDATE")) {
                    preparedStatement.setObject(i + data.length, data[i + data.length]);
                }
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet queryData(String sql, Object... data) {
        ResultSet rs = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < data.length; i++) {
                preparedStatement.setObject(i + 1, data[i]);
            }
            rs = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }
}
