package org.openschool.supplier_service.dao.repository;

import org.openschool.supplier_service.dao.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByNameContaining(String name, Pageable pageable);
}

