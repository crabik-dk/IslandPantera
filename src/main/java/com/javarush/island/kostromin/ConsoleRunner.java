package com.javarush.island.kostromin;

import com.javarush.island.kostromin.config.SimulationConfig;
import com.javarush.island.kostromin.entity.map.Island;

/**
 * To add a new organism, you need to:
 * 1) Inherit it: from Animal for animals, from Plant for plants.
 * 2) Override the methods if necessary.
 * 3) Add information about food preferences to the yaml file and
 * 4) Add the organism to Statistics class, initializeStatMaps method.
 * to the standard implementation in the class FoodProbabilityConfig.
 * 5) If you want a new animal to randomly appear at the edge of the map,
 * then fill out the enum BorderSpawnConfig.
 * */

public class ConsoleRunner {
    public static void main(String[] args) {
        SimulationConfig config = new SimulationConfig();
        Island island = new Island(config);
        island.initialize();
        island.startSimulation();
    }
}
