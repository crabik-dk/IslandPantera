package com.javarush.island.kostromin.entity.organisms.animal.predator;

import com.javarush.island.kostromin.entity.organisms.Organism;

public class Bear extends Predator {
    public Bear() {
        super(500, 500, 5, 80, 2);
    }

    @Override
    public String getEmoji() {
        return "🐻";
    }

    @Override
    public Organism createOffspring() {
        return new Bear();
    }
}
