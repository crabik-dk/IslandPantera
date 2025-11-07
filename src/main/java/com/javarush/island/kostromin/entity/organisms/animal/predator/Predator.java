package com.javarush.island.kostromin.entity.organisms.animal.predator;

import com.javarush.island.kostromin.entity.organisms.animal.Animal;

public abstract class Predator extends Animal {

    public Predator(String name, double weight, int maxPerCell, int speed, double foodNeeded) {
        super(name, weight, maxPerCell, speed, foodNeeded);
    }
}
