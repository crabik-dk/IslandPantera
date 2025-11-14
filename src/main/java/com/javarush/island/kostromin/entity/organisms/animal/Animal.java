package com.javarush.island.kostromin.entity.organisms.animal;

import com.javarush.island.kostromin.config.FoodProbabilityConfig;
import com.javarush.island.kostromin.entity.map.Location;
import com.javarush.island.kostromin.entity.organisms.Organism;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public abstract class Animal extends Organism {
    protected final double foodNeeded;
    protected final int maxSpeed;
    protected Location currentLocation;

    public Animal(double weight, double maxWeight, int maxPerLocation, double foodNeeded, int maxSpeed) {
        super(weight, maxWeight, maxPerLocation);
        this.foodNeeded = foodNeeded;
        this.maxSpeed = maxSpeed;
    }

    public void setLocation(Location location) {
        this.currentLocation = location;
    }

    public boolean canEat(Organism organism) {
        return FoodProbabilityConfig.canEat(this.getClass(), organism.getClass());
    }

    public double getEatingProbability(Class<? extends Organism> targetClass) {
        return FoodProbabilityConfig.getEatingProbability(this.getClass(), targetClass);
    }
    protected void loseWeight() {
        if (!isAlive) {
            return;
        }
        weight -= maxWeight * 0.1;
        checkWeight();
    }

    public void reproduce() {
        if (!isAlive || currentLocation == null) {
            return;
        }
        List<Animal> sameTypeAnimals = currentLocation.getOrganisms().stream()
                .filter(org -> org.getClass() == this.getClass() && org.isAlive())
                .map(org -> (Animal) org)
                .toList();
        if (sameTypeAnimals.size() >= 2) {
            if (ThreadLocalRandom.current().nextDouble() < 0.5) {
                int currentCount = sameTypeAnimals.size();
                int newCount = (int) (currentCount * 1.5);
                if (newCount <= maxPerLocation) {
                    int animalsToAdd = newCount - currentCount;
                    for (int i = 0; i < animalsToAdd; i++) {
                        Animal offspring = (Animal) createOffspring();
                        offspring.weight = this.weight * 0.7;
                        offspring.checkWeight();
                        if (offspring.isAlive() && currentLocation.canAddOrganism(offspring)) {
                            currentLocation.addOrganism(offspring);
                            offspring.setLocation(currentLocation);
                        }
                    }
                    for (Animal animal : sameTypeAnimals) {
                        animal.weight *= 0.7;
                        animal.checkWeight();
                    }
                }
            }
        }
    }
    public abstract void move();
    public abstract void eat();
}