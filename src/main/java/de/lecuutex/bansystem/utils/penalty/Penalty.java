package de.lecuutex.bansystem.utils.penalty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * A class created by yi.dnl - 19.11.2022 / 10:03
 */

@AllArgsConstructor
@Getter
@Setter
public class Penalty {
    private PenaltyReason reason;

    private String date;

    private String until;

    private String by;
}
