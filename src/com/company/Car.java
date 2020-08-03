package com.company;

import java.util.Random;

public class Car{

    //creating variables
    private int distance = 0;
    private int speed = 20;

    //creating setters
    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    //creating getters
    public int getDistance() {
        return distance;
    }

    public int getSpeed() {
        return speed;
    }

    //creating method to change distance
    public int changeDistance() {
        distance = distance - speed;
        return distance;
    }
}
