package com.example.vvyas1_foodbook;

import java.util.Date;

public class Food {
    private String description;
    private int count;
    private int unitCost;
    private String BBD;
    private String location;


    Food(String description, int count, int unitCost, String BBD, String location)
    {
        this.description = description;
        this.count = count;
        this.unitCost = unitCost;
        this.BBD = BBD;
        this.location = location;
    }


    public int getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(int unitCost) {
        this.unitCost = unitCost;
    }

    public String getBBD() {
        return BBD;
    }

    public void setBBD(String BBD) {
        this.BBD = BBD;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
