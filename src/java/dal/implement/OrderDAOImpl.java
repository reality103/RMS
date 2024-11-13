/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.implement;

import context.DBContext;
import dal.interfaces.IOrderDAO;
import dal.interfaces.ITableDAO;
import java.sql.Connection;
import models.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Chart;
import models.DiningTable;

public class OrderDAOImpl extends context.DBContext implements IOrderDAO {

    Connection connection = null;
    DBContext dBContext = new DBContext();

    @Override
    public Order checkDishOrderByUser(int userId, int product_id) {
        try {
            connection = dBContext.openConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(OrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sql = "select * from [Order] o join [OrderDetail] od on o.id = od.order_id \n"
                + "where o.customer_id = ? and od.dish_id = ? and o.status <> 'completed'";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            st.setInt(2, product_id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Order c = new Order();
                c.setId(rs.getInt(1));
                return c;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }
        return null;
    }

    @Override
    public int createNewOrder(int sum, String fullname, String email, String phone, String address, int user_id, String note, String cod, int table_id) {
        try {
            connection = dBContext.openConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(OrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sql = "INSERT INTO [dbo].[Order]\n"
                + "           (order_datetime \n"
                + "           ,[total_cost]\n"
                + "           ,[customer_name]\n"
                + "           ,[email]\n"
                + "           ,[phone]\n"
                + "           ,[address]\n"
                + "           ,[customer_id]\n"
                + "           ,[note], payment_method, table_id, status)\n"
                + "     VALUES\n"
                + "           (GETDATE(),?,?,?,?,?,?,?,?,?, 'pending') ";

        try {
            PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, sum);
            st.setString(2, fullname);
            st.setString(3, email);
            st.setString(4, phone);
            st.setString(5, address);
            st.setInt(6, user_id);
            st.setString(7, note);
            st.setString(8, cod);
            st.setInt(9, table_id);

            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }
        return 0;
    }

    @Override
    public Order getCurrentOrder(int userId, String afalse) {
        try {
            connection = dBContext.openConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(OrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sql = "select * from [Order] "
                + "where customer_id = ? and  (status = 'pending' or status = 'processing') ";

        if (afalse == null || afalse.equals("true")) {
            sql += " and table_id = -1";
        } else {
            sql += " and table_id <> -1";
        }
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Order c = new Order();
                c.setId(rs.getInt(1));
                if (rs.getInt(4) != -1) {
                    ITableDAO td = new TableDAOImpl();
                    DiningTable t = td.getTableById(rs.getInt(4));
                    c.setTable(t);
                }
                return c;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }
        return null;
    }

    @Override
    public List<Order> getAllOrder() {
        try {
            connection = dBContext.openConnection();
        } catch (ClassNotFoundException | SQLException e) {
        }
        List<Order> list = new ArrayList();
        String sql = "select  * from [Order] where status <> 'completed'";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt(1));
                o.setCustomer_id(rs.getInt(2));
                o.setCustomer_name(rs.getString(3));
                o.setTable_id(rs.getInt(4));
                o.setOrder_datetime(rs.getDate(5));
                o.setTotal_cost(rs.getInt(6));
                o.setEmail(rs.getString(7));
                o.setPhone(rs.getString(8));
                o.setAddress(rs.getString(9));
                o.setNote(rs.getString(10));
                o.setPayment_method(rs.getString(11));
                o.setStatus(rs.getString(12));
                list.add(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }
        return list;
    }

    @Override
    public List<Order> pagingOrder(int index, int RECORD_PER_PAGE, String key, String statusStr, int take_away) {
        try {
            connection = dBContext.openConnection();
        } catch (ClassNotFoundException | SQLException e) {

        }
        List<Order> list = new ArrayList();
        String sql = "select * from [Order] where status " + statusStr + " and ((address like N'%" + key + "%') or"
                + " (customer_name like N'%" + key + "%') or (phone like N'%" + key + "%'))";

        if (take_away == 1) {
            sql += " and table_id = -1";
        } else {
            sql += " and table_id <> -1";
        }
        sql += " order by id  offset ? rows fetch next ? rows only;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, (index - 1) * RECORD_PER_PAGE);
            ps.setInt(2, RECORD_PER_PAGE);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt(1));
                o.setCustomer_id(rs.getInt(2));
                o.setCustomer_name(rs.getString(3));
                o.setTable_id(rs.getInt(4));
                o.setOrder_datetime(rs.getDate(5));
                o.setTotal_cost(rs.getInt(6));
                o.setEmail(rs.getString(7));
                o.setPhone(rs.getString(8));
                o.setAddress(rs.getString(9));
                o.setNote(rs.getString(10));
                o.setPayment_method(rs.getString(11));
                o.setStatus(rs.getString(12));
                list.add(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }
        return list;
    }

    @Override
    public int countOrderByStatus(String key, String statusStr, int take_away) {
        try {
            connection = dBContext.openConnection();
        } catch (ClassNotFoundException | SQLException e) {
        }
        try {

            String sql = "select count(*) from [Order] where status " + statusStr + " and ((address like N'%" + key + "%') or"
                    + " (customer_name like N'%" + key + "%') or (phone like N'%" + key + "%'))";
            if (take_away == 1) {
                sql += " and table_id = -1";
            } else {
                sql += " and table_id <> -1";
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }
        return -1;
    }

    @Override
    public Order getOrderById(int orderId) {
        try {
            connection = dBContext.openConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(OrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            String sql = "select * from [Order] where id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, orderId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt(1));
                o.setCustomer_id(rs.getInt(2));
                o.setCustomer_name(rs.getString(3));
                o.setTable_id(rs.getInt(4));
                o.setOrder_datetime(rs.getDate(5));
                o.setTotal_cost(rs.getInt(6));
                o.setEmail(rs.getString(7));
                o.setPhone(rs.getString(8));
                o.setAddress(rs.getString(9));
                o.setNote(rs.getString(10));
                o.setPayment_method(rs.getString(11));
                o.setStatus(rs.getString(12));
                return o;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }
        return null;
    }

    @Override
    public Boolean handleOrder(String status, int order_id) {
        String sqlUpdateStatus = "UPDATE [Order] SET status = ? WHERE id = ?";
        try {
            connection = dBContext.openConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(OrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        try {
            connection.setAutoCommit(false); // Bắt đầu transaction
            // Luôn cập nhật status bất kể là "completed" hay không
            PreparedStatement st = connection.prepareStatement(sqlUpdateStatus);
            st.setString(1, status);
            st.setInt(2, order_id);
            st.executeUpdate();

            connection.commit(); // Hoàn tất transaction
            return true;

        } catch (SQLException ex) {
            try {
                connection.rollback(); // Rollback nếu có lỗi
            } catch (SQLException e) {
                Logger.getLogger(OrderDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }
            return false;
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
                Logger.getLogger(OrderDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    @Override
    public List<Order> getTableOrder() {
        try {
            connection = dBContext.openConnection();
        } catch (ClassNotFoundException | SQLException e) {
        }
        List<Order> list = new ArrayList();
        String sql = "select  * from [Order] where table_id != -1 and status <> 'completed'";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt(1));
                o.setCustomer_id(rs.getInt(2));
                o.setCustomer_name(rs.getString(3));
                o.setTable_id(rs.getInt(4));
                o.setOrder_datetime(rs.getDate(5));
                o.setTotal_cost(rs.getInt(6));
                o.setEmail(rs.getString(7));
                o.setPhone(rs.getString(8));
                o.setAddress(rs.getString(9));
                o.setNote(rs.getString(10));
                o.setPayment_method(rs.getString(11));
                o.setStatus(rs.getString(12));
                list.add(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }
        return list;
    }

    @Override
    public Order getOrderById(int user_id, int table) {
        try {
            connection = dBContext.openConnection();
        } catch (ClassNotFoundException | SQLException e) {
        }

        String sql = "select  * from [Order] where customer_id = ? and  table_id != ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, user_id);
            ps.setInt(2, table);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt(1));
                o.setCustomer_id(rs.getInt(2));
                o.setCustomer_name(rs.getString(3));
                o.setTable_id(rs.getInt(4));
                o.setOrder_datetime(rs.getDate(5));
                o.setTotal_cost(rs.getInt(6));
                o.setEmail(rs.getString(7));
                o.setPhone(rs.getString(8));
                o.setAddress(rs.getString(9));
                o.setNote(rs.getString(10));
                o.setPayment_method(rs.getString(11));
                o.setStatus(rs.getString(12));
                return o;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }
        return null;
    }

    @Override
    public List<Order> getOrdersByTableId(int table) {
        try {
            connection = dBContext.openConnection();
        } catch (ClassNotFoundException | SQLException e) {
        }
        List<Order> list = new ArrayList();
        String sql = "select  * from [Order] where table_id = ? and status <> 'completed' ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, table);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt(1));
                o.setCustomer_id(rs.getInt(2));
                o.setCustomer_name(rs.getString(3));
                o.setTable_id(rs.getInt(4));
                o.setOrder_datetime(rs.getDate(5));
                o.setTotal_cost(rs.getInt(6));
                o.setEmail(rs.getString(7));
                o.setPhone(rs.getString(8));
                o.setAddress(rs.getString(9));
                o.setNote(rs.getString(10));
                o.setPayment_method(rs.getString(11));
                o.setStatus(rs.getString(12));
                list.add(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }
        return list;
    }

    @Override
    public Order getOrderByTalbeAndUser(int user_id, int table_id) {
        try {
            connection = dBContext.openConnection();
        } catch (ClassNotFoundException | SQLException e) {
        }

        String sql = "select  * from [Order] where customer_id = ? and  table_id = ? and status <> 'completed'";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, user_id);
            ps.setInt(2, table_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt(1));
                o.setCustomer_id(rs.getInt(2));
                o.setCustomer_name(rs.getString(3));
                o.setTable_id(rs.getInt(4));
                o.setOrder_datetime(rs.getDate(5));
                o.setTotal_cost(rs.getInt(6));
                o.setEmail(rs.getString(7));
                o.setPhone(rs.getString(8));
                o.setAddress(rs.getString(9));
                o.setNote(rs.getString(10));
                o.setPayment_method(rs.getString(11));
                o.setStatus(rs.getString(12));
                return o;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }
        return null;
    }

    @Override
    public List<Chart> getChartRevenueArea(String start, int numberOfDay) {
        try {
            connection = dBContext.openConnection();
        } catch (ClassNotFoundException | SQLException e) {

        }
        List<Chart> list = new ArrayList<>();
        for (int i = 0; i < numberOfDay; i++) {
            int value = 0;
            try {
                String sql = "SELECT SUM(total_cost)\n"
                        + "FROM (\n"
                        + "  SELECT *\n"
                        + "  FROM [Order]\n"
                        + "  WHERE status = 'completed' \n"
                        + ") AS orders_with_status\n"
                        + "WHERE order_datetime BETWEEN ? AND DATEADD(DAY,?,?)";
                PreparedStatement st = connection.prepareStatement(sql);
                st.setString(1, start);
                st.setInt(2, i);
                st.setString(3, start);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    value = rs.getInt(1);
                }
                sql = "select  DATEADD(DAY, ?, ?)";
                st = connection.prepareStatement(sql);
                st.setInt(1, i);
                st.setString(2, start);
                rs = st.executeQuery();
                if (rs.next()) {
                    Chart c = new Chart();
                    c.setDate(rs.getDate(1));
                    c.setValue(value);
                    list.add(c);
                }
            } catch (SQLException ex) {
                Logger.getLogger(OrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            dBContext.closeConnection(connection);
        } catch (SQLException e) {
        }

        return list;
    }

    @Override
    public int gettotalOrderByStatus(String status, String start, int numberOfDay) {
        try {
            connection = dBContext.openConnection();
        } catch (ClassNotFoundException | SQLException e) {
        }
        int sum = 0;
        for (int i = 0; i < numberOfDay; i++) {
            try {
                String sql = "select count(id) from [Order] where status = ? and order_datetime"
                        + " between ? and DATEADD(DAY, ?,?)";
                PreparedStatement st = connection.prepareStatement(sql);
                st.setString(1, status);
                st.setString(2, start);
                st.setInt(3, i);
                st.setString(4, start);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    sum += rs.getInt(1);
                }
            } catch (SQLException ex) {
                Logger.getLogger(OrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            dBContext.closeConnection(connection);
        } catch (SQLException e) {
        }
        return sum;
    }

}
