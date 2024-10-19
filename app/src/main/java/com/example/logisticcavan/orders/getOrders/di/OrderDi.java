package com.example.logisticcavan.orders.getOrders.di;

import com.example.logisticcavan.orders.getOrders.courier.presentaion.GetCourierOrdersViewModel;
import com.example.logisticcavan.orders.getOrders.data.CourierOrderRepositoryImpl;
import com.example.logisticcavan.orders.getOrders.domain.GetAllOrderUseCaseCase;
import com.example.logisticcavan.orders.getOrders.domain.GetCourierOrdersBasedStatusUseCase;
import com.example.logisticcavan.orders.getOrders.domain.OrderRepository;
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
    public GetCourierOrdersBasedStatusUseCase provideOrderGetCourierOrdersBaseStatusUseCase(OrderRepository orderRepository) {
        return new GetCourierOrdersBasedStatusUseCase(orderRepository);
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
    public GetCourierOrdersViewModel provideGetCourierOrdersViewModel(GetCourierOrdersBasedStatusUseCase getCourierOrdersBasedStatusUseCase,GetAllOrderUseCaseCase getAllOrderUseCaseCase){
        return new GetCourierOrdersViewModel(getCourierOrdersBasedStatusUseCase,getAllOrderUseCaseCase);
    }


}
