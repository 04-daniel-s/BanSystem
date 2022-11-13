package de.lecuutex.bansystem.utils.penalty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;

import java.util.Arrays;

/**
 * A class created by yi.dnl - 12.11.2022 / 01:20
 */

@AllArgsConstructor
@Getter
public enum PenaltyReason {
    CHATBEHAVIOR(1, 5, 4, "Chat behavior"),
    BEHAVIOR(2, 5, 4, "Behavior"),
    ADVERTISEMENT(3, 3, 4, "Advertisement"),
    TROLLING(4, 2, 2, "Trolling"),
    TEAMING(5, 2, 2, "Teaming"),
    SKIN(6, 7, 4, "Skin"),
    NAME(7, 7, 4, "Name"),
    RANKUTILIZATION(8, 5, 10, "Rank utilization"),
    HACKING(9, 5, 0, "Hacking"),
    BANBYPASS(10, 20, 0, "Ban bypass"),
    EXTREME(11, 30, 0, "Extreme");

    private final int id;

    private final int banPoints;

    private final int warnPoints;

    private final String reason;

    public static PenaltyReason getReasonById(int id) {
        return Arrays.stream(values()).filter(p -> p.getId() == id).findFirst().get();
    }
}
