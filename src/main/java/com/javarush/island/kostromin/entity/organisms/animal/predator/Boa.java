package com.javarush.island.kostromin.entity.organisms.animal.predator;

import com.javarush.island.kostromin.constants.SimulationConstants;
import com.javarush.island.kostromin.entity.map.Location;
import com.javarush.island.kostromin.entity.organisms.Organism;

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
        if (ThreadLocalRandom.current().nextDouble() < SimulationConstants.BOA_MOVE_PROBABILITY) {
            Location newLocation = currentLocation.getRandomNeighbor();
            if (newLocation != null && newLocation.canAddOrganism(this)) {
                currentLocation.removeOrganism(this);
                newLocation.addOrganism(this);
                currentLocation = newLocation;
            }
        }
    }
}