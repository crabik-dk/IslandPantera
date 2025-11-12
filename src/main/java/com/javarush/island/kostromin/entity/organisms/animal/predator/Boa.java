package com.javarush.island.kostromin.entity.organisms.animal.predator;

import com.javarush.island.kostromin.entity.map.Location;
import java.util.concurrent.ThreadLocalRandom;

public class Boa extends Predator {
    public Boa() {
        super(15, 15, 30, 1);
    }

    @Override
    public String getEmoji() { return "🐍"; }

    @Override
    public void move() {
        if (currentLocation == null) return;

        if (ThreadLocalRandom.current().nextDouble() < 0.3) {
            Location newLocation = currentLocation.getRandomNeighbor();
            if (newLocation != null && newLocation.canAddOrganism(this)) {
                currentLocation.removeOrganism(this);
                newLocation.addOrganism(this);
                currentLocation = newLocation;
            }
        }
    }
}