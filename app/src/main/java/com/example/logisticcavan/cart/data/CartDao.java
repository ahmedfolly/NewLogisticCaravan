package com.example.logisticcavan.cart.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.logisticcavan.cart.domain.models.CartItem;
import com.example.logisticcavan.common.utils.MyResult;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insert(CartItem cartItem);

    @Query("SELECT * FROM cart_table WHERE productName = :name")
    Single<CartItem> getCartItemByName(String name);

    @Query("SELECT * FROM cart_table")
    Observable<List<CartItem>> getAllCartItems();

    @Query("DELETE FROM cart_table")
    Completable deleteAll();

    @Query("DELETE FROM cart_table WHERE id = :id")
    void deleteItemById(int id);

    @Query("UPDATE cart_table SET quantity = :quantity , price = :price WHERE id = :id")
    void updateQuantity(int id, int quantity,double price);

    @Query("UPDATE cart_table SET price = :price WHERE id = :id")
    void updatePrice(int id, double price);

    @Query("SELECT SUM(price) FROM cart_table")
    Double getTotalCartPrice();

    @Query("SELECT COUNT(*) > 0 FROM cart_table WHERE restaurantId = :restaurantId")
    Single<Boolean> getRestaurantId(String restaurantId);

    @Query("SELECT COUNT(*) FROM cart_table")
    Single<Integer> getCartItemsCount();
}
