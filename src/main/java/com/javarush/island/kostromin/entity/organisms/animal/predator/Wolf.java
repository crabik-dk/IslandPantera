package com.javarush.island.kostromin.entity.organisms.animal.predator;

import com.javarush.island.kostromin.entity.organisms.Organism;


public class Wolf extends Predator {
    public Wolf() {
        super(50, 50, 30, 8, 3);
    }

    @Override
    public String getEmoji() {
        return "🐺";
    }

    @Override
    public Organism createOffspring() {
        return new Wolf();
    }
}
