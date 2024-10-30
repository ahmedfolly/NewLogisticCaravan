package com.example.logisticcavan.rating;

import com.example.logisticcavan.rating.addandgetrating.data.AddRateRepoImp;
import com.example.logisticcavan.rating.addandgetrating.data.UpdateRateVarsRepoImp;
import com.example.logisticcavan.rating.addandgetrating.domain.repo.AddRateRepo;
import com.example.logisticcavan.rating.addandgetrating.domain.repo.UpdateTotalRateVarsRepo;
import com.example.logisticcavan.rating.addandgetrating.domain.usecase.AddRateUseCase;
import com.example.logisticcavan.rating.addandgetrating.domain.usecase.UpdateRateVarsUseCase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;

@Module
@InstallIn(ViewModelComponent.class)
public class AddRatingDI {
    @Provides
    public AddRateUseCase provideAddRateUseCase(AddRateRepo addRateRepo) {
        return new AddRateUseCase(addRateRepo);
    }
    @Provides
    public UpdateRateVarsUseCase provideUpdateRateVarsUseCase(UpdateTotalRateVarsRepo updateTotalRateVarsRepo) {
        return new UpdateRateVarsUseCase(updateTotalRateVarsRepo);
    }
    @Provides
    public AddRateRepo provideAddRateRepo(FirebaseFirestore firebaseFirestore, FirebaseAuth mAuth) {
        return new AddRateRepoImp(firebaseFirestore,mAuth);
    }
    @Provides
    public UpdateTotalRateVarsRepo provideUpdateTotalRateVarsRepo(FirebaseFirestore firebaseFirestore) {
        return new UpdateRateVarsRepoImp(firebaseFirestore);
    }
}
