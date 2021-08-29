package com.company;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shop {
    private String id;
    private List<Cashier> cashiers;
    private Map<Product, Integer> deliveredGoods;
    private Map<Product, Integer> soldGoods;
    private List<Receipt> issuedReceipts;
    private BigDecimal foodstuffOvercharge = BigDecimal.ZERO;
    private BigDecimal nonFoodItemsOvercharge = BigDecimal.ZERO;
    private int expirationDaysBeforeReduction;
    private BigDecimal reductionPercentage;

    public Shop(String id, BigDecimal foodstuffOvercharge,
                BigDecimal nonFoodItemsOvercharge,
                int expirationDaysBeforeReduction,
                BigDecimal reductionPercentage) {
        this.id = id;
        this.foodstuffOvercharge = foodstuffOvercharge;
        this.nonFoodItemsOvercharge = nonFoodItemsOvercharge;
        this.expirationDaysBeforeReduction = expirationDaysBeforeReduction;
        this.reductionPercentage = reductionPercentage;
        this.cashiers = new ArrayList<>();
        this.deliveredGoods = new HashMap<>();
        this.soldGoods = new HashMap<>();
        this.issuedReceipts = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public int getExpirationDaysBeforeReduction() {
        return this.expirationDaysBeforeReduction;
    }

    public BigDecimal getReductionPercentage() {
        return this.reductionPercentage;
    }

    public List<Cashier> getCashiers() {
        return this.cashiers;
    }

    public Map<Product, Integer> getDeliveredGoods() {
        return this.deliveredGoods;
    }

    public Map<Product, Integer> getSoldGoods() {
        return this.soldGoods;
    }

    public List<Receipt> getIssuedReceipts() {
        return this.issuedReceipts;
    }

    public void deliverGoods(Map<Product, Integer> goodsToDeliver) {
        for (Product goods : goodsToDeliver.keySet()) {
            this.deliveredGoods.put(goods, goodsToDeliver.get(goods));
        }
    }

    private void reduceDeliveredGoods(Product goods, int quantity) {
        this.deliveredGoods.replace(goods, this.deliveredGoods.get(goods) - quantity);
    }

    public void hireCashier(Cashier cashier) {
        if(!this.cashiers.contains(cashier)) {
            this.cashiers.add(cashier);
        }
    }

    public void addReceipt(Receipt receipt) {
        this.issuedReceipts.add(receipt);
    }

    public void addGoodsToSold(Product goods, int quantity) {
        this.soldGoods.put(goods, quantity);
        this.reduceDeliveredGoods(goods, quantity);
    }

    public BigDecimal getFoodstuffOvercharge() {
        return foodstuffOvercharge;
    }

    public BigDecimal getNonFoodItemsOvercharge() {
        return nonFoodItemsOvercharge;
    }



    public BigDecimal calculateIncome() {
        BigDecimal result = BigDecimal.ZERO;

        for (Product goods : this.soldGoods.keySet()) {
            BigDecimal overcharge = goods.getProductType() == ProductType.valueOf(ProductType.EDIBLE.name()) ?
                    this.getFoodstuffOvercharge() :
                    this.getNonFoodItemsOvercharge();

            result = result.add(goods.salePrice(overcharge, this.getExpirationDaysBeforeReduction(), this.getReductionPercentage()));
        }

        return result;
    }


}
