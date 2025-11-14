package com.javarush.island.kostromin.entity.organisms.animal.herbivore;

import com.javarush.island.kostromin.entity.map.Location;
import com.javarush.island.kostromin.entity.organisms.Organism;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Duck extends Herbivore {
    public Duck() {
        super(1, 1, 200, 0.15, 4);
    }

    @Override
    public String getEmoji() {
        return "🦆";
    }
    @Override
    public Organism createOffspring() {
        return new Duck();
    }
    @Override
    public void move() {
        if (!isAlive || currentLocation == null) {
            return;
        }
        int moves = ThreadLocalRandom.current().nextInt(maxSpeed) + 1;
        for (int i = 0; i < moves; i++) {
            Location newLocation = currentLocation.getRandomNeighbor();
            if (newLocation != null && newLocation.canAddOrganism(this)) {
                currentLocation.removeOrganism(this);
                newLocation.addOrganism(this);
                currentLocation = newLocation;
                if (ThreadLocalRandom.current().nextDouble() < 0.5) break;
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