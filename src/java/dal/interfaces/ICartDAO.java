/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dal.interfaces;

import java.util.List;
import models.Cart;

public interface ICartDAO {

    public Cart checkCart(int user_id, int product_id, int table);

    public Cart checkCart(int user_id);

    public void addToCart(int product_id, int tableId, int quantity, int total_cost, int user_id);

    public void updateQuantityProductInCart(int product_id, int table_id, int quantity, int total_cost, int user_id);

    public int getTotalItemInCart(int userId);

    public List<Cart> getCartInfoByUserId(int user_id, String isString);

    public void deleteCart(int product_id, int user_id);

    public Cart getCartByUserIdAndDishId(int userId, int product_id);

    public void deleteCartByUserId(int user_id, int table);

    List<Cart> getCartInfoByUserId(int user_id);
}
