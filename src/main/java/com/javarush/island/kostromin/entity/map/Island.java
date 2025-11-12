package com.javarush.island.kostromin.entity.map;

import com.javarush.island.kostromin.config.SimulationConfig;
import com.javarush.island.kostromin.entity.organisms.Organism;
import com.javarush.island.kostromin.entity.organisms.animal.Animal;
import com.javarush.island.kostromin.entity.organisms.plant.Plant;
import com.javarush.island.kostromin.statistics.Statistics;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Island {
    private final Location[][] locations;
    private final int width, height;
    private final SimulationConfig config;
    private final ScheduledExecutorService scheduler;
    private final Statistics statistics;
    private volatile boolean running = false;
    private AtomicInteger currentTick = new AtomicInteger(0);

    public Island(int width, int height, SimulationConfig config) {
        this.width = SimulationConfig.WIDTH;
        this.height = SimulationConfig.HEIGHT;
        this.config = config;
        this.locations = new Location[width][height];
        this.scheduler = Executors.newScheduledThreadPool(4);
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
        System.out.println("🏝️ Initializing island with animals and plants.");

        config.INITIAL_ANIMAL_COUNTS.forEach((animalClass, count) -> {
            for (int i = 0; i < count; i++) {
                try {
                    Animal animal = animalClass.getDeclaredConstructor().newInstance();
                    placeOrganismRandomly(animal);
                } catch (Exception e) {
                    System.err.println("Error creating animal: " + animalClass.getSimpleName());
                    e.printStackTrace();
                }
            }
        });
        config.INITIAL_PLANT_COUNTS.forEach((plantClass, count) -> {
            for (int i = 0; i < count; i++) {
                try {
                    Plant plant = plantClass.getDeclaredConstructor().newInstance();
                    placeOrganismRandomly(plant);
                } catch (Exception e) {
                    System.err.println("Error creating plant: " + plantClass.getSimpleName());
                    e.printStackTrace();
                }
            }
        });

        System.out.println("✅ Island initialization completed");
    }
    private void placeOrganismRandomly(Organism organism) {
        Random random = ThreadLocalRandom.current();
        int attempts = 0;
        while (attempts < 100) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            Location location = locations[x][y];

            if (location.addOrganism(organism)) {
                return;
            }
            attempts++;
        }
    }
    public void startSimulation() {
        running = true;
        System.out.println("▶️ Starting island simulation...");
        // Animal's movement
        scheduler.scheduleAtFixedRate(this::processAnimals, 0, config.TICK_DURATION_MS, TimeUnit.MILLISECONDS);
        // Statistic task
        scheduler.scheduleAtFixedRate(this::printStatistics, 500, config.TICK_DURATION_MS, TimeUnit.MILLISECONDS);
        // Interruption task
        scheduler.scheduleAtFixedRate(this::checkStopCondition, 1000, config.TICK_DURATION_MS, TimeUnit.MILLISECONDS);
    }


    private void processAnimals() {
        if (!running) return;
        currentTick.incrementAndGet();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Location location = locations[x][y];
                List<Animal> animals = location.getOrganisms().stream()
                        .filter(org -> org instanceof Animal)
                        .map(org -> (Animal) org)
                        .toList();
                for (Animal animal : animals) {
                    try {
                        animal.move();
                    } catch (Exception e) {
                        System.err.println("❌ Error moving animal: " + e.getMessage());
                    }
                }
            }
        }
    }

    private void printStatistics() {
        if (!running) return;
        statistics.printStatistics(currentTick.get());
    }

    private void checkStopCondition() {
        if (currentTick.get() >= config.MAX_TICKS) {
            System.out.println("Simulation stopped: reached maximum ticks (" + config.MAX_TICKS + ")");
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
        System.out.println("Simulation finished!");
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
    public boolean isRunning() {
        return running;
    }
}