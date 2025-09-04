package com.matcherrecon.service;

import java.util.*;

public class ReconEngine {

    public static class MatchResult {
        public Map<String, String> paywayTrx;
        public boolean isMatched;

        public MatchResult(Map<String, String> paywayTrx, boolean isMatched) {
            this.paywayTrx = paywayTrx;
            this.isMatched = isMatched;
        }
    }

    public List<MatchResult> matchTransactions(List<Map<String, String>> payway,
                                               List<Map<String, String>> npsb) {
        List<MatchResult> results = new ArrayList<>();

        Set<String> npsbIds = new HashSet<>();
        for (Map<String, String> row : npsb) {
            npsbIds.add(row.get("TransactionID"));
        }

        for (Map<String, String> row : payway) {
            boolean matched = npsbIds.contains(row.get("TransactionID"));
            results.add(new MatchResult(row, matched));
        }

        return results;
    }
}
