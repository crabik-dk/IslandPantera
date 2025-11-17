package com.javarush.island.kostromin.config;

import com.javarush.island.kostromin.entity.organisms.animal.Animal;
import com.javarush.island.kostromin.entity.organisms.animal.herbivore.*;
import com.javarush.island.kostromin.entity.organisms.animal.predator.*;

/**
 * This enum describes the probability of new animals
 * appearing at the edge of the map.
 * */
public enum BorderSpawnConfig {
    //Predators:
    WOLF(Wolf.class, 0.03, "🐺"),
    BOA(Boa.class, 0.05, "🐍"),
    FOX(Fox.class, 0.01, "🦊"),
    BEAR(Bear.class, 0.005, "🐻"),
    EAGLE(Eagle.class, 0.08, "🦅"),
    //Herbivores:
    HORSE(Horse.class, 0.005, "🐴"),
    DEER(Deer.class, 0.05, "🦌"),
    RABBIT(Rabbit.class, 0.02, "🐇"),
    MOUSE(Mouse.class, 0.01, "🐁"),
    GOAT(Goat.class, 0.008, "🐐"),
    SHEEP(Sheep.class, 0.008, "🐑"),
    BOAR(Boar.class, 0.05, "🐗"),
    BUFFALO(Buffalo.class, 0.001, "🐃"),
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