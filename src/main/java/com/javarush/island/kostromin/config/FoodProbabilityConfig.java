package com.javarush.island.kostromin.config;

import com.javarush.island.kostromin.constants.SimulationConstants;
import com.javarush.island.kostromin.entity.organisms.Organism;
import com.javarush.island.kostromin.entity.organisms.animal.Animal;
import com.javarush.island.kostromin.entity.organisms.animal.herbivore.*;
import com.javarush.island.kostromin.entity.organisms.animal.predator.*;
import com.javarush.island.kostromin.entity.organisms.plant.Grass;
import com.javarush.island.kostromin.entity.organisms.plant.Mushroom;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A class for loading and managing the probabilities of eating from a YAML file.
 * Uses static initialization to load automatically when the application starts.
 */
public class FoodProbabilityConfig {
    public static final String CONFIG_FILE = "kostromin/food_probabilities.yaml";
    private static final Map<Class<? extends Animal>, Map<Class<? extends Organism>, Double>> FOOD_PROBABILITIES = new ConcurrentHashMap<>();
    private static final Map<String, Class<? extends Organism>> CLASS_MAPPING = new ConcurrentHashMap<>();

    static {
        initializeClassMapping();
        loadProbabilitiesFromYaml();
    }

    private static void initializeClassMapping() {
        //Predators:
        CLASS_MAPPING.put("Wolf", Wolf.class);
        CLASS_MAPPING.put("Boa", Boa.class);
        CLASS_MAPPING.put("Fox", Fox.class);
        CLASS_MAPPING.put("Bear", Bear.class);
        CLASS_MAPPING.put("Eagle", Eagle.class);
        //Herbivores:
        CLASS_MAPPING.put("Horse", Horse.class);
        CLASS_MAPPING.put("Deer", Deer.class);
        CLASS_MAPPING.put("Rabbit", Rabbit.class);
        CLASS_MAPPING.put("Mouse", Mouse.class);
        CLASS_MAPPING.put("Goat", Goat.class);
        CLASS_MAPPING.put("Sheep", Sheep.class);
        CLASS_MAPPING.put("Boar", Boar.class);
        CLASS_MAPPING.put("Buffalo", Buffalo.class);
        CLASS_MAPPING.put("Duck", Duck.class);
        CLASS_MAPPING.put("Caterpillar", Caterpillar.class);
        //Plants:
        CLASS_MAPPING.put("Grass", Grass.class);
        CLASS_MAPPING.put("Mushroom", Mushroom.class);
    }

