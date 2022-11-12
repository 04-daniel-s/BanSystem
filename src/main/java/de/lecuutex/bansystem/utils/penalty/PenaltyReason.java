package de.lecuutex.bansystem.utils.penalty;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * A class created by yi.dnl - 12.11.2022 / 01:20
 */

@AllArgsConstructor
@Getter
public enum PenaltyReason {
    CHATBEHAVIOR(1,5,4),
    BEHAVIOR(2,5,4),
    ADVERTISEMENT(3,3,4),
    TROLLING(4,2,2),
    TEAMING(5,2,2),
    SKIN(6,7,4),
    NAME(7,7,4),
    RANKUTILIZATION(8,5,10),
    HACKING(9,5,0),
    BANBYPASS(10,20,0),
    EXTREME(11,30,0);

    private final int id;

    private final int banPoints;

    private final int warnPoints;

    public static PenaltyReason getReasonById(int id) {
        return Arrays.stream(values()).filter(p -> p.getId() == id).findFirst().get();
    }
}
