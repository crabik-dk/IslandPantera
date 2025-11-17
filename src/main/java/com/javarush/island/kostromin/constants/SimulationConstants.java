package com.javarush.island.kostromin.constants;

public class SimulationConstants {
    public static final double WEIGHT_LOSS_RATE = 0.1;
    public static final double MIN_WEIGHT_FACTOR = 0.15;
    public static final double OFFSPRING_WEIGHT_FACTOR = 0.7;

    public static final double REPRODUCTION_PROBABILITY = 0.5;
    public static final double PLANT_GROWTH_PROBABILITY = 0.5;
    public static final double RANDOM_PLANT_GROWTH_PROBABILITY = 0.03;
    public static final double GRASS_SPAWN_PROBABILITY = 0.7;

    public static final double BOA_MOVE_PROBABILITY = 0.3;
    public static final double CATERPILLAR_MOVE_PROBABILITY = 0.05;
    public static final double DUCK_INTERRUPT_MOVE_PROBABILITY = 0.5;

    public static final int CATERPILLAR_STARVATION_TICKS = 10;
    public static final int MAX_BORDER_SPAWN_ATTEMPTS = 100;
    public static final int MAX_ATTEMPTS = 100;

    private SimulationConstants() {

    }
}
