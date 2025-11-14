package com.javarush.island.kostromin.entity.organisms.plant;


import com.javarush.island.kostromin.entity.organisms.Organism;

public class Grass extends Plant {
    public Grass() {
        super(1, 1, 200);
    }

    @Override
    public String getEmoji() {
        return "🌿";
    }
    @Override
    public Organism createOffspring() {
        return new Grass();
    }
}