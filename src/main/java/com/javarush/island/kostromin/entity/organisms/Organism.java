package com.javarush.island.kostromin.entity.organisms;

public abstract class Organism {
    protected double weight;
    protected final double maxWeight;
    protected final int maxPerLocation;
    protected boolean isAlive = true;


    public Organism(double weight, double maxWeight, int maxPerLocation) {
        this.weight = weight;
        this.maxWeight = maxWeight;
        this.maxPerLocation = maxPerLocation;
    }

    public abstract Organism createOffspring();

    public double getWeight() {
        return weight;
    }

    public int getMaxPerLocation() {
        return maxPerLocation;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public abstract String getEmoji();

    protected void checkWeight() {
        if (isAlive && weight < maxWeight * 0.15) {
            die();
        }
    }

    public void die() {
        isAlive = false;
    }
}