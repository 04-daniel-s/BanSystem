package de.lecuutex.bansystem.utils.penalty;

import de.lecuutex.bansystem.utils.database.repository.PenaltyRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Arrays;
import java.util.List;

/**
 * A class created by yi.dnl - 12.11.2022 / 14:31
 */

@Getter
@AllArgsConstructor
public enum MuteDuration implements DefaultDuration {
    ADVERTISEMENT(Arrays.asList(minutesToMillis(15), minutesToMillis(30), minutesToMillis(45), minutesToMillis(60), daysToMillis(1), -1L)),
    CHATBEHAVIOR(Arrays.asList(minutesToMillis(15), minutesToMillis(30), minutesToMillis(45), minutesToMillis(60), daysToMillis(1), -1L)),
    BEHAVIOR(Arrays.asList(minutesToMillis(15), minutesToMillis(30), minutesToMillis(45), minutesToMillis(60), daysToMillis(1), -1L));

    private final List<Long> durations;

    private static Long minutesToMillis(int minutes) {
        return (1000L * 60 * 60 * 24 * minutes);
    }

    private static Long daysToMillis(int days) {
        return (1000L * 60 * 60 * 24 * days);
    }

    private final PenaltyRepository penaltyRepository = new PenaltyRepository();

    @Override
    public Long getDuration(ProxiedPlayer player) {
        return durations.get(penaltyRepository.getWarnPoints(player));
    }
}
