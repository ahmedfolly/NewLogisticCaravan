package com.example.logisticcavan.caravan.domain.useCase;


import com.example.logisticcavan.caravan.domain.CaravanRepository;
import com.example.logisticcavan.products.getproducts.domain.Product;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class GetCaravanProductsUseCase {


    private  CaravanRepository caravanRepository;

    @Inject
    public GetCaravanProductsUseCase(CaravanRepository caravanRepository) {
        this.caravanRepository = caravanRepository;
    }

    public CompletableFuture<List<Product>> execute(){
        return caravanRepository.getCaravanProducts();
    }
}
