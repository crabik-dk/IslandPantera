package com.javarush.island.kostromin.entity.organisms.animal.predator;

import com.javarush.island.kostromin.entity.organisms.Organism;

public class Fox extends Predator {
    public Fox() {
        super(8, 8, 30, 2, 2);
    }

    @Override
    public String getEmoji() {
        return "🦊";
    }

    @Override
    public Organism createOffspring() {
        return new Fox();
    }
}
