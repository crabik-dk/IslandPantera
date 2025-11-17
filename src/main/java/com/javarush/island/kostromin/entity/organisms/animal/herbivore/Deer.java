package com.javarush.island.kostromin.entity.organisms.animal.herbivore;

import com.javarush.island.kostromin.entity.organisms.Organism;

public class Deer extends Herbivore{
    public Deer() {
        super(300, 300, 20, 50, 4);
    }

    @Override
    public String getEmoji() {
        return "🦌";
    }

    @Override
    public Organism createOffspring() {
        return new Deer();
    }
}
