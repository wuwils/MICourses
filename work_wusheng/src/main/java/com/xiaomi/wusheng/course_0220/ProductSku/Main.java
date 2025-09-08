package com. xiaomi. wusheng. course_0220. ProductSku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 创建 SKU 列表
        List<Sku> skuList = new ArrayList<>();
        skuList.add(new Sku("SKU001", "商品A", 99.99, 150));
        skuList.add(new Sku("SKU002", "商品B", 199.99, 300));
        skuList.add(new Sku("SKU003", "商品C", 49.99, 50));
        skuList.add(new Sku("SKU004", "商品D", 299.99, 200));

        // 使用 Comparable 排序（按销量倒序）
        Collections.sort(skuList);
        System.out.println("按销量倒序排序（使用 Comparable）：");
        for (Sku sku : skuList) {
            System.out.println(sku);
        }

        // 使用 Comparator 排序（按销量倒序）
        skuList.sort(new SkuSalesVolumeComparator());
        System.out.println("\n按销量倒序排序（使用 Comparator）：");
        for (Sku sku : skuList) {
            System.out.println(sku);
        }
    }
}