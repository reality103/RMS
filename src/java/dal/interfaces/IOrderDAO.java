/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dal.interfaces;

import java.util.List;
import models.Chart;
import models.Order;

public interface IOrderDAO {

    public Order checkDishOrderByUser(int userId, int product_id);

    public int createNewOrder(int sum, String fullname, String email,
            String phone, String address, int user_id, String note, String cod, int table_id);

    public Order getCurrentOrder(int userId, String afalse);

    public List<Order> getAllOrder();

    public List<Order> pagingOrder(int index, int RECORD_PER_PAGE,
            String key, String statusStr, int take_away);

    public int countOrderByStatus(String key, String statusStr, int take_away);

    public Order getOrderById(int orderId);

    public Boolean handleOrder(String status, int order_id);

    public List<Order> getTableOrder();

    public Order getOrderById(int user_id, int table);

    public List<Order> getOrdersByTableId(int table);

    public Order getOrderByTalbeAndUser(int user_id, int table_id);

    public List<Chart> getChartRevenueArea(String start, int numberOfDay);

    public int gettotalOrderByStatus(String status, String start, int numberOfDay);
}
