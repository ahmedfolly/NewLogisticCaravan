package com.example.logisticcavan.cart.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.logisticcavan.cart.domain.models.CartItem;
import com.example.logisticcavan.common.utils.MyResult;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

@Dao
public interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CartItem cartItem);
    @Query("SELECT * FROM cart_table")
    Observable<List<CartItem>> getAllCartItems();
    @Query("DELETE FROM cart_table")
    void deleteAll();
    @Query("DELETE FROM cart_table WHERE id = :id")
    void deleteItemById(int id);
    @Query("UPDATE cart_table SET quantity = :quantity WHERE id = :id")
    void updateQuantity(int id, int quantity);
    @Query("UPDATE cart_table SET price = :price WHERE id = :id")
    void updatePrice(int id, double price);
    @Query("SELECT SUM(price) FROM cart_table")
    Observable<Double> getTotalCartPrice();


}
