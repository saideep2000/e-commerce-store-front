package com.ecom.productcatalog.controller;

import com.ecom.productcatalog.model.Category;
import com.ecom.productcatalog.service.CategoryService;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
@Import(CategoryControllerTest.MockConfig.class)
public class CategoryControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private CategoryService categoryService;

  public void testGetAllCategories() throws Exception{
    Category category1 = new Category(1L, "Electronics", Collections.EMPTY_SET);
    Category category2 = new Category(2L, "Books", Collections.EMPTY_SET);

    List<Category> mockCategories = Arrays.asList(category1, category2);

    when(categoryService.getAllCategories()).thenReturn(mockCategories);

    // Act & Assert
    mockMvc.perform(get("/api/categories")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(mockCategories.size()))
            .andExpect(jsonPath("$[0].name").value("Electronics"))
            .andExpect(jsonPath("$[1].name").value("Books"));


  }

  @TestConfiguration
  static class MockConfig {
    @Bean
    public CategoryService categoryService(){
      return Mockito.mock(CategoryService.class);
    }
  }
}
