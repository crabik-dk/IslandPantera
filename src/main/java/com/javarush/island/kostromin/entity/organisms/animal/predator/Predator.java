package com.javarush.island.kostromin.entity.organisms.animal.predator;

import com.javarush.island.kostromin.entity.organisms.animal.Animal;

public abstract class Predator extends Animal {
    public Predator(double weight, double maxWeight, int maxPerLocation, int maxSpeed) {
        super(weight, maxWeight, maxPerLocation, maxSpeed);
    }
}
