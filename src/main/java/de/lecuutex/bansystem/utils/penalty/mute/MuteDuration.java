package de.lecuutex.bansystem.utils.penalty.mute;

import de.lecuutex.bansystem.utils.database.service.PenaltyService;
import de.lecuutex.bansystem.utils.penalty.DefaultDuration;
import de.lecuutex.bansystem.utils.penalty.PenaltyReason;
import lombok.AllArgsConstructor;
import lombok.Getter;

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

    private final PenaltyService penaltyService = new PenaltyService();

    @Override
    public Long getDuration(String uuid) {
        int points = penaltyService.getWarnAmount(uuid);
        if (points >= durations.size()) points = durations.size() - 1;
        return durations.get(points);
    }

    public static MuteDuration getDurationByReason(PenaltyReason reason) {
        return Arrays.stream(values()).filter(p -> p.toString().equals(reason.toString())).findFirst().get();
    }
}
