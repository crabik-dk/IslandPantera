package com.javarush.island.kostromin.entity.organisms.animal.predator;

import com.javarush.island.kostromin.entity.organisms.Organism;

public class Eagle extends Predator {
    public Eagle() {
        super(6, 6, 20, 1, 3);
    }

    @Override
    public String getEmoji() {
        return "🦅";
    }

    @Override
    public Organism createOffspring() {
        return new Eagle();
    }
}
