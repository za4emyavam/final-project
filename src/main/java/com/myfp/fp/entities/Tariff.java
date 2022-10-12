package com.myfp.fp.entities;

public class Tariff extends Entity {
    private String name;
    private String description;
    private int cost;
    private int frequencyOfPayment;
    private Service type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getFrequencyOfPayment() {
        return frequencyOfPayment;
    }

    public void setFrequencyOfPayment(int frequencyOfPayment) {
        this.frequencyOfPayment = frequencyOfPayment;
    }

    public Service getType() {
        return type;
    }

    public void setType(Service type) {
        this.type = type;
    }
}
