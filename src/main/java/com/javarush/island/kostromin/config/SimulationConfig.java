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

public class SimulationConfig {
    public final int TICK_DURATION_MS = 1000;
    public final int MAX_TICKS = 1000;
    public final static int WIDTH = 20;
    public final static int HEIGHT = 20;

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
