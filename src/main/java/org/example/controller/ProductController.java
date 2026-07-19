package org.example.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ProductDTO;
import org.example.entity.Product;
import org.example.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody ProductDTO dto) {
        return new ResponseEntity<>(productService.create(dto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Product>> readAll() {
        return new ResponseEntity<>(productService.readAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> readById(@PathVariable Long id) {
        return new ResponseEntity<>(productService.readById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @Valid @RequestBody ProductDTO dto) {
        log.info("PUT request to update product with id: {}", id);

        dto.setId(id);

        ProductDTO updated = productService.update(dto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        productService.delete(id);
        return HttpStatus.OK;
    }
}
