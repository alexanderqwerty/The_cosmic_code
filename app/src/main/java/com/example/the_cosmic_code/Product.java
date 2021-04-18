package com.example.the_cosmic_code;

import java.io.Serializable;

public class Product implements Comparable<Product>, Serializable {
    private String name;
    private Spaceship spaceship;
    private int mass;
    private int cost;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    private int id;
    private double rat;

    public double getRat() {
        return rat;
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
        this.rat = cost * 1. / mass;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;

    }

    public Product(Spaceship spaceship, String name, int cost, int mass, int id) {
        this.name = name;
        this.mass = mass;
        this.cost = cost;
        this.id = id;
        this.rat = cost * 1. / mass;
        this.spaceship = spaceship;
    }

    public Product(Spaceship spaceship, String name, int cost, int mass) {
        this.mass = mass;
        this.cost = cost;
        this.name = name;
        this.rat = cost * 1. / mass;
        this.spaceship = spaceship;
    }

    public Spaceship getSpaceship() {
        return spaceship;
    }

    public void setSpaceship(Spaceship spaceship) {
        this.spaceship = spaceship;
    }

    @Override
    public int compareTo(Product o) {
        if (o.getRat() - this.getRat() == 0)
            return this.getMass() - o.getMass();
        return (int) ((o.rat - this.rat) * 10);
    }


}
