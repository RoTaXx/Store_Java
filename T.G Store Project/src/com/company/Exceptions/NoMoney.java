package com.company.Exceptions;

import com.company.Product;
import java.math.BigDecimal;
import java.util.Map;

public class NoMoney extends Exception{
    private BigDecimal clientMoney;
    private Map<Product, Integer> markedProduct;

    public NoMoney(BigDecimal clientMoney, Map<Product, Integer> markedProduct) {
        this.clientMoney = clientMoney;
        this.markedProduct = markedProduct;
    }

    @Override
    public String toString() {
        return "NoMoney{" +
                "clientMoney=" + clientMoney +
                ", markedProduct=" + markedProduct +
                '}';
    }
}
