package com.javarush.island.kostromin.entity.organisms.animal.herbivore;

import com.javarush.island.kostromin.entity.organisms.Organism;

public class Boar extends Herbivore{
    public Boar() {
        super(400, 400, 50, 50, 2);
    }

    @Override
    public String getEmoji() {
        return "🐗";
    }

    @Override
    public Organism createOffspring() {
        return new Boar();
    }
}
