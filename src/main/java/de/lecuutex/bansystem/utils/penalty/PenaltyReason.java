package de.lecuutex.bansystem.utils.penalty;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A class created by yi.dnl - 12.11.2022 / 01:20
 */

@AllArgsConstructor
@Getter
public enum PenaltyReason {
    HACKING(1,5,0),
    BEHAVIOR(2,5,4),
    CHATBEHAVIOR(3,5,4),
    TROLLING(4,2,2),
    TEAMING(5,2,2),
    BANBYPASS(6,20,0),
    SKIN(7,7,4),
    NAME(8,7,4),
    EXTREME(9,30,0),
    ADVERTISEMENT(10,3,4),
    RANKUTILIZATION(11,5,10);

    private final int id;

    private final int banPoints;

    private final int warnPoints;
}
