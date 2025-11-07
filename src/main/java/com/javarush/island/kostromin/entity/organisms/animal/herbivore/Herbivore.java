package com.javarush.island.kostromin.entity.organisms.animal.herbivore;

import com.javarush.island.kostromin.entity.organisms.animal.Animal;

public abstract class Herbivore extends Animal {
    public Herbivore(String name, double weight, int maxPerCell, int speed, double foodNeeded) {
        super(name, weight, maxPerCell, speed, foodNeeded);
    }
}
