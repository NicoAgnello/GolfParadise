package com.mindhub.golfparadise.utils;

public class OrderPurchaseUtil {
    public static double getDeliveryCost(short zipCode) {
        if (zipCode >= 0 && zipCode < 2000) {
            return 9.90;
        } else if (zipCode >= 2000 && zipCode < 4000) {
            return 14.90;
        } else if (zipCode >= 4000 && zipCode < 6000) {
            return 19.90;
        } else if (zipCode >= 6000 && zipCode < 8000) {
            return 24.90;
        } else {
            return 29.90;
        }
    }
}
