package com.example.the_cosmic_code;

import java.io.Serializable;


public class Spaceship implements Serializable {
    private int maxMass;
    private String name;

    public Spaceship() {
    }

    public Spaceship(int maxMass, String name) {
        this.maxMass = maxMass;
        this.name = name;
    }

    public int getMaxMass() {
        return maxMass;
    }

    public void setMaxMass(int maxMass) {
        this.maxMass = maxMass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
