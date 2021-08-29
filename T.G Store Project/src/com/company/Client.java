package com.company;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Client {
    private BigDecimal money;
    private Map<Product, Integer> goodsList;
    private Shop relatedShop;

    public Client(BigDecimal money, Shop relatedShop) {
        this.money = money;
        this.relatedShop = relatedShop;
        this.goodsList = new HashMap<>();
    }

    public Client(BigDecimal money, Shop relatedShop, Map<Product, Integer> goodsList) {
        this.money = money;
        this.relatedShop = relatedShop;
        this.goodsList = goodsList;
    }

    public BigDecimal getMoney() {
        return this.money;
    }

    public Map<Product, Integer> getGoodsList() {
        return this.goodsList;
    }

    public void setGoodsList(Map<Product, Integer> changedGoods) {
        this.goodsList = changedGoods;
    }

    public boolean hasEnoughMoney(int expirationDaysBeforeReduction, BigDecimal reductionPercentage) {
        BigDecimal sumOfGoodsTaken = BigDecimal.ZERO;

        for (Product goods : this.goodsList.keySet()) {

            BigDecimal overcharge = goods.getProductType() == ProductType.valueOf(ProductType.EDIBLE.name()) ?
                    this.relatedShop.getFoodstuffOvercharge() :
                    this.relatedShop.getNonFoodItemsOvercharge();

            sumOfGoodsTaken = (sumOfGoodsTaken.add(goods.salePrice(
                    overcharge,
                    expirationDaysBeforeReduction,
                    reductionPercentage)).multiply(BigDecimal.valueOf(this.goodsList.get(goods))));
        }

        return this.money.compareTo(sumOfGoodsTaken) >= 0;
    }

}
