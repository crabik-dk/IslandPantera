package com.javarush.island.kostromin.constants;

public class SimulationConstants {
    public static final double WEIGHT_LOSS_RATE = 0.05;
    public static final double MIN_WEIGHT_FACTOR = 0.15;
    public static final double OFFSPRING_WEIGHT_FACTOR = 0.7;
    public static final double ANIMAL_RATIO_AFTER_REPRODUCTION = 1.5;
    public static final double PLANT_RATIO_AFTER_GROWING = 1.2;

    public static final double REPRODUCTION_PROBABILITY = 0.75;
    public static final double PLANT_GROWTH_PROBABILITY = 0.6;
    public static final double RANDOM_PLANT_GROWTH_PROBABILITY = 0.1;
    public static final double GRASS_SPAWN_PROBABILITY = 0.6;

    public static final double BOA_MOVE_PROBABILITY = 0.3;
    public static final double CATERPILLAR_MOVE_PROBABILITY = 0.05;
    public static final double DUCK_INTERRUPT_MOVE_PROBABILITY = 0.5;

    public static final int BORDERS_COUNT = 4;
    public static final int MIN_GROUP_SIZE = 2;
    public static final int CATERPILLAR_STARVATION_TICKS = 10;
    public static final int MAX_BORDER_SPAWN_ATTEMPTS = 100;
    public static final int MAX_ATTEMPTS = 100;

    public static final String NO_FOODMAP_FOUND_IN_YAML = "❌ No 'foodMap' found in YAML, using default probabilities";
    public static final String YAML_CONFIG_FILE_NOT_FOUND = "❌ YAML config file not found: ";
    public static final String USING_DEFAULT_PROBABILITIES = ", using default probabilities";
    public static final String FOOD_PROBABILITIES_LOADED_SUCCESSFULLY_FROM = "✅ Food probabilities loaded successfully from ";
    public static final String ERROR_LOADING_YAML_CONFIG = "❌ Error loading YAML config: ";

    public static final String INITIALIZING_ISLAND_WITH_ANIMALS_AND_PLANTS = "🏝️ Initializing island with animals and plants...";
    public static final String ISLAND_INITIALIZATION_COMPLETED = "✅ Island initialization completed...";
    public static final String COULD_NOT_PLACE_ORGANISM_AFTER = "⚠️ Could not place organism after ";
    public static final String ATTEMPTS = " attempts";
    public static final String STARTING_ISLAND_SIMULATION = "▶️ Starting island simulation...";
    public static final String ERROR_CREATING_ANIMAL = "❌ Error creating animal: ";
    public static final String ERROR_CREATING_PLANT = "❌ Error creating plant: ";
    public static final String ERROR_IN_TICK = "❌ Error in tick ";
    public static final String ERROR_MOVING_ANIMAL = "❌ Error moving animal: ";
    public static final String ERROR_FEEDING_ANIMAL = "❌ Error moving animal: ";
    public static final String ERROR_REPRODUCING_ANIMALS = "❌ Error reproducing animals: ";
    public static final String ERROR_GROWING_PLANT = "❌ Error growing plant: ";
    public static final String ERROR_CREATING_ANIMAL_INSTANCE_FOR_BORDER_SPAWN = "❌ Error creating animal instance for border spawn: ";
    public static final String TOTAL_ANIMALS_SPAWNED_AT_BORDER = "📍 Total animals spawned at border: ";
    public static final String SPAWNED_NEW = " Spawned new ";
    public static final String AT_BORDER = " at border";
    public static final String FAILED_TO_SPAWN = "⚠️ Failed to spawn ";
    public static final String SIMULATION_STOPPED_REACHED_MAXIMUM_TICKS = "⏹️ Simulation stopped: reached maximum ticks: ";
    public static final String SIMULATION_STOPPED_NO_ANIMALS_LEFT = "⏹️ Simulation stopped: no animals left";
    public static final String SIMULATION_FINISHED = "✅ Simulation finished.";

    public static final String ISLAND_MAP_SHOWING_HEAVIEST_ORGANISM_IN_EACH_CELL = "Island Map (showing heaviest organism in each cell):";
    public static final String ANIMALS_LEGEND = "Legend: 🐺=Wolf 🐍=Boa 🦊=Fox 🐻=Bear 🦅=Eagle 🐛=Caterpillar 🌿=Grass 🍄=Mushroom " +
            "🐁=Mouse 🦆=Duck 🐴=Horse 🦌=Deer 🐇=Rabbit 🐐=Goat 🐑=Sheep 🐗=Boar 🐃=Buffalo ❌=Empty";
    public static final String DETAILED_STATISTICS = "\n📊 Detailed Statistics:";

    private SimulationConstants() {

    }
}
