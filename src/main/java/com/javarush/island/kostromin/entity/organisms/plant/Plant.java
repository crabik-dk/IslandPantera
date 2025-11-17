package com.javarush.island.kostromin.entity.organisms.plant;

import com.javarush.island.kostromin.constants.SimulationConstants;
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
        if (ThreadLocalRandom.current().nextDouble() < SimulationConstants.PLANT_GROWTH_PROBABILITY) {
            weight = Math.min(maxWeight, weight * SimulationConstants.PLANT_RATIO_AFTER_GROWING);
        }
    }

    public void reproduce() {
        if (!isAlive || currentLocation == null) {
            return;
        }
        if (ThreadLocalRandom.current().nextDouble() < SimulationConstants.PLANT_GROWTH_PROBABILITY) {
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