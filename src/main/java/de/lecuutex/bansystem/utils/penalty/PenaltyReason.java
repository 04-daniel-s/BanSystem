package de.lecuutex.bansystem.utils.penalty;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A class created by yi.dnl - 12.11.2022 / 01:20
 */

@AllArgsConstructor
@Getter
public enum PenaltyReason {
    HACKING(1),
    BEHAVIOR(2),
    CHATBEHAVIOR(3),
    TROLLING(4),
    TEAMING(5),
    BANBYPASS(6),
    SKIN(7),
    NAME(8),
    EXTREME(9),
    ADVERTISEMENT(10),
    RANKUTILIZATION(11),
    HAUSVERBOT(12);

    private final int id;
}
