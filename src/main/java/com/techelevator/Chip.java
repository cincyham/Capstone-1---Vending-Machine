package com.techelevator;

import java.math.BigDecimal;

public class Chip extends Item {


    public Chip(String itemSlot, String name, BigDecimal price, String type, int quantity) {
        super(itemSlot, name, price, type, quantity);
    }

    @Override
    public String getMessage() {
        return "Crunch Crunch, Yum!";
    }

}
