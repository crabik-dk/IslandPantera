package com.javarush.island.kostromin.entity.organisms.plant;


public class Mushroom extends Plant {
    public Mushroom() {
        super(0.5, 0.5, 100);
    }

    @Override
    public String getEmoji() {
        return "🍄";
    }
}