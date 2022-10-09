package com.techelevator;

import java.math.BigDecimal;

public abstract class Item {

    private String itemSlot;
    private String name;
    private BigDecimal price;
    private String type;
    private int quantity;


    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public String getItemSlot() {
        return itemSlot;
    }

    public int getQuantity() {
        return quantity;
    }

    public Item(String itemSlot, String name, BigDecimal price, String type, int quantity) {
        this.itemSlot = itemSlot;
        this.name = name;
        this.price = price;
        this.type = type;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("%s|%s|%s|%s|Amount available: %s", itemSlot, name, price, type, quantity);
    }

    public abstract String getMessage();


    public void minusQuantity(){
        this.quantity -= 1;
    }


}
