package com.techelevator;

import java.math.BigDecimal;

public class Candy extends Item {


    public Candy(String itemSlot, String name, BigDecimal price, String type, int quantity) {
        super(itemSlot, name, price, type, quantity);
    }

    @Override
    public String getMessage() {
        return "Munch Munch, Yum!";
    }


}
