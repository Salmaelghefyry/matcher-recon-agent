package com.matcherrecon.agent;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import com.matcherrecon.service.ReconEngine;
import com.matcherrecon.utils.FileLoader;

import java.util.List;
import java.util.Map;


public class MatcherAgent extends Agent {

    protected void setup(){
        System.out.println("MatcherAgent started");
        addBehaviour(new CyclicBehaviour(this){
            public void action(){
                // 1. Load files
                List<Map<String,String>> payway = FileLoader.loadExcel("data/trx-payway.xlsx");
                List<Map<String,String>> npsb = FileLoader.loadExcel("data/npsb_transactions.xlsx");

                // 2. Match transactions
                ReconEngine engine = new ReconEngine();
                List<ReconEngine.MatchResult> results = engine.matchTransactions(payway, npsb);

                // 3. Send anomalies to AnomalyAgent
                for(ReconEngine.MatchResult r : results){
                    if(!r.isMatched){
                        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                        AID anomalyAgent = new AID("AnomalyAgent", AID.ISLOCALNAME);
                        msg.addReceiver(anomalyAgent);
                        msg.setContent(r.paywayTrx.toString());
                        send(msg);
                    }
                }

                // 4. Save matched transactions (can be implemented later)
                block(10000); // run once per batch
            }
        });
    }
}
