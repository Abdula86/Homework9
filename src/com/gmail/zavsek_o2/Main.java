package com.gmail.zavsek_o2;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

class Product {
    String type;
    double price;
    boolean discount;
    LocalDate createDate;

    public Product(String type, double price, boolean discount, LocalDate createDate) {
        this.type = type;
        this.price = price;
        this.discount = discount;
        this.createDate = createDate;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public boolean hasDiscount() {
        return discount;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }
}

public class Main {
    public static void main(String[] args) {
        List<Product> products = Arrays.asList(
                new Product("Book", 300, false, LocalDate.now()),
                new Product("Book", 200, true, LocalDate.now()),
                new Product("Toy", 150, false, LocalDate.now())
        );

        List<Product> expensiveBooks = products.stream()
                .filter(product -> product.getType().equals("Book") && product.getPrice() > 250)
                .toList();
        System.out.println("Expensive Books: " + expensiveBooks);

        List<Product> discountedBooks = products.stream()
                .filter(product -> product.getType().equals("Book") && product.hasDiscount())
                .peek(product -> {
                    if (product.hasDiscount()) {
                        product.price *= 0.9;
                    }
                })
                .toList();
        System.out.println("Discounted Books: " + discountedBooks);

        Product cheapestBook = products.stream()
                .filter(product -> product.getType().equals("Book"))
                .min(Comparator.comparingDouble(Product::getPrice))
                .orElseThrow(() -> new NoSuchElementException("Продукт [категорія: Book] не знайдено"));
        System.out.println("Cheapest Book: " + cheapestBook);

        List<Product> lastThreeAddedProducts = products.stream()
                .sorted(Comparator.comparing(Product::getCreateDate).reversed())
                .limit(3)
                .toList();
        System.out.println("Last Three Added Products: " + lastThreeAddedProducts);

        double totalPrice = products.stream()
                .filter(product -> product.getCreateDate().getYear() == LocalDate.now().getYear())
                .filter(product -> product.getType().equals("Book"))
                .filter(product -> product.getPrice() <= 75)
                .mapToDouble(Product::getPrice)
                .sum();
        System.out.println("Total Price of Books added this year under $75: " + totalPrice);

        Map<String, List<Product>> groupedProducts = products.stream()
                .collect(Collectors.groupingBy(Product::getType));
        System.out.println("Grouped Products: " + groupedProducts);
    }
}
