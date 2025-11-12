package com.javarush.island.kostromin.entity.organisms.animal.predator;

import com.javarush.island.kostromin.entity.map.Location;
import java.util.concurrent.ThreadLocalRandom;

public class Wolf extends Predator {
    public Wolf() {
        super(50, 50, 30, 3);
    }

    @Override
    public String getEmoji() { return "🐺"; }

    @Override
    public void move() {
        if (currentLocation == null) return;

        int moves = ThreadLocalRandom.current().nextInt(maxSpeed) + 1;

        for (int i = 0; i < moves; i++) {
            Location newLocation = currentLocation.getRandomNeighbor();
            if (newLocation != null && newLocation.canAddOrganism(this)) {
                currentLocation.removeOrganism(this);
                newLocation.addOrganism(this);
                currentLocation = newLocation;
            }
        }
    }
}