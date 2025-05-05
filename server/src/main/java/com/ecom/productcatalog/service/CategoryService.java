package com.ecom.productcatalog.service;

import com.ecom.productcatalog.model.Category;
import com.ecom.productcatalog.repository.CategoryRespository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
  public CategoryService(CategoryRespository categoryRespository) {
    this.categoryRespository = categoryRespository;
  }

  private final CategoryRespository categoryRespository;

  public List<Category> getAllCategories() {
    return categoryRespository.findAll();
  }


}
