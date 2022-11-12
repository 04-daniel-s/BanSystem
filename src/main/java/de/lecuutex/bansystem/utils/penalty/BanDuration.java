package de.lecuutex.bansystem.utils.penalty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.List;

/**
 * A class created by yi.dnl - 12.11.2022 / 14:31
 */

@AllArgsConstructor
@Getter
public enum BanDuration implements DefaultDuration {
    HACKING(-1L),
    BEHAVIOR((daysToMillis(14))),
    CHATBEHAVIOR(daysToMillis(14)),
    TROLLING(daysToMillis(7)),
    TEAMING(daysToMillis(7)),
    BANBYPASS(-1L),
    SKIN(daysToMillis(14)),
    NAME(daysToMillis(30)),
    EXTREME((-1L)),
    ADVERTISEMENT(daysToMillis(14)),
    RANKUTILIZATION(daysToMillis(30));

    private final Long duration;

    private static Long daysToMillis(int days) {
        return (1000L * 60 * 60 * 24 * days);
    }

    @Override
    public Long getDuration(ProxiedPlayer player) {
        return duration;
    }
}
