package com.javarush.island.kostromin.statistics;

import com.javarush.island.kostromin.entity.map.Island;
import com.javarush.island.kostromin.entity.map.Location;
import com.javarush.island.kostromin.entity.organisms.Organism;
import com.javarush.island.kostromin.entity.organisms.animal.herbivore.Caterpillar;
import com.javarush.island.kostromin.entity.organisms.animal.herbivore.Duck;
import com.javarush.island.kostromin.entity.organisms.animal.herbivore.Mouse;
import com.javarush.island.kostromin.entity.organisms.animal.predator.Boa;
import com.javarush.island.kostromin.entity.organisms.animal.predator.Wolf;
import com.javarush.island.kostromin.entity.organisms.plant.Grass;
import com.javarush.island.kostromin.entity.organisms.plant.Mushroom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Statistics {
    private final Island island;

    public Statistics(Island island) {
        this.island = island;
    }

    public void printStatistics(int tick) {
        System.out.println("\n=== Tick " + tick + " ===");
        printIslandMap();
        printDetailedStatistics();
    }

    private void printIslandMap() {
        Location[][] locations = island.getLocations();
        int width = island.getWidth();
        int height = island.getHeight();

        System.out.println("Island Map (showing heaviest organism in each cell):");
        System.out.println("Legend: 🐺=Wolf 🐍=Boa 🐁=Mouse 🦆=Duck 🐛=Caterpillar 🌿=Grass 🍄=Mushroom 🔳=empty");
        String emptyEmoji = "🔳";

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Location location = locations[x][y];
                Organism heaviest = location.getHeaviestOrganism();

                if (heaviest != null) {
                    System.out.print("|" + heaviest.getEmoji() );
                } else {
                    System.out.print("|" + emptyEmoji );
                }
            }
            System.out.println("|");
        }
    }

    private void printDetailedStatistics() {
        Map<Class<? extends Organism>, AtomicInteger> counts = new HashMap<>();
        Map<Class<? extends Organism>, Double> totalWeights = new HashMap<>();

        initializeStatMaps(counts, totalWeights);

        for (int x = 0; x < island.getWidth(); x++) {
            for (int y = 0; y < island.getHeight(); y++) {
                Location location = island.getLocation(x, y);
                List<Organism> organisms = location.getOrganisms();

                for (Organism org : organisms) {
                    Class<? extends Organism> orgClass = org.getClass();
                    counts.get(orgClass).incrementAndGet();
                    totalWeights.put(orgClass, totalWeights.get(orgClass) + org.getWeight());
                }
            }
        }

        System.out.println("\nDetailed Statistics:");
        System.out.println("====================");

        counts.entrySet().stream()
                .filter(entry -> entry.getValue().get() > 0)
                .sorted((a, b) -> Integer.compare(b.getValue().get(), a.getValue().get()))
                .forEach(entry -> {
                    Class<? extends Organism> orgClass = entry.getKey();
                    int count = entry.getValue().get();
                    double totalWeight = totalWeights.get(orgClass);
                    double avgWeight = totalWeight / count;
                    String emoji = getEmojiForClass(orgClass);
                    String name = orgClass.getSimpleName();

                    System.out.printf("%s %s: Count=%d, Avg Weight=%.3f, Total Weight=%.3f%n",
                            emoji, name, count, avgWeight, totalWeight);
                });
    }

    private void initializeStatMaps(
            Map<Class<? extends Organism>, AtomicInteger> counts,
            Map<Class<? extends Organism>, Double> totalWeights) {

        Class<?>[] organismClasses = {
                Wolf.class, Boa.class, Mouse.class, Duck.class, Caterpillar.class,
                Grass.class, Mushroom.class
        };

        for (Class<?> orgClass : organismClasses) {
            @SuppressWarnings("unchecked")
            Class<? extends Organism> typedClass = (Class<? extends Organism>) orgClass;
            counts.put(typedClass, new AtomicInteger(0));
            totalWeights.put(typedClass, 0.0);
        }
    }

    private String getEmojiForClass(Class<? extends Organism> orgClass) {
        try {
            Organism instance = orgClass.getDeclaredConstructor().newInstance();
            return instance.getEmoji();
        } catch (Exception e) {
            return "?";
        }
    }
}