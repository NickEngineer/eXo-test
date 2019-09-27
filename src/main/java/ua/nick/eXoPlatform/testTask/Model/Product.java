package ua.nick.eXoPlatform.testTask.Model;

/*
 * vendorCode it's a type of product, as well as its digital
 * or letter designation for encoding
 *
 * */

public class Product {
    private String name;
    private String vendorCode;
    private int price;

    public Product(String name, String vendorCode, int price) {
        this.name = name;
        this.vendorCode = vendorCode;
        this.price = price;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
