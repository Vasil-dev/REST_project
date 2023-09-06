package ua.foxminded.vasilmartsyniuk.car_rest_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Category controller", description = "Methods for categories")
public class CategoryController {

    private final CategoryService categoryService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Get all category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found categories",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Category.class))})})
    @GetMapping("/all")
    public List<Category> categories() {
        LOGGER.info("Getting all categories");
        return categoryService.getAll();
    }

    @Operation(summary = "Get a category by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found category",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Category.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid category Id",
                    content = @Content)})
    @GetMapping("/show")
    public Category showCategoryById(@RequestParam("id") Integer id) {
        LOGGER.info("Getting categories by id: {}", id);
        return categoryService.getById(id);
    }

    @Operation(summary = "Add category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category was added",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Category.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid parameters",
                    content = @Content)})
    @PostMapping("/add")
    public ResponseEntity<String> createCategory(@RequestBody Category category) {
        categoryService.createCategory(category);
        LOGGER.info("Creating category: {}", category);
        return ResponseEntity.ok("Category created");
    }

    @Operation(summary = "Update category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category was updated",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Category.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid parameters",
                    content = @Content)})
    @PutMapping("/update")
    public ResponseEntity<String> updateCategory(@RequestParam("id") Integer id, @RequestBody Category category) {
        LOGGER.info("Updating category by id: {}: {}", id, category);
        category.setId(id);
        categoryService.updateCategory(category);
        return ResponseEntity.ok("Category updated");
    }

    @Operation(summary = "Delete category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category was deleted",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Category.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Id",
                    content = @Content)})
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCategory(@RequestParam("id") Integer id) {
        LOGGER.info("Deleting category with id: {}", id);
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted");
    }
}
