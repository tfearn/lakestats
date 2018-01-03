package com.toddfearn.lakestats.model;

public class LakeData {
    private LakeName name;
    public LakeName getName() { return name; }

    private String location;
    public String getLocation() { return location; }

    private double temperature;
    public double getTemperature() { return temperature; }

    private double level;
    public double getLevel() { return level; }

    public LakeData(LakeName name, String location, double temperature, double level) {
        this.name = name;
        this.location = location;
        this.temperature = temperature;
        this.level = level;
    }
}
