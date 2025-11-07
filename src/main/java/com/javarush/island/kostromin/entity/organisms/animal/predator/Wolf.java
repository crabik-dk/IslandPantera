package com.javarush.island.kostromin.entity.organisms.animal.predator;

public class Wolf extends Predator {
    public Wolf(String name, double weight, int maxPerCell, int speed, double foodNeeded) {
        super("Wolf", 50, 30, 3, 8);
    }

    @Override
    public String toString() {
        return "🐺";
    }
}
