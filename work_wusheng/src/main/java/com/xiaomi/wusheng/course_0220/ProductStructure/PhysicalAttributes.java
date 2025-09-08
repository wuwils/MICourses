package com. xiaomi. wusheng. course_0220. ProductStructure;

public class PhysicalAttributes {
    private int stock; // 库存
    private String weight; // 重量
    private String logistics; // 物流信息

    // Getters and Setters
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getLogistics() {
        return logistics;
    }

    public void setLogistics(String logistics) {
        this.logistics = logistics;
    }

    @Override
    public String toString() {
        return "PhysicalAttributes{" +
                "stock=" + stock +
                ", weight='" + weight + '\'' +
                ", logistics='" + logistics + '\'' +
                '}';
    }
}

