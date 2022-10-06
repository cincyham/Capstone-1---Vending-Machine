package com.techelevator;

import java.math.BigDecimal;

public class Item {

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

    public int getQuantity() {
        return quantity;
    }

    public Item(String name, BigDecimal price, String type, int quantity) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("|%s|%s|%s|Stock: %s", name, price, type, quantity);
    }

    public void purchaseItem(){
        if (this.type.equals("Chip")) {
            System.out.println("Crunch Crunch, Yum!");
        } else if (this.type.equals("Drink")) {
            System.out.println("Glug Glug, Yum!");
        } else if (this.type.equals("Candy")) {
            System.out.println("Munch Munch, Yum!");
        } else if (this.type.equals("Gum")){
            System.out.println("Chew Chew, Yum!");
        } else {
            System.out.println("Type not found.");
        }
        this.quantity -= 1;
    }


}
