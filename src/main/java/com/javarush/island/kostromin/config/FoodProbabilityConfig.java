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
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



/**
 * Here we create probabilities of being eaten.
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
        CLASS_MAPPING.put("Wolf", Wolf.class);
        CLASS_MAPPING.put("Boa", Boa.class);
        CLASS_MAPPING.put("Mouse", Mouse.class);
        CLASS_MAPPING.put("Duck", Duck.class);
        CLASS_MAPPING.put("Caterpillar", Caterpillar.class);
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
                System.err.println("❌ YAML config file not found: " + CONFIG_FILE + ", using default probabilities");
                loadDefaultProbabilities();
                return;
            }
            Map<String, Object> data = yaml.load(inputStream);
            Map<String, Map<String, Integer>> foodMap = (Map<String, Map<String, Integer>>) data.get("foodMap");
            if (foodMap == null) {
                System.err.println("❌ No 'foodMap' found in YAML, using default probabilities");
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
            System.out.println("✅ Food probabilities loaded successfully from " + CONFIG_FILE);
        } catch (Exception e) {
            System.err.println("❌ Error loading YAML config: " + e.getMessage());
            loadDefaultProbabilities();
        }
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