    @SuppressWarnings("unchecked")
    private static void loadProbabilitiesFromYaml() {
        try {
            Yaml yaml = new Yaml();
            InputStream inputStream = FoodProbabilityConfig.class
                    .getClassLoader()
                    .getResourceAsStream(CONFIG_FILE);
            if (inputStream == null) {
                System.err.println(SimulationConstants.YAML_CONFIG_FILE_NOT_FOUND + CONFIG_FILE + SimulationConstants.USING_DEFAULT_PROBABILITIES);
                loadDefaultProbabilities();
                return;
            }
            Map<String, Object> data = yaml.load(inputStream);
            Map<String, Map<String, Integer>> foodMap = (Map<String, Map<String, Integer>>) data.get("foodMap");
            if (foodMap == null) {
                System.err.println(SimulationConstants.NO_FOODMAP_FOUND_IN_YAML);
                loadDefaultProbabilities();
                return;
            }
            for (Map.Entry<String, Map<String, Integer>> predatorEntry : foodMap.entrySet()) {
                String predatorName = predatorEntry.getKey();
                Class<? extends Organism> predatorClass = CLASS_MAPPING.get(predatorName);
                if (predatorClass == null || !Animal.class.isAssignableFrom(predatorClass)) {
                    continue;
                }
                Map<Class<? extends Organism>, Double> preyMap = new HashMap<>();
                Map<String, Integer> preyProbabilities = predatorEntry.getValue();
                for (Map.Entry<String, Integer> preyEntry : preyProbabilities.entrySet()) {
                    String preyName = preyEntry.getKey();
                    Class<? extends Organism> preyClass = CLASS_MAPPING.get(preyName);
                    if (preyClass != null) {
                        double probability = preyEntry.getValue() / 100.0;
                        preyMap.put(preyClass, probability);
                    }
                }
                FOOD_PROBABILITIES.put((Class<? extends Animal>) predatorClass, preyMap);
            }
            inputStream.close();
            System.out.println(SimulationConstants.FOOD_PROBABILITIES_LOADED_SUCCESSFULLY_FROM + CONFIG_FILE);
        } catch (Exception e) {
            System.err.println(SimulationConstants.ERROR_LOADING_YAML_CONFIG + e.getMessage());
            loadDefaultProbabilities();
        }
    }
    /**
     * If the yaml file does not load for some reason,
     * then you can use the standard HashMap selection
     * of the probability of eating.
     * The name of the variable is an animal that eats.
     * */
    private static void loadDefaultProbabilities() {
        Map<Class<? extends Organism>, Double> wolfMap = new HashMap<>();
        wolfMap.put(Horse.class, 0.10);    // 10%
        wolfMap.put(Deer.class, 0.15);     // 15%
        wolfMap.put(Rabbit.class, 0.60);   // 60%
        wolfMap.put(Mouse.class, 0.80);    // 80%
        wolfMap.put(Goat.class, 0.60);     // 60%
        wolfMap.put(Sheep.class, 0.70);    // 70%
        wolfMap.put(Boar.class, 0.15);     // 15%
        wolfMap.put(Buffalo.class, 0.10);  // 10%
        wolfMap.put(Duck.class, 0.40);     // 40%
        FOOD_PROBABILITIES.put(Wolf.class, wolfMap);

        Map<Class<? extends Organism>, Double> boaMap = new HashMap<>();
        boaMap.put(Fox.class, 0.15);       // 15%
        boaMap.put(Rabbit.class, 0.20);    // 20%
        boaMap.put(Mouse.class, 0.40);     // 40%
        boaMap.put(Duck.class, 0.10);      // 10%
        FOOD_PROBABILITIES.put(Boa.class, boaMap);

        Map<Class<? extends Organism>, Double> foxMap = new HashMap<>();
        foxMap.put(Rabbit.class, 0.70);    // 70%
        foxMap.put(Mouse.class, 0.90);     // 90%
        foxMap.put(Duck.class, 0.60);      // 60%
        foxMap.put(Caterpillar.class, 0.40); // 40%
        foxMap.put(Mushroom.class, 0.20);
        FOOD_PROBABILITIES.put(Fox.class, foxMap);

        Map<Class<? extends Organism>, Double> bearMap = new HashMap<>();
        bearMap.put(Boa.class, 0.80);      // 80%
        bearMap.put(Horse.class, 0.40);    // 40%
        bearMap.put(Deer.class, 0.80);     // 80%
        bearMap.put(Rabbit.class, 0.80);   // 80%
        bearMap.put(Mouse.class, 0.90);    // 90%
        bearMap.put(Goat.class, 0.70);     // 70%
        bearMap.put(Sheep.class, 0.70);    // 70%
        bearMap.put(Boar.class, 0.50);     // 50%
        bearMap.put(Buffalo.class, 0.20);  // 20%
        bearMap.put(Duck.class, 0.10);     // 10%
        bearMap.put(Grass.class, 1.00);    // 40%
        bearMap.put(Mushroom.class, 1.00); // 30%
        FOOD_PROBABILITIES.put(Bear.class, bearMap);

        Map<Class<? extends Organism>, Double> eagleMap = new HashMap<>();
        eagleMap.put(Fox.class, 0.10);     // 10%
        eagleMap.put(Rabbit.class, 0.90);  // 90%
        eagleMap.put(Mouse.class, 0.90);   // 90%
        eagleMap.put(Duck.class, 0.80);    // 80%
        FOOD_PROBABILITIES.put(Eagle.class, eagleMap);

        Map<Class<? extends Organism>, Double> horseMap = new HashMap<>();
        horseMap.put(Grass.class, 1.00);    // 100%
        horseMap.put(Mushroom.class, 1.00); // 100%
        FOOD_PROBABILITIES.put(Horse.class, horseMap);

        Map<Class<? extends Organism>, Double> deerMap = new HashMap<>();
        deerMap.put(Grass.class, 1.00);    // 100%
        deerMap.put(Mushroom.class, 1.00); // 100%
        FOOD_PROBABILITIES.put(Deer.class, deerMap);

        Map<Class<? extends Organism>, Double> rabbitMap = new HashMap<>();
        rabbitMap.put(Grass.class, 1.00);    // 100%
        rabbitMap.put(Mushroom.class, 1.00); // 100%
        FOOD_PROBABILITIES.put(Rabbit.class, rabbitMap);

        Map<Class<? extends Organism>, Double> mouseMap = new HashMap<>();
        mouseMap.put(Caterpillar.class, 0.90); // 90%
        mouseMap.put(Grass.class, 1.00);      // 100%
        mouseMap.put(Mushroom.class, 1.00);   // 100%
        FOOD_PROBABILITIES.put(Mouse.class, mouseMap);

        Map<Class<? extends Organism>, Double> goatMap = new HashMap<>();
        goatMap.put(Grass.class, 1.00);    // 100%
        goatMap.put(Mushroom.class, 1.00); // 100%
        FOOD_PROBABILITIES.put(Goat.class, goatMap);

        Map<Class<? extends Organism>, Double> sheepMap = new HashMap<>();
        sheepMap.put(Grass.class, 1.00);    // 100%
        sheepMap.put(Mushroom.class, 1.00); // 100%
        FOOD_PROBABILITIES.put(Sheep.class, sheepMap);

        Map<Class<? extends Organism>, Double> boarMap = new HashMap<>();
        boarMap.put(Mouse.class, 0.50);     // 50%
        boarMap.put(Caterpillar.class, 0.90); // 90%
        boarMap.put(Grass.class, 1.00);     // 100%
        boarMap.put(Mushroom.class, 1.00);  // 100%
        FOOD_PROBABILITIES.put(Boar.class, boarMap);

        Map<Class<? extends Organism>, Double> buffaloMap = new HashMap<>();
        buffaloMap.put(Grass.class, 1.00);    // 100%
        buffaloMap.put(Mushroom.class, 1.00); // 100%
        FOOD_PROBABILITIES.put(Buffalo.class, buffaloMap);

        Map<Class<? extends Organism>, Double> duckMap = new HashMap<>();
        duckMap.put(Caterpillar.class, 0.90); // 90%
        duckMap.put(Grass.class, 1.00);      // 100%
        duckMap.put(Mushroom.class, 1.00);   // 100%
        FOOD_PROBABILITIES.put(Duck.class, duckMap);

        Map<Class<? extends Organism>, Double> caterpillarMap = new HashMap<>();
        caterpillarMap.put(Grass.class, 1.00);    // 100%
        caterpillarMap.put(Mushroom.class, 1.00); // 100%
        FOOD_PROBABILITIES.put(Caterpillar.class, caterpillarMap);
    }

    public static double getEatingProbability(Class<? extends Animal> predator, Class<? extends Organism> prey) {
        Map<Class<? extends Organism>, Double> preyMap = FOOD_PROBABILITIES.get(predator);
        if (preyMap == null) {
            return 0.0;
        }
        Double probability = preyMap.get(prey);
        return probability != null ? probability : 0.0;
    }

    public static boolean canEat(Class<? extends Animal> predator, Class<? extends Organism> prey) {
        return getEatingProbability(predator, prey) > 0.0;
    }
}