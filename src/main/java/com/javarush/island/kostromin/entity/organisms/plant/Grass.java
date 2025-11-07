package com.javarush.island.kostromin.entity.organisms.plant;

import com.javarush.island.kostromin.entity.organisms.Organism;

public class Grass extends Organism {
    public Grass() {
        this.weight = 1.0;
    }

    @Override
    public String toString() {
        return "🌿";
    }
}
