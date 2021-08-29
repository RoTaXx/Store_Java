package com.company;

import com.company.Exceptions.ProductsOutOfStock;
import com.company.Exceptions.NoMoney;
import com.company.Exceptions.Quantity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CashRegister {
    private Cashier cashier;
    private final Map<Product, Integer> markedGoods;
    private final List<Client> clients;
    private final Shop relatedShop; //in witch Shop the certain cashregister is located *different markups*

    public CashRegister(Cashier cashier, Shop relatedShop) {
        this.cashier = cashier;
        this.relatedShop = relatedShop;
        this.markedGoods = new HashMap<>();
        this.clients = new ArrayList<>();
    }

    public Cashier getCashier() {
        return this.cashier;
    }

    public List<Client> getLinedUpClients() {
        return this.clients;
    }

    public void lineUpClient(Client client) {
        this.clients.add(client);
    }

    public Shop getRelatedShop() {
        return relatedShop;
    }

    public void markGoods(Client client) {
        for (Product goods : client.getGoodsList().keySet()) {
            if(!this.getRelatedShop().getDeliveredGoods().containsKey(goods)) {
                try {
                    throw new ProductsOutOfStock(goods.getName());
                } catch (ProductsOutOfStock e) {
                    e.printStackTrace();
                }
            } else {
                if(this.getRelatedShop().getDeliveredGoods().get(goods) < client.getGoodsList().get(goods)) {
                    try {
                        throw new Quantity(
                                goods.getName(),
                                client.getGoodsList().get(goods) - this.getRelatedShop().getDeliveredGoods().get(goods));
                    } catch (Quantity e) {
                        e.printStackTrace();
                    }
                } else {
                    markedGoods.put(goods, client.getGoodsList().get(goods));
                }
            }
        }

        client.setGoodsList(this.markedGoods);
    }

    public void sellMarkedGoods(Client client) {
        if (!client.hasEnoughMoney(this.relatedShop.getExpirationDaysBeforeReduction(), this.relatedShop.getReductionPercentage())) {
            try {
                throw new NoMoney(client.getMoney(), this.markedGoods);
            } catch (NoMoney e) {
                e.printStackTrace();
            }
        } else {
            for (Product goods : this.markedGoods.keySet()) {
                this.relatedShop.addGoodsToSold(goods, this.markedGoods.get(goods));
            }
            Receipt receipt = this.issueReceipt(this.markedGoods,
                    this.relatedShop.getFoodstuffOvercharge(),
                    this.relatedShop.getNonFoodItemsOvercharge(),
                    this.relatedShop.getExpirationDaysBeforeReduction(),
                    this.relatedShop.getReductionPercentage());

            String filePath = "Receipt" + receipt.getSerialNumber() + ".ser";
            receipt.serializeReceipt(filePath);


            this.markedGoods.clear();
        }
    }

    private Receipt issueReceipt(Map<Product, Integer> goods,
                                 BigDecimal foodstuffOvercharge,
                                 BigDecimal nonFoodItemsOvercharge,
                                 int expirationDaysBeforeReduction,
                                 BigDecimal reductionPercentage) {

        Receipt receipt = new Receipt(this.cashier,
                LocalDateTime.now(), goods,
                foodstuffOvercharge, nonFoodItemsOvercharge,
                expirationDaysBeforeReduction,
                reductionPercentage);

        this.relatedShop.addReceipt(receipt);
        return receipt;
    }
}