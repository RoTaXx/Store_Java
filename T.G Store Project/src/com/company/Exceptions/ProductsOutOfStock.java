package com.company.Exceptions;

import com.company.Product;

public class ProductsOutOfStock extends Exception{
    private String name;

    public ProductsOutOfStock(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GoodsOutOfStockException{" +
                "name='" + name + '\'' +
                "} ";
    }
}
