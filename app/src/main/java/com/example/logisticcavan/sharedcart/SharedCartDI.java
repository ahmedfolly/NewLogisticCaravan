package com.example.logisticcavan.sharedcart;

import com.example.logisticcavan.sharedcart.data.AddToSharedCartRepoImp;
import com.example.logisticcavan.sharedcart.data.AddUserToSharedCartRepoImp;
import com.example.logisticcavan.sharedcart.data.DeleteSharedCartRepoImp;
import com.example.logisticcavan.sharedcart.data.DeleteSharedProductRepoImp;
import com.example.logisticcavan.sharedcart.data.GetSharedCartRepoImp;
import com.example.logisticcavan.sharedcart.data.GetSharedProductsRepoImp;
import com.example.logisticcavan.sharedcart.domain.repo.AddSharedCartItemRepo;
import com.example.logisticcavan.sharedcart.domain.repo.AddUserToSharedCartRepo;
import com.example.logisticcavan.sharedcart.domain.repo.DeleteSharedCartRepo;
import com.example.logisticcavan.sharedcart.domain.repo.DeleteSharedProductRepo;
import com.example.logisticcavan.sharedcart.domain.repo.GetSharedCartRepo;
import com.example.logisticcavan.sharedcart.domain.repo.GetSharedProductsRepo;
import com.example.logisticcavan.sharedcart.domain.usecases.AddToSharedCartUseCase;
import com.example.logisticcavan.sharedcart.domain.usecases.AddUserToSharedCartUseCase;
import com.example.logisticcavan.sharedcart.domain.usecases.DeleteSharedCartProductUseCase;
import com.example.logisticcavan.sharedcart.domain.usecases.DeleteSharedCartUseCase;
import com.example.logisticcavan.sharedcart.domain.usecases.GetSharedCartUseCase;
import com.example.logisticcavan.sharedcart.domain.usecases.GetSharedProductsUseCase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;

@Module
@InstallIn(ViewModelComponent.class)
public class SharedCartDI {

    @Provides
    public DeleteSharedCartUseCase provideDeleteSharedCartUseCase(DeleteSharedCartRepo deleteSharedCartRepo){
        return new DeleteSharedCartUseCase(deleteSharedCartRepo);
    }
    @Provides DeleteSharedCartRepo provideDeleteSharedCartRepository(FirebaseFirestore firestore){
        return new DeleteSharedCartRepoImp(firestore);
    }
    @Provides
    public DeleteSharedCartProductUseCase provideDeleteSharedCartProductUseCase(DeleteSharedProductRepo deleteSharedProductRepo){
        return new DeleteSharedCartProductUseCase(deleteSharedProductRepo);
    }
    @Provides DeleteSharedProductRepo provideDeleteSharedCartProductRepo(FirebaseFirestore firestore, FirebaseAuth firebaseAuth){
        return new DeleteSharedProductRepoImp(firestore,firebaseAuth);
    }
    @Provides
    public GetSharedCartUseCase provideGetSharedCartUseCase(GetSharedCartRepo getSharedCart) {
        return new GetSharedCartUseCase(getSharedCart);
    }
    @Provides
    public GetSharedCartRepo provideGetSharedCartRepository(FirebaseFirestore firebaseFirestore, FirebaseAuth firebaseAuth) {
        return new GetSharedCartRepoImp(firebaseFirestore, firebaseAuth);
    }

    @Provides
    public AddUserToSharedCartUseCase provideAddUserToSharedCartUseCase(AddUserToSharedCartRepo sharedCartRepository) {
        return new AddUserToSharedCartUseCase(sharedCartRepository);
    }
    @Provides
    public AddUserToSharedCartRepo provideAddUserToSharedCartRepository(FirebaseFirestore firebaseFirestore, FirebaseAuth firebaseAuth) {
        return new AddUserToSharedCartRepoImp(firebaseFirestore, firebaseAuth);
    }

    @Provides
    public GetSharedProductsUseCase provideGetSharedProductsUseCase(GetSharedProductsRepo sharedCartRepository) {
        return new GetSharedProductsUseCase(sharedCartRepository);
    }

    @Provides
    public GetSharedProductsRepo provideGetSharedProductsRepository(FirebaseFirestore firebaseFirestore, FirebaseAuth firebaseAuth) {
        return new GetSharedProductsRepoImp(firebaseFirestore, firebaseAuth);
    }

    @Provides
    public AddToSharedCartUseCase provideAddToSharedCartUseCase(AddSharedCartItemRepo sharedCartRepository) {
        return new AddToSharedCartUseCase(sharedCartRepository);
    }

    @Provides
    public AddSharedCartItemRepo provideSharedCartRepository(FirebaseFirestore firebaseFirestore, FirebaseAuth mAuth) {
        return new AddToSharedCartRepoImp(firebaseFirestore, mAuth);
    }

}
