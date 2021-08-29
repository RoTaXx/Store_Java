package com.company;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public class Receipt implements Serializable {
    private final int serialNumber;
    private final Cashier cashier;
    private final LocalDateTime issueDate;
    private final Map<Product, Integer> goodsList;
    private final BigDecimal totalPrice;
    private static int numberOfReceipts = 0;
    private static BigDecimal totalTurnover = BigDecimal.ZERO;

    public Receipt(Cashier cashier,
                   LocalDateTime issueDate,
                   Map<Product, Integer> goodsList,
                   BigDecimal foodstuffOvercharge,
                   BigDecimal nonFoodItemsOvercharge,
                   int expirationDaysBeforeReduction,
                   BigDecimal reductionPercentage) {

        this.cashier = cashier;
        this.issueDate = issueDate;
        this.goodsList = goodsList;
        Receipt.numberOfReceipts++;
        this.serialNumber = Receipt.numberOfReceipts;
        this.totalPrice = calculateTotalPrice(foodstuffOvercharge, nonFoodItemsOvercharge, expirationDaysBeforeReduction, reductionPercentage);
        Receipt.totalTurnover = Receipt.totalTurnover.add(this.totalPrice);
    }

    public int getSerialNumber() {
        return this.serialNumber;
    }

    public Cashier getCashier() {
        return this.cashier;
    }

    public LocalDateTime getIssueDate() {
        return this.issueDate;
    }

    public Map<Product, Integer> getGoodsList() {
        return this.goodsList;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public static int getNumberOfReceipts() {
        return Receipt.numberOfReceipts;
    }

    public static BigDecimal getTotalTurnover() {
        return Receipt.totalTurnover;
    }

    private BigDecimal calculateTotalPrice(BigDecimal foodstuffOvercharge,
                                           BigDecimal nonFoodItemsOvercharge,
                                           int expirationDaysBeforeReduction, BigDecimal reductionPercentage) {

        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Product goods : this.goodsList.keySet()) {
            BigDecimal overcharge = goods.getProductType() == ProductType.valueOf(ProductType.EDIBLE.name()) ? foodstuffOvercharge : nonFoodItemsOvercharge;
            totalPrice = totalPrice.add(goods.salePrice(
                    overcharge,
                    expirationDaysBeforeReduction,
                    reductionPercentage).multiply(BigDecimal.valueOf(this.goodsList.get(goods))));
        }
        return totalPrice;
    }

    public void serializeReceipt(String filePath) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Receipt deserializeReceipt(String filePath) {
        Receipt receipt = null;
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            receipt = (Receipt) objectInputStream.readObject();


        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
       catch (IOException e) {
            e.printStackTrace();
        }

        return receipt;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "serialNumber=" + serialNumber +
                ", cashier=" + cashier +
                ", issueDate=" + issueDate +
                ", goodsList=" + goodsList +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
