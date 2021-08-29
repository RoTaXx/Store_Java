package com.company.Exceptions;


public class ExpiredProduct extends Exception{
    private String name;

    public ExpiredProduct(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ExpiredProduct{" +
                "name='" + name + '\'' +
                '}';
    }
}
