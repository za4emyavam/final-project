package com.myfp.fp.entities;

public class Tariff extends Entity {
    private String name;
    private String description;
    private Service service;
    private int cost;
    private int frequencyOfPayment;

    public TariffStatus getTariffStatus() {
        return tariffStatus;
    }

    public void setTariffStatus(TariffStatus tariffStatus) {
        this.tariffStatus = tariffStatus;
    }

    private TariffStatus tariffStatus;

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

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
