package com.javarush.island.kostromin.entity.organisms.animal.herbivore;

import com.javarush.island.kostromin.entity.organisms.animal.Animal;


public abstract class Herbivore extends Animal {
    public Herbivore(double weight, double maxWeight, int maxPerLocation, int maxSpeed) {
        super(weight, maxWeight, maxPerLocation, maxSpeed);
    }
}