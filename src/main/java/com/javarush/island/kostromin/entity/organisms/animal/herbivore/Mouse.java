package com.javarush.island.kostromin.entity.organisms.animal.herbivore;

public class Mouse extends Herbivore{
    public Mouse(String name, double weight, int maxPerCell, int speed, double foodNeeded) {
        super("Mouse", 0.05, 500, 1, 0.01);
    }

    @Override
    public String toString() {
        return "🐁";
    }
}
