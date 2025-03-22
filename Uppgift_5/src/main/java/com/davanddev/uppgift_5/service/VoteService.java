package com.davanddev.uppgift_5.service;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class VoteService {
    private final Map<String, Integer> votes = new ConcurrentHashMap<>();
    public void addVote(String contestant) {
        votes.merge(contestant, 1, Integer::sum);
    }
    public Map<String, Integer> getResults() {
        return votes;
    }
}
