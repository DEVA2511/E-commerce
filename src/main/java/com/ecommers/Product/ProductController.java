package com.ecommers.Product;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Get all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Get product by ID
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }

    // Add a product without image
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product createdProduct = productService.addProduct(product);
        return ResponseEntity.ok(createdProduct);
    }

    // Add a product with image upload
    @PostMapping("/add")
    public ResponseEntity<Product> addProductWithImage(
            @RequestParam("product") String productJson,
            @RequestParam("image") MultipartFile imageFile) throws IOException {

        // Convert product JSON to Product object
        Product product = new ObjectMapper().readValue(productJson, Product.class);

        // Validate image file
        if (imageFile.isEmpty()) {
            return ResponseEntity.badRequest().body(null); // Return 400 if no image is uploaded
        }

        // Save the image to a directory
        String imageFileName = "images/" + imageFile.getOriginalFilename();
        Files.copy(imageFile.getInputStream(), Paths.get(imageFileName));

        // Set the image URL
        product.setImageUrl("/images/" + imageFile.getOriginalFilename());

        // Save product
        Product createdProduct = productService.addProduct(product);

        return ResponseEntity.ok(createdProduct);
    }

    // Update product
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        return productService.updateProduct(id, productDetails);
    }

    // Delete product
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
