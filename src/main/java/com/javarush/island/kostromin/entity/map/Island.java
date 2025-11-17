package com.javarush.island.kostromin.entity.map;

import com.javarush.island.kostromin.config.BorderSpawnConfig;
import com.javarush.island.kostromin.config.SimulationConfig;
import com.javarush.island.kostromin.constants.SimulationConstants;
import com.javarush.island.kostromin.entity.organisms.Organism;
import com.javarush.island.kostromin.entity.organisms.animal.Animal;
import com.javarush.island.kostromin.entity.organisms.plant.Grass;
import com.javarush.island.kostromin.entity.organisms.plant.Mushroom;
import com.javarush.island.kostromin.entity.organisms.plant.Plant;
import com.javarush.island.kostromin.statistics.Statistics;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * This class describes the main activities of organisms:
 * movement, food, reproduction, growth.
 * The initial organisms also appear here.
 * */
public class Island {
    private final Location[][] locations;
    private final int width, height;
    private final SimulationConfig config;
    private final ScheduledExecutorService scheduler;
    private final Statistics statistics;
    private volatile boolean running = false;
    private final AtomicInteger currentTick = new AtomicInteger(0);

    public Island(SimulationConfig config) {
        this.width = SimulationConfig.WIDTH;
        this.height = SimulationConfig.HEIGHT;
        this.config = config;
        this.locations = new Location[width][height];
        this.scheduler = Executors.newScheduledThreadPool(8);
        this.statistics = new Statistics(this);
        initializeLocations();
    }

