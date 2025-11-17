package com.javarush.island.kostromin.entity.organisms.animal.herbivore;

import com.javarush.island.kostromin.entity.organisms.Organism;

public class Buffalo extends Herbivore {
    public Buffalo() {
        super(700, 700, 10, 100, 3);
    }

    @Override
    public String getEmoji() {
        return "🐃";
    }

    @Override
    public Organism createOffspring() {
        return new Buffalo();
    }
}
