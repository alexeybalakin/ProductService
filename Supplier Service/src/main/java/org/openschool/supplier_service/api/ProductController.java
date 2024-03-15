package org.openschool.supplier_service.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openschool.supplier_service.dao.entity.Product;
import org.openschool.supplier_service.exceptions.ProductNotFoundException;
import org.openschool.supplier_service.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping()
    public Page<Product> findProducts(@RequestParam(required = false) String name,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "3") int size) {
        return productService.findProducts(name, page, size);
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        Optional<Product> product = productService.getById(id);
        if (product.isEmpty()) {
            log.info("Продукт с id = {} не найден", id);
            throw new ProductNotFoundException();
        }
        return product.get();
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.delete(id);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@RequestBody Product product, @PathVariable Long id) {
        return productService.update(product, id);
    }
}
