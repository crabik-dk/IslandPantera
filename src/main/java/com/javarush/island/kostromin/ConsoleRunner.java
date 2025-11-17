package com.javarush.island.kostromin;

import com.javarush.island.kostromin.config.SimulationConfig;
import com.javarush.island.kostromin.entity.map.Island;

public class ConsoleRunner {
    public static void main(String[] args) {
        System.out.println("🚀 Starting Island Simulation...");
        SimulationConfig config = new SimulationConfig();
        Island island = new Island(config);
        island.initialize();
        island.startSimulation();
    }
}
