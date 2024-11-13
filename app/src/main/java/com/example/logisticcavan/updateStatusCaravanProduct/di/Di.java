package com.example.logisticcavan.updateStatusCaravanProduct.di;

import com.example.logisticcavan.updateStatusCaravanProduct.data.UpdateStatusCaravanRepo;
import com.example.logisticcavan.updateStatusCaravanProduct.domain.UpdateStatusCaravanProductUseCase;
import com.google.firebase.firestore.FirebaseFirestore;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class Di {

@Provides
    UpdateStatusCaravanProductUseCase provideUpdateStatusCaravanProductUseCase
        (UpdateStatusCaravanRepo updateStatusCaravanProduct) {
    return new UpdateStatusCaravanProductUseCase(updateStatusCaravanProduct);
}

    @Provides
    UpdateStatusCaravanRepo provideUpdateStatusCaravanRepo(FirebaseFirestore firestore) {
        return new UpdateStatusCaravanRepo(firestore);
    }

}




