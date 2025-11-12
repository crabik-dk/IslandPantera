package com.javarush.island.kostromin.entity.map;

import com.javarush.island.kostromin.entity.organisms.Organism;
import com.javarush.island.kostromin.entity.organisms.animal.Animal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

public class Location {
    private final int x, y;
    private final List<Organism> organisms;
    private final ReentrantLock lock;
    private final Island island;

    public Location(int x, int y, Island island) {
        this.x = x;
        this.y = y;
        this.island = island;
        this.organisms = new ArrayList<>();
        this.lock = new ReentrantLock();
    }
    public void lock() {
        lock.lock();
    }
    public void unlock() {
        lock.unlock();
    }
    public boolean addOrganism(Organism organism) {
        lock();
        try {
            long count = organisms.stream()
                    .filter(org -> org.getClass() == organism.getClass())
                    .count();

            if (count < organism.getMaxPerLocation()) {
                organisms.add(organism);
                if (organism instanceof Animal animal) {
                    animal.setLocation(this);
                }
                return true;
            }
            return false;
        } finally {
            unlock();
        }
    }

    public void removeOrganism(Organism organism) {
        lock();
        try {
            organisms.remove(organism);
        } finally {
            unlock();
        }
    }

    public List<Organism> getOrganisms() {
        lock();
        try {
            return new ArrayList<>(organisms);
        } finally {
            unlock();
        }
    }
    public boolean canAddOrganism(Organism organism) {
        lock();
        try {
            long count = organisms.stream()
                    .filter(org -> org.getClass() == organism.getClass())
                    .count();
            return count < organism.getMaxPerLocation();
        } finally {
            unlock();
        }
    }
    public Location getRandomNeighbor() {
        Random random = ThreadLocalRandom.current();
        int[][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}};
        int[] dir = directions[random.nextInt(directions.length)];

        int newX = x + dir[0];
        int newY = y + dir[1];

        return island.getLocation(newX, newY);
    }

    public Organism getHeaviestOrganism() {
        lock();
        try {
            return organisms.stream()
                    .max(Comparator.comparingDouble(Organism::getWeight))
                    .orElse(null);
        } finally {
            unlock();
        }
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}