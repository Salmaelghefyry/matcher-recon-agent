package com.matcherrecon.service;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class Boot {

    public static void main(String[] args) {
        Runtime rt = Runtime.instance();
        ProfileImpl profile = new ProfileImpl();
        profile.setParameter(ProfileImpl.GUI, "true");

        AgentContainer mainContainer = rt.createMainContainer(profile);

        try {
            AgentController matcher = mainContainer.createNewAgent(
                    "MatcherAgent",
                    "com.matcherrecon.agent.MatcherAgent",
                    null
            );
            AgentController anomaly = mainContainer.createNewAgent(
                    "AnomalyAgent",
                    "com.matcherrecon.agent.AnomalyAgent",
                    null
            );

            matcher.start();
            anomaly.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
