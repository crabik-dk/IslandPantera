package com.javarush.island.kostromin.entity.organisms.plant;

import com.javarush.island.kostromin.entity.map.Location;
import com.javarush.island.kostromin.entity.organisms.Organism;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Plant extends Organism {
    public Plant(double weight, double maxWeight, int maxPerLocation) {
        super(weight, maxWeight, maxPerLocation);
    }
    protected Location currentLocation;

    public void setLocation(Location location) {
        this.currentLocation = location;
    }
    public void grow() {
        if (!isAlive) {
            return;
        }
        if (ThreadLocalRandom.current().nextDouble() < 0.5) {
            weight = Math.min(maxWeight, weight * 1.2);
        }
    }
    public void reproduce() {
        if (!isAlive || currentLocation == null) {
            return;
        }
        if (ThreadLocalRandom.current().nextDouble() < 0.5) {
            long sameTypeCount = currentLocation.getOrganisms().stream()
                    .filter(org -> org.getClass() == this.getClass() && org.isAlive())
                    .count();
            if (sameTypeCount < maxPerLocation) {
                Plant newPlant = (Plant) createOffspring();
                newPlant.checkWeight();
                if (currentLocation.canAddOrganism(newPlant)) {
                    currentLocation.addOrganism(newPlant);
                }
            }
        }
    }
}