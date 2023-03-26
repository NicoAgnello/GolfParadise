package com.mindhub.golfparadise.dtos;

public class DeliveryCostDTO {
    private double deliveryCost;

    public DeliveryCostDTO(double deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public double getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(double deliveryCost) {
        this.deliveryCost = deliveryCost;
    }
}
