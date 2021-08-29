package com.company.Exceptions;

public class Quantity extends Throwable {
    private String name;
    private int quantity;

    public Quantity(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Quantity{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
