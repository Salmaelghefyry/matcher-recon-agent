package com.matcherrecon.agent;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.FileWriter;
import java.io.IOException;

public class AnomalyAgent extends Agent {

    protected void setup() {
        System.out.println("AnomalyAgent started");

        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    try (FileWriter fw = new FileWriter("reports/anomalies.csv", true)) {
                        fw.write(msg.getContent());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    block();
                }
            }
        });
    }
}
