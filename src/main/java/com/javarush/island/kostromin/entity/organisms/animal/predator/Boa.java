package com.javarush.island.kostromin.entity.organisms.animal.predator;

import com.javarush.island.kostromin.entity.map.Location;
import com.javarush.island.kostromin.entity.organisms.Organism;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Boa extends Predator {
    public Boa() {
        super(15, 15, 30, 3, 1);
    }
    @Override
    public String getEmoji() {
        return "🐍";
    }
    @Override
    public Organism createOffspring() {
        return new Boa();
    }
    @Override
    public void move() {
        if (!isAlive || currentLocation == null) {
            return;
        }
        if (ThreadLocalRandom.current().nextDouble() < 0.3) {
            Location newLocation = currentLocation.getRandomNeighbor();
            if (newLocation != null && newLocation.canAddOrganism(this)) {
                currentLocation.removeOrganism(this);
                newLocation.addOrganism(this);
                currentLocation = newLocation;
            }
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