package com.example.logisticcavan.rating.addandgetrating.domain.usecase;

import com.example.logisticcavan.rating.addandgetrating.domain.repo.UpdateTotalRateVarsRepo;

public class UpdateRateVarsUseCase {
    private final UpdateTotalRateVarsRepo updateTotalRateVarsRepo;
    public UpdateRateVarsUseCase(UpdateTotalRateVarsRepo updateTotalRateVarsRepo) {
        this.updateTotalRateVarsRepo = updateTotalRateVarsRepo;
    }

    public void updateRateVars(String restaurantId,int totalStars){
        updateTotalRateVarsRepo.updateRateVars(restaurantId,totalStars);
    }
}
