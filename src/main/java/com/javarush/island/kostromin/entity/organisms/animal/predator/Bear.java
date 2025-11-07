package com.javarush.island.kostromin.entity.organisms.animal.predator;

public class Bear extends Predator {
    public Bear(String name, double weight, int maxPerCell, int speed, double foodNeeded) {
        super("Bear", 500, 5, 2, 80);
    }

    @Override
    public String toString() {
        return "🐻";
    }
}
