package com.javarush.island.kostromin.entity.organisms.animal.herbivore;

import com.javarush.island.kostromin.entity.organisms.Organism;

public class Horse extends Herbivore{
    public Horse() {
        super(400, 400, 20, 60, 4);
    }

    @Override
    public String getEmoji() {
        return "🐴";
    }

    @Override
    public Organism createOffspring() {
        return new Horse();
    }
}
