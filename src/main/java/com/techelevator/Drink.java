package com.techelevator;

import java.math.BigDecimal;

public class Drink extends Item{

    public Drink(String itemSlot, String name, BigDecimal price, String type, int quantity) {
        super(itemSlot, name, price, type, quantity);
    }

    @Override
    public String getMessage() {
        return "Glug Glug, Yum!";
    }
}
