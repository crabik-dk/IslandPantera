package com.javarush.island.kostromin.entity.organisms;

public abstract class Organism {
    protected double weight;
    protected final double maxWeight;
    protected final int maxPerLocation;

    public Organism(double weight, double maxWeight, int maxPerLocation) {
        this.weight = weight;
        this.maxWeight = maxWeight;
        this.maxPerLocation = maxPerLocation;
    }

    public double getWeight() {
        return weight;
    }

    public int getMaxPerLocation() {
        return maxPerLocation;
    }

    public abstract String getEmoji();

    public boolean isAlive() {
        return true;
    }
}