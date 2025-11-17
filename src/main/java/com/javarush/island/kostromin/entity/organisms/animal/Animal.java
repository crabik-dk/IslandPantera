package com.javarush.island.kostromin.entity.organisms.animal;

import com.javarush.island.kostromin.config.FoodProbabilityConfig;
import com.javarush.island.kostromin.entity.map.Location;
import com.javarush.island.kostromin.entity.organisms.Organism;
import com.javarush.island.kostromin.services.Eatable;
import com.javarush.island.kostromin.services.Movable;
import com.javarush.island.kostromin.services.Reproducible;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public abstract class Animal extends Organism implements Movable, Eatable, Reproducible {
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
                if (shouldInterruptMovement()) break;
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
                // Успешно съедаем пищу
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

    protected boolean shouldInterruptMovement() {
        return false;
    }

    protected double getMoveProbability() {
        return 1.0;
    }
}