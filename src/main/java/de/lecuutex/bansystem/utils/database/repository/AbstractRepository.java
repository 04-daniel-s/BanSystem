package de.lecuutex.bansystem.utils.database.repository;

import com.google.gson.Gson;
import de.lecuutex.bansystem.BanSystem;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;

/**
 * A class created by yi.dnl - 12.11.2022 / 13:31
 */

@Getter
public abstract class AbstractRepository {

    private final ExecutorService es = BanSystem.getInstance().getExecutorService();

    private final Connection connection = BanSystem.getInstance().getMySQL().getConnection();

    public void updateData(String sql, Object... data) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < data.length; i++) {
                preparedStatement.setObject(i + 1, data[i]);
                if (sql.contains("ON DUPLICATE KEY UPDATE")) {
                    preparedStatement.setObject(i + 1 + data.length, data[i]);
                }
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet queryData(String sql, Object... data) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < data.length; i++) {
                preparedStatement.setObject(i + 1, data[i]);
            }
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
