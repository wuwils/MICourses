package com.xiaomi.wusheng.course_0222.ProductStream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<Product> products = Arrays.asList(
                new Product(1, "aa", 10.5, "C", Arrays.asList("11", "22"), null),
                new Product(2, "bb", 50.5, "B", Arrays.asList("11", "33"), null),
                new Product(3, "cc", 200.5, "A", Arrays.asList("11", "44"), null),
                new Product(4, "dd", 3999.99, "C", Arrays.asList("22", "33"), null),
                new Product(5, "ee", 7999.99, "B", Arrays.asList("22", "44"), null),
                new Product(6, "ff", 9999.99, "A", Arrays.asList("33", "44"), null)
        );

        List<Product> filteredProducts = products.stream()
                .filter(product -> product.getPrice() >= 100)
                .collect(Collectors.toList());

        System.out.println("价格大于等于100元的商品:");
        filteredProducts.forEach(System.out::println);

        List<Product> sortedProducts = filteredProducts.stream()
                .sorted(Comparator.comparingDouble(Product::getPrice).reversed())
                .collect(Collectors.toList());

        System.out.println("按价格从高到低排序后的商品:");
        sortedProducts.forEach(System.out::println);

        List<String> productStrings = sortedProducts.stream()
                .map(product -> "name: " + product.getName() + ", price: " + product.getPrice())
                .collect(Collectors.toList());

        System.out.println("商品名称和价格的字符串:");
        productStrings.forEach(System.out::println);

        Map<String, List<String>> groupedByCategory = productStrings.stream()
                .collect(Collectors.groupingBy(
                        str -> {
                            for (Product product : sortedProducts) {
                                if (str.contains(product.getName())) {
                                    return product.getCategory();
                                }
                            }
                            return "未知分类";
                        }
                ));

        System.out.println("按分类分组的商品字符串:");
        groupedByCategory.forEach((category, list) -> {
            System.out.println("分类: " + category);
            list.forEach(System.out::println);
        });

        Map<String, Double> averagePriceByCategory = sortedProducts.stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.averagingDouble(Product::getPrice)
                ));

        System.out.println("每个分类的平均价格:");
        averagePriceByCategory.forEach((category, avgPrice) ->
                System.out.println("分类: " + category + ", 平均价格: " + avgPrice)
        );

        List<String> allTags = products.stream()
                .flatMap(product -> product.getTags().stream())
                .distinct()
                .collect(Collectors.toList());

        System.out.println("所有商品的标签:");
        allTags.forEach(System.out::println);

        double totalPrice = products.stream()
                .mapToDouble(Product::getPrice)
                .reduce(0, Double::sum);

        System.out.println("所有商品的总价: " + totalPrice);

        Map<Boolean, List<Product>> partitionedProducts = products.stream()
                .collect(Collectors.partitioningBy(product -> product.getPrice() >= 5000));

        System.out.println("高价商品（价格>=5000）:");
        partitionedProducts.get(true).forEach(System.out::println);

        System.out.println("低价商品（价格<5000）:");
        partitionedProducts.get(false).forEach(System.out::println);

        Map<String, List<Product>> groupedByTag = products.stream()
                .flatMap(product -> product.getTags().stream()
                        .map(tag -> new AbstractMap.SimpleEntry<>(tag, product)))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));

        System.out.println("按标签分组的商品:");
        groupedByTag.forEach((tag, productList) -> {
            System.out.println("标签: " + tag);
            productList.forEach(System.out::println);
        });

        List<Product> highPriceProducts = partitionedProducts.get(true);
        List<Product> lowPriceProducts = partitionedProducts.get(false);

        List<Product> mergedProducts = Stream.concat(highPriceProducts.stream(), lowPriceProducts.stream())
                .collect(Collectors.toList());

        System.out.println("合并后的商品列表:");
        mergedProducts.forEach(System.out::println);
    }
}
