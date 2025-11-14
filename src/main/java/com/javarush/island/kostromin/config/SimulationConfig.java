package com.javarush.island.kostromin.config;


import com.javarush.island.kostromin.entity.organisms.animal.Animal;
import com.javarush.island.kostromin.entity.organisms.animal.herbivore.Caterpillar;
import com.javarush.island.kostromin.entity.organisms.animal.herbivore.Duck;
import com.javarush.island.kostromin.entity.organisms.animal.herbivore.Mouse;
import com.javarush.island.kostromin.entity.organisms.animal.predator.Boa;
import com.javarush.island.kostromin.entity.organisms.animal.predator.Wolf;
import com.javarush.island.kostromin.entity.organisms.plant.Grass;
import com.javarush.island.kostromin.entity.organisms.plant.Mushroom;
import com.javarush.island.kostromin.entity.organisms.plant.Plant;

import java.util.Map;

/**
 * This is where the initial values for the organisms on the island are set.
 * The size of the island, the time of one tick, and the maximum number of ticks.
 * */

public class SimulationConfig {
    public final int TICK_DURATION_MS = 50;
    public final int MAX_TICKS = 1000;
    public final static int WIDTH = 15;
    public final static int HEIGHT = 15;


    public final Map<Class<? extends Animal>, Integer> INITIAL_ANIMAL_COUNTS = Map.of(
            Wolf.class, 10,
            Boa.class, 15,
            Mouse.class, 50,
            Duck.class, 30,
            Caterpillar.class, 100
    );
    public final Map<Class<? extends Plant>, Integer> INITIAL_PLANT_COUNTS = Map.of(
            Grass.class, 200,
            Mushroom.class, 100
    );
}
