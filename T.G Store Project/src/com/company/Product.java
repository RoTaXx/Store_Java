package com.company;

import com.company.Exceptions.ExpiredProduct;

import java.io.Serializable;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

public class Product implements Serializable {
    private int id;
    private String name;
    private BigDecimal deliveryPrice;
    private static int goods_qty = 0;
    private ProductType productType;
    private LocalDate expDate;

    public Product(String name, BigDecimal deliveryPrice,  ProductType productType, LocalDate expDate) {
        this.name = name;
        this.deliveryPrice = deliveryPrice;
        this.productType = productType;
        this.expDate = expDate;
        Product.goods_qty++;
        this.id = Product.goods_qty;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public static int getGoods_qty() {
        return goods_qty;
    }

    public ProductType getProductType() {
        return productType;
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public BigDecimal salePrice(BigDecimal overcharge, int expirationDaysBeforeReduction, BigDecimal reductionPercentage) {
        if(this.expDate.isBefore(LocalDate.now())) {
            try {
                throw new ExpiredProduct(this.getName());
            } catch (ExpiredProduct e) {
                e.printStackTrace();
            }
            return BigDecimal.ZERO;
        }

        BigDecimal salePrice = this.deliveryPrice;
        salePrice = salePrice.add(salePrice.multiply(overcharge).divide(BigDecimal.valueOf(100)));
        if(ChronoUnit.DAYS.between(LocalDate.now(), this.expDate) < expirationDaysBeforeReduction) {
            salePrice = salePrice.subtract(salePrice.multiply(reductionPercentage).divide(BigDecimal.valueOf(100)));
        }

        return salePrice;
    }

    @Override
    public String toString() {
        return name ;
    }
}
