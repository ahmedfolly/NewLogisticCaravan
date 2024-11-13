package com.example.logisticcavan.caravan.domain;

import com.example.logisticcavan.products.getproducts.domain.Product;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CaravanRepository {

    CompletableFuture<List<Product>> getCaravanProducts();

}
