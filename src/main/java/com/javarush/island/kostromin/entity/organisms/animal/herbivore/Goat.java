package com.javarush.island.kostromin.entity.organisms.animal.herbivore;


import com.javarush.island.kostromin.entity.organisms.Organism;

public class Goat extends Herbivore {
    public Goat() {
        super(60, 60, 140, 10, 3);
    }

    @Override
    public String getEmoji() {
        return "🐐";
    }

    @Override
    public Organism createOffspring() {
        return new Goat();
    }
}
