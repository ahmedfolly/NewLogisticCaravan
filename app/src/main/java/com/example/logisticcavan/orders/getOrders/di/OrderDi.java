package com.example.logisticcavan.orders.getOrders.di;

import com.example.logisticcavan.orders.getOrders.courier.presentaion.GetCourierOrdersViewModel;
import com.example.logisticcavan.orders.getOrders.data.CourierOrderRepositoryImpl;
import com.example.logisticcavan.orders.getOrders.data.GetOrdersOfCurrUserRepoImp;
import com.example.logisticcavan.orders.getOrders.domain.GetAllOrderUseCaseCase;
import com.example.logisticcavan.orders.getOrders.domain.GetCourierOrdersBasedStatusUseCase;
import com.example.logisticcavan.orders.getOrders.domain.GetOrdersBasedBeach;
import com.example.logisticcavan.orders.getOrders.domain.GetOrdersIdsUseCase;
import com.example.logisticcavan.orders.getOrders.domain.GetOrdersOfCurrUser;
import com.example.logisticcavan.orders.getOrders.domain.GetOrdersOfCurrUserUseCase;
import com.example.logisticcavan.orders.getOrders.domain.OrderRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class OrderDi {

    @Provides
    public GetOrdersIdsUseCase provideGetOrdersIdsUseCase(GetOrdersOfCurrUser getOrdersOfCurrUser) {
        return new GetOrdersIdsUseCase(getOrdersOfCurrUser);
    }
    @Provides
    public GetOrdersOfCurrUserUseCase provideGetOrdersOfCurrUserUseCase(GetOrdersOfCurrUser getOrdersOfCurrUser) {
        return new GetOrdersOfCurrUserUseCase(getOrdersOfCurrUser);
    }
    @Provides
    public GetOrdersOfCurrUser provideGetOrdersOfCurrUser(FirebaseFirestore firebaseFireStore, FirebaseAuth mAuth) {
        return new GetOrdersOfCurrUserRepoImp(firebaseFireStore,mAuth);
    }
//    @Provides
//    public FirebaseAuth provideFirebaseAuth() {
//        return FirebaseAuth.getInstance();
//    }

    @Provides
    public GetCourierOrdersBasedStatusUseCase provideOrderGetCourierOrdersBaseStatusUseCase(OrderRepository orderRepository) {
        return new GetCourierOrdersBasedStatusUseCase(orderRepository);
    }
   @Provides
    public GetOrdersBasedBeach provideGetOrdersBasedBeach(OrderRepository orderRepository) {
        return new GetOrdersBasedBeach(orderRepository);
    }

    @Provides
    public GetAllOrderUseCaseCase provideGetAllOrderUseCaseCase(OrderRepository orderRepository) {
        return new GetAllOrderUseCaseCase(orderRepository);
    }




    @Provides
    @Singleton
    public OrderRepository provideOrderRepository(FirebaseFirestore firebaseFireStore) {
        return new CourierOrderRepositoryImpl(firebaseFireStore);
    }



    @Provides
    @Singleton
    public GetCourierOrdersViewModel provideGetCourierOrdersViewModel(GetCourierOrdersBasedStatusUseCase getCourierOrdersBasedStatusUseCase,GetAllOrderUseCaseCase getAllOrderUseCaseCase,GetOrdersBasedBeach getOrdersBasedBeach){
        return new GetCourierOrdersViewModel(getCourierOrdersBasedStatusUseCase,getAllOrderUseCaseCase,getOrdersBasedBeach);
    }


}
