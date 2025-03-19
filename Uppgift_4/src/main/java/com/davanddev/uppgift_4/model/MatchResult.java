package com.davanddev.uppgift_4.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchResult {
    private String matchId;
    private String teamA;
    private String teamB;
    private int scoreA;
    private int scoreB;
}
