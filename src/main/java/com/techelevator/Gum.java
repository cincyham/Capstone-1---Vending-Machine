package com.techelevator;

import java.math.BigDecimal;

public class Gum extends Item{


    public Gum(String itemSlot, String name, BigDecimal price, String type, int quantity) {
        super(itemSlot, name, price, type, quantity);
    }

    @Override
    public String getMessage() {
        return "Chew Chew, Yum!";
    }
}
