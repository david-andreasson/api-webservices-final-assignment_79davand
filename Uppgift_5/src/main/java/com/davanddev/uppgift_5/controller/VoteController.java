package com.davanddev.uppgift_5.controller;

import com.davanddev.uppgift_5.service.VoteService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
public class VoteController {
    private final VoteService voteService;
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }
    @PostMapping("/vote")
    public Map<String, String> vote(@RequestBody Map<String, String> payload) {
        String contestant = payload.get("contestant");
        voteService.addVote(contestant);
        return Map.of("message", "Vote registered");
    }
    @GetMapping("/results")
    public Map<String, Integer> results() {
        return voteService.getResults();
    }
}
