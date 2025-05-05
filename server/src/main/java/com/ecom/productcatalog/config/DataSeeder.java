package com.ecom.productcatalog.config;

import com.ecom.productcatalog.model.Category;
import com.ecom.productcatalog.model.Product;
import com.ecom.productcatalog.repository.CategoryRespository;
import com.ecom.productcatalog.repository.ProductRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

  private final ProductRepository productRepository;
  private final CategoryRespository categoryRespository;

  public DataSeeder(ProductRepository productRepository, CategoryRespository categoryRespository) {
    this.productRepository = productRepository;
    this.categoryRespository = categoryRespository;
  }

  @Override
  public void run(String... args) throws Exception {
    // Clear all existing data
    productRepository.deleteAll();
    categoryRespository.deleteAll();

    // Create and save categories
    Category electronics = new Category();
    electronics.setName("Electronics");

    Category clothing = new Category();
    clothing.setName("Clothing");

    Category home = new Category();
    home.setName("Home");

    Category toys = new Category();
    toys.setName("Toys");

    // Save and fetch the persisted categories (with generated IDs)
    List<Category> savedCategories = categoryRespository.saveAll(List.of(electronics, clothing, home, toys));
    electronics = savedCategories.get(0);
    clothing = savedCategories.get(1);
    home = savedCategories.get(2);
    toys = savedCategories.get(3);

    List<Product> products = new ArrayList<>();

    // Electronics
    for (int i = 1; i <= 25; i++) {
      Product p = new Product();
      p.setName("Electronic Product " + i);
      p.setDescription("High-quality electronic device number " + i);
      p.setImageUrl("https://placehold.co/600x400");
      p.setPrice((double) (100 + i));
      p.setCategory(electronics);
      products.add(p);
    }

    // Clothing
    for (int i = 1; i <= 25; i++) {
      Product p = new Product();
      p.setName("Clothing Item " + i);
      p.setDescription("Trendy clothing piece number " + i);
      p.setImageUrl("https://placehold.co/600x400");
      p.setPrice((double) (20 + i));
      p.setCategory(clothing);
      products.add(p);
    }

    // Home
    for (int i = 1; i <= 25; i++) {
      Product p = new Product();
      p.setName("Home Product " + i);
      p.setDescription("Essential home item number " + i);
      p.setImageUrl("https://placehold.co/600x400");
      p.setPrice((double) (50 + i));
      p.setCategory(home);
      products.add(p);
    }

    // Toys
    for (int i = 1; i <= 25; i++) {
      Product p = new Product();
      p.setName("Toy " + i);
      p.setDescription("Fun toy for kids number " + i);
      p.setImageUrl("https://placehold.co/600x400");
      p.setPrice((double) (15 + i));
      p.setCategory(toys);
      products.add(p);
    }

    productRepository.saveAll(products);
  }
}
