package com. xiaomi. wusheng. course_0220. ProductStructure;

public class Main {
    public static void main(String[] args) {
        ProductService productService = new ProductService();

        // 查询实物商品
        ApiResponse<Product<?>> physicalProductResponse = productService.getProductDetail("PHY123");
        System.out.println("实物商品详情: " + physicalProductResponse.getData().getAttributes());

        // 查询虚拟商品
        ApiResponse<Product<?>> virtualProductResponse = productService.getProductDetail("VIR456");
        System.out.println("虚拟商品详情: " + virtualProductResponse.getData().getAttributes());
    }
}

