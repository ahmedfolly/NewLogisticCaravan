package com.example.logisticcavan.products.getproducts;

import com.example.logisticcavan.products.getproducts.data.GetAllProductsRepoImp;
import com.example.logisticcavan.products.getproducts.data.GetCategoryProductsRepoImp;
import com.example.logisticcavan.products.getproducts.domain.GetCategoryProductsRepo;
import com.example.logisticcavan.products.getproducts.domain.GetCategoryProductsUseCase;
import com.example.logisticcavan.products.getproducts.domain.GetProductsRepo;
import com.example.logisticcavan.products.getproducts.domain.GetProductsUseCase;
import com.google.firebase.firestore.FirebaseFirestore;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class Di {
    @Provides
    GetProductsUseCase provideGetProductsUseCase(GetProductsRepo repo){
        return new GetProductsUseCase(repo);
    }
    @Provides
    GetProductsRepo providesGetProductsRepo(FirebaseFirestore firestore){
        return new GetAllProductsRepoImp(firestore);
    }
    @Provides
    GetCategoryProductsUseCase providesGetCategoryProductsUseCase(GetCategoryProductsRepo repo){
        return new GetCategoryProductsUseCase(repo);
    }
    @Provides
    GetCategoryProductsRepo providesGetCategoryProductsRepo(FirebaseFirestore firestore){
        return new GetCategoryProductsRepoImp(firestore);
    }
}
