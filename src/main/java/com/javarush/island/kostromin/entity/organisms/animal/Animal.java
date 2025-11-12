package com.javarush.island.kostromin.entity.organisms.animal;

import com.javarush.island.kostromin.entity.map.Location;
import com.javarush.island.kostromin.entity.organisms.Organism;


public abstract class Animal extends Organism {
    protected final int maxSpeed;
    protected Location currentLocation;

    public Animal(double weight, double maxWeight, int maxPerLocation, int maxSpeed) {
        super(weight, maxWeight, maxPerLocation);
        this.maxSpeed = maxSpeed;
    }

    public void setLocation(Location location) {
        this.currentLocation = location;
    }

    public abstract void move();
}