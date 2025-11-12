package com.javarush.island.kostromin.entity.organisms.plant;

import com.javarush.island.kostromin.entity.organisms.Organism;

public abstract class Plant extends Organism {
    public Plant(double weight, double maxWeight, int maxPerLocation) {
        super(weight, maxWeight, maxPerLocation);
    }
}