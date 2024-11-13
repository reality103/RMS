/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dal.interfaces;

import java.util.List;
import models.Cart;
import models.OrderDetail;

public interface IOrderDetailDAO {

    public void addCartToOrder(List<Cart> listCart, int order_id);

    public List<OrderDetail> getOrderDetailByOrder(int orderId);

}
