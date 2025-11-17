package com.javarush.island.kostromin.entity.organisms.animal.herbivore;

import com.javarush.island.kostromin.entity.organisms.Organism;

public class Rabbit extends Herbivore{
    public Rabbit() {
        super(2, 2, 150, 0.45, 2);
    }

    @Override
    public String getEmoji() {
        return "🐇";
    }

    @Override
    public Organism createOffspring() {
        return new Rabbit();
    }
}
