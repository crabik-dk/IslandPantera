package com.javarush.island.kostromin.entity.organisms.animal.herbivore;

import com.javarush.island.kostromin.constants.SimulationConstants;
import com.javarush.island.kostromin.entity.organisms.Organism;

import java.util.concurrent.ThreadLocalRandom;

public class Duck extends Herbivore {
    public Duck() {
        super(1, 1, 200, 0.15, 4);
    }

    @Override
    public String getEmoji() {
        return "🦆";
    }
    @Override
    public Organism createOffspring() {
        return new Duck();
    }
    @Override
    protected boolean shouldInterruptMovement() {
        return ThreadLocalRandom.current().nextDouble() < SimulationConstants.DUCK_INTERRUPT_MOVE_PROBABILITY;
    }
}