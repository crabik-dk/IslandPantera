package com.javarush.island.kostromin.entity.organisms.animal.herbivore;

import com.javarush.island.kostromin.entity.map.Location;
import com.javarush.island.kostromin.entity.organisms.Organism;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Mouse extends Herbivore {
    public Mouse() {
        super(0.05, 0.05, 500, 0.01, 1);
    }

    @Override
    public String getEmoji() {
        return "🐁";
    }
    @Override
    public Organism createOffspring() {
        return new Mouse();
    }
    @Override
    public void move() {
        if (!isAlive || currentLocation == null) {
            return;
        }
        Location newLocation = currentLocation.getRandomNeighbor();
        if (newLocation != null && newLocation.canAddOrganism(this)) {
            currentLocation.removeOrganism(this);
            newLocation.addOrganism(this);
            currentLocation = newLocation;
        }
    }

    @Override
    public void eat() {
        if (!isAlive || currentLocation == null) {
            return;
        }
        List<Organism> potentialFood = currentLocation.getOrganisms().stream()
                .filter(org -> org != this && canEat(org) && org.isAlive())
                .toList();
        boolean hasEaten = false;
        for (Organism food : potentialFood) {
            double probability = getEatingProbability(food.getClass());
            if (ThreadLocalRandom.current().nextDouble() < probability) {
                currentLocation.removeOrganism(food);
                weight = Math.min(maxWeight, weight + food.getWeight());
                hasEaten = true;
                checkWeight();
                break;
            }
        }
        if (!hasEaten) {
            loseWeight();
        }
    }
}