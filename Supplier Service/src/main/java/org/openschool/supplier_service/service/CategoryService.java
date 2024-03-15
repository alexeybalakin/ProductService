package org.openschool.supplier_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openschool.supplier_service.dao.entity.Category;
import org.openschool.supplier_service.dao.entity.Product;
import org.openschool.supplier_service.dao.repository.CategoryRepository;
import org.openschool.supplier_service.exceptions.CategoryNotFoundException;
import org.openschool.supplier_service.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Optional<Category> getById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Category save(Category category) {
        return repository.save(category);
    }

    public Category update(Category category, Long id) {
        Optional<Category> optionalCategory = repository.findById(id);
        if (optionalCategory.isEmpty()) {
            log.info("Категория с id = {} не найдена", id);
            throw new CategoryNotFoundException();
        }

        Category existedCategory = optionalCategory.get();
        existedCategory.setName(category.getName());

        return repository.save(existedCategory);
    }

}
