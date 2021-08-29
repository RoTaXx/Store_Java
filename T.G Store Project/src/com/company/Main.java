package com.company;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        Product milk = new Product("Milk", BigDecimal.valueOf(1.00), ProductType.EDIBLE, LocalDate.now().plusDays(10));
        Product butter = new Product("Butter", BigDecimal.valueOf(5.00), ProductType.EDIBLE, LocalDate.now().plusDays(10));
        Product orange_juice = new Product("Orange Juice", BigDecimal.valueOf(2.20), ProductType.EDIBLE, LocalDate.now().plusDays(10));
        Product broom = new Product("Broom", BigDecimal.valueOf(2.20), ProductType.NOT_EDIBLE, LocalDate.now().plusDays(5));

//-----------------------------------------------------------------------------------------------------------------------------------------------------------
        Map<Product, Integer> goodsList1 = new HashMap<>();
        goodsList1.put(milk, 20);
        goodsList1.put(butter, 20);
        goodsList1.put(orange_juice, 20);
        goodsList1.put(broom, 20);


        BigDecimal shop1FoodOvercharge = BigDecimal.valueOf(30);
        BigDecimal shop1NonFoodOvercharge = BigDecimal.valueOf(25);
        int shop1ExpirationDaysBeforeReduction = 5;
        BigDecimal shop1ReductionPercentage = BigDecimal.valueOf(20);

        Shop shop1 = new Shop("~~~ Kaufland ~~~",shop1FoodOvercharge, shop1NonFoodOvercharge, shop1ExpirationDaysBeforeReduction, shop1ReductionPercentage);
        System.out.println();
        System.out.println("            Welcome to "+shop1.getId());

        shop1.deliverGoods(goodsList1);

        Cashier cashier1Shop1 = new Cashier("Franko Goretzka", BigDecimal.valueOf(2000));
        shop1.hireCashier(cashier1Shop1);

        Client client1 = new Client(BigDecimal.valueOf(12000), shop1);
        Map<Product, Integer> client1GoodsList = new HashMap<>();
        client1GoodsList.put(milk, 4);
        client1GoodsList.put(butter, 2);
        client1GoodsList.put(orange_juice, 1);

        client1.setGoodsList(client1GoodsList);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Receipt receipt = new Receipt(cashier1Shop1, now, client1GoodsList, shop1FoodOvercharge, shop1NonFoodOvercharge, shop1ExpirationDaysBeforeReduction, shop1ReductionPercentage );
        System.out.println(receipt);


        //---------------------------------------------------------------------------------------------------------------------------------------------------

        BigDecimal shop2FoodOvercharge = BigDecimal.valueOf(40);
        BigDecimal shop2NonFoodOvercharge = BigDecimal.valueOf(35);
        int shop2ExpirationDaysBeforeReduction = 10;
        BigDecimal shop2ReductionPercentage = BigDecimal.valueOf(23);

        Cashier cashier2Shop2 = new Cashier("Jessica Owlens", BigDecimal.valueOf(2000));
        Shop shop2 = new Shop("~~~ Lidl ~~~",shop1FoodOvercharge, shop2NonFoodOvercharge, shop2ExpirationDaysBeforeReduction, shop2ReductionPercentage);
        System.out.println();
        System.out.println("            Welcome to "+shop2.getId());
        Client client2 = new Client(BigDecimal.valueOf(2200), shop2);
        Map<Product, Integer> client2GoodsList = new HashMap<>();
        client2GoodsList.put(milk, 1);
        client2GoodsList.put(butter, 2);
        shop2.hireCashier(cashier2Shop2);

        client2.setGoodsList(client2GoodsList);
        Receipt receipt1 = new Receipt(cashier2Shop2, now, client2GoodsList, shop1FoodOvercharge, shop1NonFoodOvercharge, shop1ExpirationDaysBeforeReduction, shop1ReductionPercentage );
        System.out.println(receipt1);

        //---------------------------------------------------------------------------------------------------------------------------------------------
        Cashier cashier3Shop2 = new Cashier("Nina Dobrev", BigDecimal.valueOf(2000));
        shop2.hireCashier(cashier3Shop2);
        Client client3 = new Client(BigDecimal.valueOf(10000), shop1);
        Map<Product, Integer> client3GoodsList = new HashMap<>();
        client3GoodsList.put(milk, 3);
        client3GoodsList.put(butter, 5);
        client3GoodsList.put(broom, 1);

        client3.setGoodsList(client3GoodsList);
        Receipt receipt2 = new Receipt(cashier3Shop2, now, client3GoodsList, shop2FoodOvercharge, shop2NonFoodOvercharge, shop2ExpirationDaysBeforeReduction, shop2ReductionPercentage );
        System.out.println(receipt2);
        CashRegister cashRegister1Shop1 = new CashRegister(cashier1Shop1, shop1);
        CashRegister cashRegister2Shop2 = new CashRegister(cashier2Shop2, shop1);

        cashRegister1Shop1.lineUpClient(client1);
        cashRegister1Shop1.lineUpClient(client3);

        cashRegister2Shop2.lineUpClient(client2);

        CashRegisterThread cashRegister1 = new CashRegisterThread(cashRegister1Shop1);
        CashRegisterThread cashRegister2 = new CashRegisterThread(cashRegister2Shop2);

        Thread cashRegisterThread1 = new Thread(cashRegister1);
        Thread cashRegisterThread2 = new Thread(cashRegister2);

        cashRegisterThread1.start();
        cashRegisterThread2.start();
    }
}
