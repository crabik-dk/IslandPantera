package com.javarush.island.kostromin.config;


import com.javarush.island.kostromin.entity.organisms.animal.Animal;
import com.javarush.island.kostromin.entity.organisms.animal.herbivore.*;
import com.javarush.island.kostromin.entity.organisms.animal.predator.*;
import com.javarush.island.kostromin.entity.organisms.plant.Grass;
import com.javarush.island.kostromin.entity.organisms.plant.Mushroom;
import com.javarush.island.kostromin.entity.organisms.plant.Plant;

import java.util.Map;

/**
 * This is where the initial values for the organisms on the island are set.
 * The size of the island, the time of one tick, and the maximum number of ticks.
 */

public class SimulationConfig {
    public final int TICK_DURATION_MS = 200;
    public final int MAX_TICKS = 20000;
    public final static int WIDTH = 30;
    public final static int HEIGHT = 30;

    public final Map<Class<? extends Animal>, Integer> INITIAL_ANIMAL_COUNTS = Map.ofEntries(
            Map.entry(Wolf.class, 20),
            Map.entry(Boa.class, 35),
            Map.entry(Fox.class, 30),
            Map.entry(Bear.class, 5),
            Map.entry(Eagle.class, 20),
            Map.entry(Horse.class, 20),
            Map.entry(Deer.class, 20),
            Map.entry(Rabbit.class, 150),
            Map.entry(Mouse.class, 100),
            Map.entry(Goat.class, 140),
            Map.entry(Sheep.class, 140),
            Map.entry(Boar.class, 50),
            Map.entry(Buffalo.class, 10),
            Map.entry(Duck.class, 50),
            Map.entry(Caterpillar.class, 1000)
    );

    public final Map<Class<? extends Plant>, Integer> INITIAL_PLANT_COUNTS = Map.of(
            Grass.class, 2000,
            Mushroom.class, 700
    );
}
