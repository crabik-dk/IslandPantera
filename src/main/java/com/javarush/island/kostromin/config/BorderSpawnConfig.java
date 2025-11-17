package com.javarush.island.kostromin.config;

import com.javarush.island.kostromin.entity.organisms.animal.Animal;
import com.javarush.island.kostromin.entity.organisms.animal.herbivore.Caterpillar;
import com.javarush.island.kostromin.entity.organisms.animal.herbivore.Duck;
import com.javarush.island.kostromin.entity.organisms.animal.herbivore.Mouse;
import com.javarush.island.kostromin.entity.organisms.animal.predator.Boa;
import com.javarush.island.kostromin.entity.organisms.animal.predator.Wolf;

public enum BorderSpawnConfig {
    WOLF(Wolf.class, 0.05, "🐺"),
    BOA(Boa.class, 0.05, "🐍"),
    MOUSE(Mouse.class, 0.01, "🐁"),
    DUCK(Duck.class, 0.01, "🦆"),
    CATERPILLAR(Caterpillar.class, 0.01, "🐛");

    private final Class<? extends Animal> animalClass;
    private final double spawnProbability;
    private final String emoji;

    BorderSpawnConfig(Class<? extends Animal> animalClass, double spawnProbability, String emoji) {
        this.animalClass = animalClass;
        this.spawnProbability = spawnProbability;
        this.emoji = emoji;
    }

    public Class<? extends Animal> getAnimalClass() {
        return animalClass;
    }

    public double getSpawnProbability() {
        return spawnProbability;
    }

    public String getEmoji() {
        return emoji;
    }
}