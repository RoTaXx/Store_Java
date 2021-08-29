package com.company;

import java.io.Serializable;
import java.math.BigDecimal;

public class Cashier implements Serializable {
    private int id;
    private String name;
    private BigDecimal salary; //monthly salary
    private static int num_of_Cashiers = 0;
    private boolean isBusy;

    public Cashier(String name, BigDecimal salary) {
        this.name = name;
        this.salary = salary;
        Cashier.num_of_Cashiers++;
        this.id = Cashier.num_of_Cashiers;
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getSalary() {
        return this.salary;
    }

    @Override
    public String toString() {
        return "Cashier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
