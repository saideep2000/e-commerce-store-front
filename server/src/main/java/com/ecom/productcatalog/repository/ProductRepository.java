package com.ecom.productcatalog.repository;

import com.ecom.productcatalog.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

//  Here JPA Hibernate will get the desired when written in this spring data JPA naming convention
  List<Product> findByCategoryId(Long categoryId);

}
