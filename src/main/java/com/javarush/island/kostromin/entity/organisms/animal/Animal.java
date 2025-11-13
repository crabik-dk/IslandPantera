package com.javarush.island.kostromin.entity.organisms.animal;

import com.javarush.island.kostromin.config.FoodProbabilityConfig;
import com.javarush.island.kostromin.entity.map.Location;
import com.javarush.island.kostromin.entity.organisms.Organism;


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
        weight -= maxWeight * 0.1;
        if (weight <= 0) {
            die();
            if (currentLocation != null) {
                currentLocation.removeOrganism(this);
            }
        }
    }
    public abstract void move();

    public abstract void eat();
}