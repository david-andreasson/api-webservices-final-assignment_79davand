package com.davanddev.uppgift_4.controller;

import com.davanddev.uppgift_4.model.MatchResult;
import com.davanddev.uppgift_4.service.MatchResultService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MatchResultController {

    private final MatchResultService service;

    public MatchResultController(MatchResultService service) {
        this.service = service;
    }

    @QueryMapping
    public List<MatchResult> getMatchResults() {
        return service.getAll();
    }

    @MutationMapping
    public MatchResult createMatchResult(@Argument String teamA,
                                         @Argument String teamB,
                                         @Argument int scoreA,
                                         @Argument int scoreB) {
        return service.create(teamA, teamB, scoreA, scoreB);
    }

    @MutationMapping
    public MatchResult updateMatchResult(@Argument String matchId,
                                         @Argument String teamA,
                                         @Argument String teamB,
                                         @Argument Integer scoreA,
                                         @Argument Integer scoreB) {
        return service.update(matchId, teamA, teamB, scoreA, scoreB);
    }

    @MutationMapping
    public boolean deleteMatchResult(@Argument String matchId) {
        return service.delete(matchId);
    }
}
