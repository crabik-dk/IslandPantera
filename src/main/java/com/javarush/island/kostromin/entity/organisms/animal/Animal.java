package com.javarush.island.kostromin.entity.organisms.animal;

import com.javarush.island.kostromin.entity.organisms.Organism;

import javax.xml.stream.Location;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Animal extends Organism {
    protected String name;
    protected int maxPerCell;
    protected int speed;
    protected double foodNeeded;
    protected double satiety;
    protected boolean isAlive = true;

    public Animal(String name, double weight, int maxPerCell, int speed, double foodNeeded) {
        this.name = name;
        this.weight = weight;
        this.maxPerCell = maxPerCell;
        this.speed = speed;
        this.foodNeeded = foodNeeded;
    }
}
