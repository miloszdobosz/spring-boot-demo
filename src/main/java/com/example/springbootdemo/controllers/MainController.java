package com.example.springbootdemo.controllers;

import com.example.springbootdemo.schemas.Category;
import com.example.springbootdemo.schemas.Item;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class MainController {
    ArrayList<Category> categories = new ArrayList<>();

    @Operation(
            description = "Get all existing categories.",
            responses = {
                    @ApiResponse(responseCode = "200"),
            })
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Category> getHome() {
        return categories;
    }

    @Operation(
            description = "Delete all existing categories.",
            responses = {
                    @ApiResponse(responseCode = "200"),
            })
    @DeleteMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteHome() {
        this.categories = new ArrayList<>();
    }

    @Operation(
            description = "Get category by id.",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "Category not found")
            })
    @GetMapping(value = "/category", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> getCategory(@RequestParam(value = "id") int id) {
        try {
            return new ResponseEntity<>(categories.get(id), HttpStatus.OK);
        } catch (IndexOutOfBoundsException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            description = "Add a new category.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Category added."),
            })
    @PostMapping(value = "/category", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> postCategory(@RequestParam(value = "name") String name) {
        Category category = new Category(categories.size(), name, new ArrayList<>());
        this.categories.add(category);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @Operation(
            description = "Replace existing category by id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Category replaced."),
                    @ApiResponse(responseCode = "404", description = "Category not found.")
            })
    @PutMapping(value = "/category", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> putCategory(@RequestParam(value = "id") int id, @RequestParam String name) {
        try {
            this.categories.set(id, new Category(id, name, new ArrayList<>()));
            return new ResponseEntity<>(this.categories.get(id), HttpStatus.OK);
        } catch (IndexOutOfBoundsException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            description = "Delete existing category by id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Category replaced."),
                    @ApiResponse(responseCode = "404", description = "Category not found.")
            })
    @DeleteMapping(value = "/category", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> deleteCategory(@RequestParam(value = "id") int id) {
        try {
            this.categories.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IndexOutOfBoundsException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            description = "Get item by id in category with category_id.",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "Category not found."),
                    @ApiResponse(responseCode = "454", description = "Item not found in existing category.")
            })
    @GetMapping(value = "/item", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> getItem(@RequestParam(value = "category_id") int category_id, @RequestParam(value = "id") int id) {
        try {
            Category category = categories.get(category_id);
            try {
                return new ResponseEntity<>(category.getItems().get(id), HttpStatus.OK);
            } catch (IndexOutOfBoundsException e) {
                // Item not found
                return new ResponseEntity<>(HttpStatusCode.valueOf(454));
            }
        } catch (IndexOutOfBoundsException e) {
            // Category not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            description = "Add new item to a category with category_id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Item added."),
                    @ApiResponse(responseCode = "404", description = "Category not found."),
            })
    @PostMapping(value = "/item", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> postItem(@RequestParam(value = "category_id") int category_id, @RequestParam(value = "name") String name) {
        try {
            Category category = this.categories.get(category_id);
            ArrayList<Item> items = category.getItems();
            Item item = new Item(category_id, items.size(), name);
            items.add(item);
            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (IndexOutOfBoundsException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            description = "Update existing item by id in a category with category_id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Item updated."),
                    @ApiResponse(responseCode = "404", description = "Category not found."),
                    @ApiResponse(responseCode = "454", description = "Item not found in existing category.")
            })
    @PutMapping(value = "/item", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> putItem(@RequestParam(value = "category_id") int category_id, @RequestParam(value = "id") int id, @RequestParam String name) {
        try {
            Category category = this.categories.get(category_id);
            try {
                category.getItems().set(id, new Item(category_id, id, name));
                return new ResponseEntity<>(category.getItems().get(id), HttpStatus.OK);
            } catch (IndexOutOfBoundsException e) {
                // Item not found
                return new ResponseEntity<>(HttpStatusCode.valueOf(454));
            }
        } catch (IndexOutOfBoundsException e) {
            // Category not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            description = "Delete existing item by id from a category with category_id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Item deleted."),
                    @ApiResponse(responseCode = "404", description = "Category not found."),
                    @ApiResponse(responseCode = "454", description = "Item not found in existing category.")
            })
    @DeleteMapping(value = "/item", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> deleteItem(@RequestParam(value = "category_id") int category_id, @RequestParam(value = "id") int id) {
        try {
            Category category = this.categories.get(category_id);
            try {
                category.getItems().remove(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (IndexOutOfBoundsException e) {
                // Item not found
                return new ResponseEntity<>(HttpStatusCode.valueOf(454));
            }
        } catch (IndexOutOfBoundsException e) {
            // Category not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
