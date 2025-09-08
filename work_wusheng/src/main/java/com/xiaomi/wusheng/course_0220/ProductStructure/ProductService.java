package com. xiaomi. wusheng. course_0220. ProductStructure;

public class ProductService {

    // 模拟查询商品详情
    public ApiResponse<Product<?>> getProductDetail(String productId) {
        // 根据商品ID判断商品类型
        if (productId.startsWith("PHY")) {
            // 实物商品
            Product<PhysicalAttributes> product = new Product<>();
            product.setProductId(productId);
            product.setName("实物商品示例");
            product.setPrice(99.99);
            product.setDescription("这是一个实物商品");
            product.setType(ProductType.PHYSICAL);

            PhysicalAttributes attributes = new PhysicalAttributes();
            attributes.setStock(100);
            attributes.setWeight("1.5kg");
            attributes.setLogistics("免运费");
            product.setAttributes(attributes);

            return new ApiResponse<>(200, "success", product);
        } else if (productId.startsWith("VIR")) {
            // 虚拟商品
            Product<VirtualAttributes> product = new Product<>();
            product.setProductId(productId);
            product.setName("虚拟商品示例");
            product.setPrice(49.99);
            product.setDescription("这是一个虚拟商品");
            product.setType(ProductType.VIRTUAL);

            VirtualAttributes attributes = new VirtualAttributes();
            attributes.setRedeemCode("ABCD-1234");
            attributes.setValidityPeriod("2023-12-31");
            attributes.setUsageRules("仅限一次性使用");
            product.setAttributes(attributes);

            return new ApiResponse<>(200, "success", product);
        } else {
            // 未知商品类型
            return new ApiResponse<>(404, "Product not found", null);
        }
    }
}
