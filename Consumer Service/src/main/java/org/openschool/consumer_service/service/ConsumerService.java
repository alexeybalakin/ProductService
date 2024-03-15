package org.openschool.consumer_service.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openschool.consumer_service.JacksonPage;
import org.openschool.consumer_service.model.Category;
import org.openschool.consumer_service.model.Product;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ConsumerService {

    private final RestTemplate restTemplate;

    private final static String API_URL = "http://localhost:8080/api/v1";
    private final static String CATEGORY = "/categories";
    private final static String PRODUCT = "/products";
    private final static String SEARCH = "New";


    @PostConstruct
    public void init() {

        // Получение списка всех продуктов и категорий и вывод информации о них.
        Category[] categories = restTemplate.getForObject(API_URL + CATEGORY, Category[].class);
        if (categories != null && categories.length > 0) {
            log.info("Получен список категорий: ");
            for (Category category : categories) {
                log.info("{}", category);
            }
        }

        // Реализовать пагинацию для списка продуктов.
        ResponseEntity<JacksonPage<Product>> responseEntity = restTemplate.exchange(API_URL + PRODUCT,
            HttpMethod.GET, null, new ParameterizedTypeReference<>() {
            });
        int pageSize = responseEntity.getBody().getPageable().getPageSize();
        int pageNumber = responseEntity.getBody().getPageable().getPageNumber();
        log.info("Получен постраничный список продуктов (количество продуктов на странице = {}), страница №{}: ", pageSize, pageNumber);
        for (Product product : responseEntity.getBody()) {
            log.info("{}", product);
        }

        responseEntity = restTemplate.exchange(API_URL + PRODUCT + "?page=1&size=2",
            HttpMethod.GET, null, new ParameterizedTypeReference<>() {
            });
        pageSize = responseEntity.getBody().getPageable().getPageSize();
        pageNumber = responseEntity.getBody().getPageable().getPageNumber();
        log.info("Получен постраничный список продуктов (количество продуктов на странице = {}), страница №{}: ", pageSize, pageNumber);
        for (Product product : responseEntity.getBody()) {
            log.info("{}", product);
        }

        // Возможность добавления нового продукта с указанием категории.
        Category category = restTemplate.getForObject(API_URL + CATEGORY + "/" + 1L, Category.class);

        Product newProduct = Product.builder()
            .name("New Product")
            .description("New Desc")
            .price(new BigDecimal("351.09"))
            .category(category)
            .build();

        Product createdProduct = restTemplate.postForObject(API_URL + PRODUCT, newProduct, Product.class);
        log.info("Создан новый продукт {}", createdProduct);

        //Добавить функциональность для поиска продуктов по их названию или описанию
        responseEntity = restTemplate.exchange(API_URL + PRODUCT + "?name=" + SEARCH,
            HttpMethod.GET, null, new ParameterizedTypeReference<>() {
            });
        pageSize = responseEntity.getBody().getPageable().getPageSize();
        pageNumber = responseEntity.getBody().getPageable().getPageNumber();
        log.info("Получен постраничный список продуктов содержащей в названии {} (количество продуктов на странице = {}), страница №{}: ",
            SEARCH, pageSize, pageNumber);
        for (Product product : responseEntity.getBody()) {
            log.info("{}", product);
        }

        //Возможность удаления продукта.
        newProduct = restTemplate.getForObject(API_URL + PRODUCT + "/" + createdProduct.getId(), Product.class);
        log.info("Удаляем продукт : {}", newProduct);
        restTemplate.delete(API_URL + PRODUCT + "/" + createdProduct.getId());
        log.info("Убеждаемся, что продукт [id={}] удален", createdProduct.getId());
        newProduct = restTemplate.getForObject(API_URL + PRODUCT + "/" + createdProduct.getId(), Product.class);
        log.info("Product delete: {}", newProduct);

        //Возможность обновления информации о продукте.
        newProduct = restTemplate.getForObject(API_URL + PRODUCT + "/" + 1L, Product.class);
        log.info("Получаем информацию о продукте: {}", newProduct);
        newProduct.setName("Новое название");
        newProduct.setDescription("Новое описание");
        newProduct.setPrice(new BigDecimal("999.99"));
        log.info("Обновляем продукт : {}", newProduct);
        restTemplate.put(API_URL + PRODUCT + "/" + 1L, newProduct, Product.class);
        newProduct = restTemplate.getForObject(API_URL + PRODUCT + "/" + 1L, Product.class);
        log.info("Получаем информацию об обновленном продукте: {}", newProduct);

    }
}
