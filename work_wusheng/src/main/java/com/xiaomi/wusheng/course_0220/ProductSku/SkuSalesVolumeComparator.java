package com. xiaomi. wusheng. course_0220. ProductSku;

import java.util.Comparator;

public class SkuSalesVolumeComparator implements Comparator<Sku> {
    @Override
    public int compare(Sku sku1, Sku sku2) {
        return Integer.compare(sku2.getSalesVolume(), sku1.getSalesVolume()); // 倒序
    }
}
