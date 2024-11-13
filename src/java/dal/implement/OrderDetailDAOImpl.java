/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.implement;

import context.DBContext;
import dal.interfaces.IOrderDetailDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Cart;
import models.OrderDetail;

public class OrderDetailDAOImpl extends DBContext implements IOrderDetailDAO {

    Connection connection = null;
    DBContext dBContext = new DBContext();

    @Override
    public void addCartToOrder(List<Cart> listCart, int order_id) {
        try {
            connection = dBContext.openConnection();

            for (Cart cart : listCart) {
                // Step 1: Check if the record exists
                String checkSql = "SELECT quantity, price FROM [dbo].[OrderDetail] WHERE order_id = ? AND dish_id = ?";
                PreparedStatement checkSt = connection.prepareStatement(checkSql);
                checkSt.setInt(1, order_id);
                checkSt.setInt(2, cart.getDish_id());

                ResultSet rs = checkSt.executeQuery();

                if (rs.next()) {
                    // Record exists, so update the quantity and total_cost
                    int existingQuantity = rs.getInt("quantity");
                    int existingTotalCost = rs.getInt("price");

                    int newQuantity = existingQuantity + cart.getQuantity();
                    int newTotalCost = existingTotalCost + cart.getTotal_cost();

                    String updateSql = "UPDATE [dbo].[OrderDetail] SET quantity = ?, price = ? WHERE order_id = ? AND dish_id = ?";
                    PreparedStatement updateSt = connection.prepareStatement(updateSql);
                    updateSt.setInt(1, newQuantity);
                    updateSt.setInt(2, newTotalCost);
                    updateSt.setInt(3, order_id);
                    updateSt.setInt(4, cart.getDish_id());

                    updateSt.executeUpdate();
                    updateSt.close();

                } else {
                    // Record does not exist, so insert a new one
                    String insertSql = "INSERT INTO [dbo].[OrderDetail] (order_id, dish_id, quantity, price) VALUES (?,?,?,?)";
                    PreparedStatement insertSt = connection.prepareStatement(insertSql);

                    insertSt.setInt(1, order_id);
                    insertSt.setInt(2, cart.getDish_id());
                    insertSt.setInt(3, cart.getQuantity());
                    insertSt.setInt(4, cart.getTotal_cost());

                    insertSt.executeUpdate();
                    insertSt.close();
                }
                rs.close();
                checkSt.close();
            }
            // Step 2: Calculate the total cost for the order
            String totalCostSql = "SELECT SUM(price) AS totalCost FROM [dbo].[OrderDetail] WHERE order_id = ?";
            PreparedStatement totalCostSt = connection.prepareStatement(totalCostSql);
            totalCostSt.setInt(1, order_id);

            ResultSet totalCostRs = totalCostSt.executeQuery();
            if (totalCostRs.next()) {
                int totalCost = totalCostRs.getInt("totalCost");

                // Step 3: Update the total_cost in the Order table
                String updateOrderSql = "UPDATE [dbo].[Order] SET total_cost = ? WHERE id = ?";
                PreparedStatement updateOrderSt = connection.prepareStatement(updateOrderSql);
                updateOrderSt.setInt(1, totalCost);
                updateOrderSt.setInt(2, order_id);

                updateOrderSt.executeUpdate();
                updateOrderSt.close();
            }

            totalCostRs.close();
            totalCostSt.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(OrderDetailDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
                Logger.getLogger(OrderDetailDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    @Override
    public List<OrderDetail> getOrderDetailByOrder(int orderId) {

        try {
            connection = dBContext.openConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(OrderDetailDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<OrderDetail> list = new ArrayList<>();
        try {
            String sql = "select * from OrderDetail where order_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderDetail od = new OrderDetail();
                od.setOrder_id(rs.getInt(1));
                od.setDish_id(rs.getInt(2));
                od.setQuantity(rs.getInt(3));
                od.setPrice(rs.getInt(4));
                list.add(od);
            }
        } catch (SQLException ex) {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }
        return list;
    }

}
