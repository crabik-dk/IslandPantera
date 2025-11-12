package com.javarush.island.kostromin.entity.organisms.plant;


public class Grass extends Plant {
    public Grass() {
        super(1, 1, 200);
    }

    @Override
    public String getEmoji() { return "🌿"; }
}