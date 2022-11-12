package de.lecuutex.bansystem.utils.penalty;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A class created by yi.dnl - 12.11.2022 / 01:20
 */

@AllArgsConstructor
@Getter
public enum PenaltyReason {
    HACKING(1, PenaltyType.BAN, Collections.singletonList(-1L)),
    BEHAVIOR(2, PenaltyType.BAN, Collections.singletonList(daysToMillis(14))),
    CHATBEHAVIOR(3, PenaltyType.BAN, Collections.singletonList(daysToMillis(14))),
    TROLLING(4, PenaltyType.BAN, Collections.singletonList(daysToMillis(7))),
    TEAMING(5, PenaltyType.BAN, Collections.singletonList(daysToMillis(7))),
    BANBYPASS(6, PenaltyType.BAN, Collections.singletonList(-1L)),
    SKIN(7, PenaltyType.BAN, Collections.singletonList(daysToMillis(14))),
    NAME(8, PenaltyType.BAN, Collections.singletonList(daysToMillis(30))),
    EXTREME(9,PenaltyType.BAN,Collections.singletonList(-1L)),
    ADVERTISEMENT(10, PenaltyType.BAN, Collections.singletonList(daysToMillis(14))),
    RANKUTILIZATION(11, PenaltyType.BAN, Collections.singletonList(daysToMillis(30))),
    HAUSVERBOT(12, PenaltyType.BAN, Collections.singletonList(-1L));

    private int id;

    private PenaltyType penaltyType;

    private List<Long> duration;

    private static Long daysToMillis(int days) {
        return (1000L * 60 * 60 * 24 * days);
    }

    private static Long minutesToMillis(int days) {
        return (1000L * 60 * 60 * 24 * days);
    }
}
