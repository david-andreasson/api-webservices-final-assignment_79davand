package com.davanddev.uppgift_4.service;

import com.davanddev.uppgift_4.model.MatchResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MatchResultService {

    private final Map<String, MatchResult> matchResults = new ConcurrentHashMap<>();

    //Seed-data till test application
    public MatchResultService() {

        String id1 = UUID.randomUUID().toString();
        matchResults.put(id1, new MatchResult(id1, "Team A", "Team B", 1, 0));

        String id2 = UUID.randomUUID().toString();
        matchResults.put(id2, new MatchResult(id2, "Team C", "Team D", 3, 2));
    }

    public List<MatchResult> getAll() {
        return new ArrayList<>(matchResults.values());
    }

    public MatchResult create(String teamA, String teamB, int scoreA, int scoreB) {
        String id = UUID.randomUUID().toString();
        MatchResult result = new MatchResult(id, teamA, teamB, scoreA, scoreB);
        matchResults.put(id, result);
        return result;
    }

    public MatchResult update(String matchId, String teamA, String teamB, Integer scoreA, Integer scoreB) {
        MatchResult result = matchResults.get(matchId);
        if (result == null) {
            throw new RuntimeException("MatchResult not found");
        }
        if (teamA != null) result.setTeamA(teamA);
        if (teamB != null) result.setTeamB(teamB);
        if (scoreA != null) result.setScoreA(scoreA);
        if (scoreB != null) result.setScoreB(scoreB);
        return result;
    }

    public boolean delete(String matchId) {
        return matchResults.remove(matchId) != null;
    }
}
