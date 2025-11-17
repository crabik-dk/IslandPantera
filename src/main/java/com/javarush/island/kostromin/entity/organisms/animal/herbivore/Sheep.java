package com.javarush.island.kostromin.entity.organisms.animal.herbivore;

import com.javarush.island.kostromin.entity.organisms.Organism;

public class Sheep extends Herbivore {
    public Sheep() {
        super(70, 70, 140, 15, 3);
    }

    @Override
    public String getEmoji() {
        return "🐑";
    }

    @Override
    public Organism createOffspring() {
        return new Sheep();
    }
}
