package com.javarush.island.kostromin.entity.organisms.plant;


import com.javarush.island.kostromin.entity.organisms.Organism;

public class Mushroom extends Plant {
    public Mushroom() {
        super(0.5, 0.5, 100);
    }

    @Override
    public String getEmoji() {
        return "🍄";
    }
    @Override
    public Organism createOffspring() {
        return new Mushroom();
    }
}