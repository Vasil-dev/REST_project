package ua.foxminded.vasilmartsyniuk.car_rest_project.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ua.foxminded.vasilmartsyniuk.car_rest_project.entity.Category;
import ua.foxminded.vasilmartsyniuk.car_rest_project.service.CategoryService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@ExtendWith(SpringExtension.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CategoryService categoryService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser
    void testGetAllCategories() throws Exception {
        List<Category> categories = Arrays.asList(
                new Category(1, "SUV"),
                new Category(2, "Sedan")
        );

        when(categoryService.getAll()).thenReturn(categories);

        mvc.perform(MockMvcRequestBuilders.get("/categories/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(categories.size()));
    }

    @Test
    @WithMockUser
    void testGetById() throws Exception {
        final int ID = 1;
        Category category = new Category(1, "Sedan");

        when(categoryService.getById(ID)).thenReturn(category);

        mvc.perform(MockMvcRequestBuilders.get("/categories/show")
                        .param("id", String.valueOf(ID))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(category.getId()))
                .andExpect(jsonPath("$.name").value(category.getName()));
    }

    @Test
    @WithMockUser
    void testCreateCategories() throws Exception {
        Category category = new Category(1, "Sedan");
        ObjectMapper objectMapper = new ObjectMapper();
        String categoryJson = objectMapper.writeValueAsString(category);

        mvc.perform(MockMvcRequestBuilders.post("/categories/add")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryJson))
                .andExpect(status().isOk());

        verify(categoryService, times(1)).createCategory(category);
    }

    @Test
    @WithMockUser
    void testDeleteCategory() throws Exception {
        int id = 1;
        Category category = new Category(id, "Sedan");

        when(categoryService.getById(id)).thenReturn(category);

        mvc.perform(MockMvcRequestBuilders.delete("/categories/delete")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("id", String.valueOf(id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Category deleted"));

        verify(categoryService, times(1)).deleteCategory(id);
    }
}