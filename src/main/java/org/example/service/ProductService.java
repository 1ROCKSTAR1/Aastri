package org.example.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ProductDTO;
import org.example.entity.Product;
import org.example.exception.ProductNotFoundException;
import org.example.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product create(ProductDTO dto) {
        log.info("Creating product: {}", dto.getName());
        return productRepository.save(Product.builder()
                .name(dto.getName())
                .amount(dto.getAmount())
                .build());
    }

    public Product readById(Long id) {
        log.debug("Reading product by id: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
    }

    public List<Product> readAll() {
        log.debug("Reading all products");
        return productRepository.findAll();
    }

    @Transactional
    public ProductDTO update(ProductDTO dto) {
        log.info("Updating product with id: {}", dto.getId());

        Product existingProduct = productRepository.findById(dto.getId())
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product with id " + dto.getId() + " not found for update"
                ));

        existingProduct.setName(dto.getName());
        existingProduct.setAmount(dto.getAmount());

        Product saved = productRepository.save(existingProduct);

        return convertToDto(saved);
    }

    public void delete(Long id) {
        log.info("Deleting product with id: {}", id);

        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product with id " + id + " not found for deletion");
        }
        productRepository.deleteById(id);
    }

    private ProductDTO convertToDto(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setAmount(product.getAmount());
        return dto;
    }
}
