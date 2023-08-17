package ua.foxminded.vasilmartsyniuk.car_rest_project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import ua.foxminded.vasilmartsyniuk.car_rest_project.entity.Category;
import ua.foxminded.vasilmartsyniuk.car_rest_project.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public List<Category> categories() {
        LOGGER.info("Getting all categories");
        return categoryService.getAll();
    }

    @GetMapping("/show")
    public Category showCategoryById(@RequestParam("id") Integer id) {
        LOGGER.info("Getting categories by id: {}", id);
        return categoryService.getById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<String> createCategory(@RequestBody Category category) {
        categoryService.createCategory(category);
        LOGGER.info("Creating category: {}", category);
        return ResponseEntity.ok("Category created");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCategory(@RequestParam("id") Integer id, @RequestBody Category category) {
        LOGGER.info("Updating category by id: {}: {}", id, category);
        category.setId(id);
        categoryService.updateCategory(category);
        return ResponseEntity.ok("Category updated");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCategory(@RequestParam("id") Integer id) {
        LOGGER.info("Deleting category with id: {}", id);
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted");
    }
}
