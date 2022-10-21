package com.example.productdiscount.classes;

import java.text.DecimalFormat;
import java.util.*;

public class Product {

    private List<String> listOfProducts;
    private double product_price;
    private double percentage_discount;
    private final DecimalFormat decimalFormatter = new DecimalFormat("0.00");

    public Product(List<String> listOfProducts, double product_price, double percentage_discount) {
        this.listOfProducts = listOfProducts;
        this.product_price = product_price;
        this.percentage_discount = percentage_discount;
    }

    public Product() {
        this.product_price = 0;
        this.percentage_discount = 0;
    }

    public List<String> getListOfProducts() {
        return listOfProducts;
    }

    public void setListOfProducts(List<String> listOfProducts) {
        this.listOfProducts = listOfProducts;
    }

    public double getProductPrice() {
        return product_price;
    }

    public void setProductPrice(double product_price) {
        this.product_price = Double.parseDouble(decimalFormatter.format(product_price));
    }

    public double getPercentage_discount() {
        return percentage_discount;
    }

    public void setPercentageDiscount(double percentage_discount) {
        this.percentage_discount = Double.parseDouble(decimalFormatter.format(percentage_discount));
    }

    public double getDiscountedAmount(){
        return Double.parseDouble(decimalFormatter.format(getProductPrice() * (getPercentage_discount() / 100)));
    }

    public double getAmountToPay(){
        return getProductPrice() - getDiscountedAmount();
    }

}
