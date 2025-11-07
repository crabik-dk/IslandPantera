package com.javarush.island.kostromin.entity.organisms.animal.herbivore;

public class Boar extends Herbivore {

    public Boar(String name, double weight, int maxPerCell, int speed, double foodNeeded) {
        super("Boar", 400, 50, 2, 50);
    }

    @Override
    public String toString() {
        return "🐗";
    }
}
