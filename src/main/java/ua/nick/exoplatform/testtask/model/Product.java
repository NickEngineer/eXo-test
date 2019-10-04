package ua.nick.exoplatform.testtask.model;

/*
 * vendorCode it's a type of product, as well as its digital
 * or letter designation for encoding
 *
 * */

import java.math.BigDecimal;

public class Product {
    private String name;
    private String vendorCode;
    private BigDecimal price;

    public Product(String name, String vendorCode, BigDecimal price) {
        this.name = name;
        this.vendorCode = vendorCode;
        this.price = price;
    }

    public Product(Product product) {
        this.name = product.getName();
        this.vendorCode = product.getVendorCode();
        this.price = product.getPrice();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
