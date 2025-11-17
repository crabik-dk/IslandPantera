package com.javarush.island.kostromin.entity.organisms.animal.herbivore;

import com.javarush.island.kostromin.entity.map.Location;
import com.javarush.island.kostromin.entity.organisms.Organism;

public class Mouse extends Herbivore {
    public Mouse() {
        super(0.05, 0.05, 500, 0.01, 1);
    }

    @Override
    public String getEmoji() {
        return "🐁";
    }
    @Override
    public Organism createOffspring() {
        return new Mouse();
    }
    @Override
    public void move() {
        if (!isAlive || currentLocation == null) {
            return;
        }
        Location newLocation = currentLocation.getRandomNeighbor();
        if (newLocation != null && newLocation.canAddOrganism(this)) {
            currentLocation.removeOrganism(this);
            newLocation.addOrganism(this);
            currentLocation = newLocation;
        }
    }
}