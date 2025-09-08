package com. xiaomi. wusheng. course_0220. ProductSku;

public class Sku implements Comparable<Sku> {
    private String skuId; // SKU ID
    private String skuName; // SKU 名称
    private double price; // 价格
    private int salesVolume; // 销量

    // 构造方法
    public Sku(String skuId, String skuName, double price, int salesVolume) {
        this.skuId = skuId;
        this.skuName = skuName;
        this.price = price;
        this.salesVolume = salesVolume;
    }

    // Getters and Setters
    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(int salesVolume) {
        this.salesVolume = salesVolume;
    }

    @Override
    public String toString() {
        return "Sku{" +
                "skuId='" + skuId + '\'' +
                ", skuName='" + skuName + '\'' +
                ", price=" + price +
                ", salesVolume=" + salesVolume +
                '}';
    }

    // 实现 Comparable 接口，按销量倒序排序
    @Override
    public int compareTo(Sku other) {
        return Integer.compare(other.salesVolume, this.salesVolume); // 倒序
    }
}
