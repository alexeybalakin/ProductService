package org.openschool.supplier_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openschool.supplier_service.dao.entity.Product;
import org.openschool.supplier_service.dao.repository.ProductRepository;
import org.openschool.supplier_service.exceptions.ProductNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public Page<Product> findProducts(String name, int page, int size) {

        Pageable paging = PageRequest.of(page, size);
        Page<Product> productPage;
        if (name == null) {
           productPage = repository.findAll(paging);

        } else {
            productPage = repository.findByNameContaining(name, paging);
        }
        return productPage;
    }

    public Product save(Product product) {
        return repository.save(product);
    }

    public Optional<Product> getById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Product update(Product product, Long id) {
        Optional<Product> optionalProduct = repository.findById(id);
        if (optionalProduct.isEmpty()) {
            log.info("Продукт с id = {} не найден", id);
            throw new ProductNotFoundException();
        }

        Product existedProduct = optionalProduct.get();
        existedProduct.setName(product.getName());
        existedProduct.setDescription(product.getDescription());
        existedProduct.setPrice(product.getPrice());
        existedProduct.setCategory(product.getCategory());

        return repository.save(existedProduct);
    }
}
