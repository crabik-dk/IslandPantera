package com.javarush.island.kostromin.config;

import com.javarush.island.kostromin.entity.organisms.Organism;
import com.javarush.island.kostromin.entity.organisms.animal.Animal;
import com.javarush.island.kostromin.entity.organisms.animal.herbivore.Caterpillar;
import com.javarush.island.kostromin.entity.organisms.animal.herbivore.Duck;
import com.javarush.island.kostromin.entity.organisms.animal.herbivore.Mouse;
import com.javarush.island.kostromin.entity.organisms.animal.predator.Boa;
import com.javarush.island.kostromin.entity.organisms.animal.predator.Wolf;
import com.javarush.island.kostromin.entity.organisms.plant.Grass;
import com.javarush.island.kostromin.entity.organisms.plant.Mushroom;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



/**
 * Here we create probabilities of being eaten.
 * Uses static initialization to load automatically when the application starts.
 */
public class FoodProbabilityConfig {
    private static final Map<Class<? extends Animal>, Map<Class<? extends Organism>, Double>> FOOD_PROBABILITIES = new ConcurrentHashMap<>();

    static {
        loadDefaultProbabilities();
    }


    private static void loadDefaultProbabilities() {
        Map<Class<? extends Organism>, Double> wolfMap = new HashMap<>();
        wolfMap.put(Mouse.class, 0.8);
        wolfMap.put(Duck.class, 0.4);
        FOOD_PROBABILITIES.put(Wolf.class, wolfMap);

        Map<Class<? extends Organism>, Double> boaMap = new HashMap<>();
        boaMap.put(Mouse.class, 0.4);
        boaMap.put(Duck.class, 0.1);
        FOOD_PROBABILITIES.put(Boa.class, boaMap);

        Map<Class<? extends Organism>, Double> mouseMap = new HashMap<>();
        mouseMap.put(Caterpillar.class, 0.9);
        mouseMap.put(Grass.class, 0.7);
        mouseMap.put(Mushroom.class, 1.0);
        FOOD_PROBABILITIES.put(Mouse.class, mouseMap);

        Map<Class<? extends Organism>, Double> duckMap = new HashMap<>();
        duckMap.put(Caterpillar.class, 0.9);
        duckMap.put(Grass.class, 1.0);
        duckMap.put(Mushroom.class, 0.5);
        FOOD_PROBABILITIES.put(Duck.class, duckMap);

        Map<Class<? extends Organism>, Double> caterpillarMap = new HashMap<>();
        caterpillarMap.put(Grass.class, 1.0);
        caterpillarMap.put(Mushroom.class, 0.8);
        FOOD_PROBABILITIES.put(Caterpillar.class, caterpillarMap);
    }

    public static double getEatingProbability(Class<? extends Animal> predator, Class<? extends Organism> prey) {
        Map<Class<? extends Organism>, Double> preyMap = FOOD_PROBABILITIES.get(predator);
        if (preyMap == null) return 0.0;
        Double probability = preyMap.get(prey);
        return probability != null ? probability : 0.0;
    }

    public static boolean canEat(Class<? extends Animal> predator, Class<? extends Organism> prey) {
        return getEatingProbability(predator, prey) > 0.0;
    }
}