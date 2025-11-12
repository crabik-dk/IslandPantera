package com.javarush.island.kostromin.entity.organisms.animal.herbivore;

import com.javarush.island.kostromin.entity.map.Location;

import java.util.concurrent.ThreadLocalRandom;

public class Caterpillar extends Herbivore {
    public Caterpillar() {
        super(0.01, 0.01, 1000, 0);
    }
    @Override
    public String getEmoji() {
        return "🐛";
    }
    @Override
    public void move() {
        if (ThreadLocalRandom.current().nextDouble() < 0.05 && currentLocation != null) {
            Location newLocation = currentLocation.getRandomNeighbor();
            if (newLocation != null && newLocation.canAddOrganism(this)) {
                currentLocation.removeOrganism(this);
                newLocation.addOrganism(this);
                currentLocation = newLocation;
            }
        }
    }
}