    private void initializeLocations() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                locations[x][y] = new Location(x, y, this);
            }
        }
    }

    public void initialize() {
        System.out.println(SimulationConstants.INITIALIZING_ISLAND_WITH_ANIMALS_AND_PLANTS);
        config.INITIAL_ANIMAL_COUNTS.forEach((animalClass, count) -> {
            for (int i = 0; i < count; i++) {
                try {
                    Animal animal = animalClass.getDeclaredConstructor().newInstance();
                    placeOrganismRandomly(animal);
                } catch (Exception e) {
                    System.err.println(SimulationConstants.ERROR_CREATING_ANIMAL + animalClass.getSimpleName());
                }
            }
        });
        config.INITIAL_PLANT_COUNTS.forEach((plantClass, count) -> {
            for (int i = 0; i < count; i++) {
                try {
                    Plant plant = plantClass.getDeclaredConstructor().newInstance();
                    placeOrganismRandomly(plant);
                } catch (Exception e) {
                    System.err.println(SimulationConstants.ERROR_CREATING_PLANT + plantClass.getSimpleName());
                }
            }
        });

        System.out.println(SimulationConstants.ISLAND_INITIALIZATION_COMPLETED);
    }

    private void placeOrganismRandomly(Organism organism) {
        Random random = ThreadLocalRandom.current();
        int attempts = 0;
        while (attempts < SimulationConstants.MAX_ATTEMPTS) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            Location location = locations[x][y];

            if (location.addOrganism(organism)) {
                if (organism instanceof Animal animal) {
                    animal.setLocation(location);
                }
                return;
            }
            attempts++;
        }
        System.err.println(SimulationConstants.COULD_NOT_PLACE_ORGANISM_AFTER + SimulationConstants.MAX_ATTEMPTS + SimulationConstants.ATTEMPTS + ": " + organism.getClass().getSimpleName());
    }
    public void startSimulation() {
        running = true;
        System.out.println(SimulationConstants.STARTING_ISLAND_SIMULATION);
        scheduler.scheduleAtFixedRate(this::processTick, 0, config.TICK_DURATION_MS, TimeUnit.MILLISECONDS);
//        scheduler.scheduleWithFixedDelay(this::processTick,0,config.TICK_DURATION_MS,TimeUnit.MILLISECONDS); //
    }
    private void processTick() {
        if (!running) {
            return;
        }
        int tick = currentTick.incrementAndGet();
        System.out.println("\n=== Tick " + tick + " ===");
        try {
            moveAllAnimals();
            feedAllAnimals();
            reproduceAllAnimals();
            growAllPlants();
            randomPlantGrowth();
            spawnAnimalsAtBorder();
            printStatistics();
            checkStopCondition();
        } catch (Exception e) {
            System.err.println(SimulationConstants.ERROR_IN_TICK + tick + ": " + e.getMessage());
        }
    }

    private void moveAllAnimals() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Location location = locations[x][y];
                List<Animal> animals = location.getOrganisms().stream()
                        .filter(org -> org instanceof Animal && org.isAlive())
                        .map(org -> (Animal) org)
                        .toList();
                for (Animal animal : animals) {
                    try {
                        animal.move();
                    } catch (Exception e) {
                        System.err.println(SimulationConstants.ERROR_MOVING_ANIMAL + e.getMessage());
                    }
                }
            }
        }
    }

    private void feedAllAnimals() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Location location = locations[x][y];
                List<Animal> animals = location.getOrganisms().stream()
                        .filter(org -> org instanceof Animal && org.isAlive())
                        .map(org -> (Animal) org)
                        .toList();
                for (Animal animal : animals) {
                    try {
                        animal.eat();
                    } catch (Exception e) {
                        System.err.println(SimulationConstants.ERROR_FEEDING_ANIMAL+ e.getMessage());
                    }
                }
            }
        }
    }
    private void reproduceAllAnimals() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Location location = locations[x][y];
                List<Animal> animals = location.getOrganisms().stream()
                        .filter(org -> org instanceof Animal && org.isAlive())
                        .map(org -> (Animal) org)
                        .toList();
                Map<Class<? extends Animal>, List<Animal>> animalsByType = new HashMap<>();
                for (Animal animal : animals) {
                    animalsByType.computeIfAbsent(animal.getClass(), k -> new ArrayList<>()).add(animal);
                }
                for (List<Animal> animalGroup : animalsByType.values()) {
                    if (animalGroup.size() >= SimulationConstants.MIN_GROUP_SIZE) {
                        Animal representative = animalGroup.get(ThreadLocalRandom.current().nextInt(animalGroup.size()));
                        try {
                            representative.reproduce();
                        } catch (Exception e) {
                            System.err.println(SimulationConstants.ERROR_REPRODUCING_ANIMALS + e.getMessage());
                        }
                    }
                }
            }
        }
    }
    private void growAllPlants() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Location location = locations[x][y];
                List<Plant> plants = location.getOrganisms().stream()
                        .filter(org -> org instanceof Plant && org.isAlive())
                        .map(org -> (Plant) org)
                        .toList();
                for (Plant plant : plants) {
                    try {
                        plant.grow();
                        plant.reproduce();
                    } catch (Exception e) {
                        System.err.println(SimulationConstants.ERROR_GROWING_PLANT + e.getMessage());
                    }
                }
            }
        }
    }
    private void randomPlantGrowth() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Location location = locations[x][y];
                if (ThreadLocalRandom.current().nextDouble() < SimulationConstants.RANDOM_PLANT_GROWTH_PROBABILITY) {
                    Plant newPlant = ThreadLocalRandom.current().nextDouble() < SimulationConstants.GRASS_SPAWN_PROBABILITY ? new Grass() : new Mushroom();
                    if (location.canAddOrganism(newPlant)) {
                        location.addOrganism(newPlant);
                        newPlant.setLocation(location);
                    }
                }
            }
        }
    }

    private void spawnAnimalsAtBorder() {
        int totalSpawned = 0;
        for (BorderSpawnConfig spawnConfig : BorderSpawnConfig.values()) {
            if (ThreadLocalRandom.current().nextDouble() < spawnConfig.getSpawnProbability()) {
                boolean spawned = trySpawnAnimalAtBorder(spawnConfig.getAnimalClass());
                if (spawned) {
                    totalSpawned++;
                    System.out.println(spawnConfig.getEmoji() + SimulationConstants.SPAWNED_NEW +
                            spawnConfig.getAnimalClass().getSimpleName() + SimulationConstants.AT_BORDER);
                }
            }
        }
        if (totalSpawned > 0) {
            System.out.println(SimulationConstants.TOTAL_ANIMALS_SPAWNED_AT_BORDER + totalSpawned);
        }
    }

    /**
     * Tries to place an animal of the specified type at the edge of the map
     * return true if the animal has been successfully placed, false otherwise
     */
    private boolean trySpawnAnimalAtBorder(Class<? extends Animal> animalClass) {
        try {
            Animal animal = animalClass.getDeclaredConstructor().newInstance();
            for (int attempt = 0; attempt < SimulationConstants.MAX_BORDER_SPAWN_ATTEMPTS; attempt++) {
                Location borderLocation = getRandomBorderLocation();
                if (borderLocation != null && borderLocation.canAddOrganism(animal)) {
                    borderLocation.addOrganism(animal);
                    animal.setLocation(borderLocation);
                    return true;
                }
            }
            System.out.println(SimulationConstants.FAILED_TO_SPAWN + animalClass.getSimpleName() + " at border after " +
                    SimulationConstants.MAX_BORDER_SPAWN_ATTEMPTS + SimulationConstants.ATTEMPTS);
            return false;
        } catch (Exception e) {
            System.err.println(SimulationConstants.ERROR_CREATING_ANIMAL_INSTANCE_FOR_BORDER_SPAWN +
                    animalClass.getSimpleName() + " - " + e.getMessage());
            return false;
        }
    }
    private Location getRandomBorderLocation() {
        int side = ThreadLocalRandom.current().nextInt(SimulationConstants.BORDERS_COUNT);
        int x, y;
        switch (side) {
            case 0: // top border y = 0
                x = ThreadLocalRandom.current().nextInt(width);
                y = 0;
                break;
            case 1: // low border
                x = ThreadLocalRandom.current().nextInt(width);
                y = height - 1;
                break;
            case 2: // left border
                x = 0;
                y = ThreadLocalRandom.current().nextInt(height);
                break;
            case 3: // right border
                x = width - 1;
                y = ThreadLocalRandom.current().nextInt(height);
                break;
            default:
                return null;
        }
        return getLocation(x, y);
    }
    private void printStatistics() {
        statistics.printStatistics(currentTick.get());
    }

    private void checkStopCondition() {
        if (currentTick.get() >= config.MAX_TICKS) {
            System.out.println(SimulationConstants.SIMULATION_STOPPED_REACHED_MAXIMUM_TICKS + config.MAX_TICKS);
            stopSimulation();
            return;
        }
        boolean hasAnimals = false;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Location location = locations[x][y];
                boolean locationHasAnimals = location.getOrganisms().stream()
                        .anyMatch(org -> org instanceof Animal && org.isAlive());
                if (locationHasAnimals) {
                    hasAnimals = true;
                    break;
                }
            }
            if (hasAnimals) break;
        }

        if (!hasAnimals) {
            System.out.println(SimulationConstants.SIMULATION_STOPPED_NO_ANIMALS_LEFT);
            stopSimulation();
        }
    }

    public void stopSimulation() {
        running = false;
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(3, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println(SimulationConstants.SIMULATION_FINISHED);
    }

    public Location getLocation(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return locations[x][y];
        }
        return null;
    }

    public Location[][] getLocations() {
        return locations;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}