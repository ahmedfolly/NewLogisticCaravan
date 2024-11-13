package com.example.logisticcavan.updateStatusCaravanProduct.domain;

import com.example.logisticcavan.updateStatusCaravanProduct.data.UpdateStatusCaravanRepo;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class UpdateStatusCaravanProductUseCase {

    private final UpdateStatusCaravanRepo updateStatusCaravanProduct;

    @Inject
    public UpdateStatusCaravanProductUseCase(UpdateStatusCaravanRepo updateStatusCaravanProduct) {
        this.updateStatusCaravanProduct = updateStatusCaravanProduct;
    }


    public CompletableFuture<Void> execute(String productID, long timeStamp) {
      return   updateStatusCaravanProduct.updateStatusCaravanProduct(productID,timeStamp);
    }
}
