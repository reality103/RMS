package dal.implement;

import context.DBContext;
import dal.interfaces.ICartDAO;
import dal.interfaces.IDishDAO;
import dal.interfaces.ITableDAO;
import dal.interfaces.IUserDAO;
import models.Cart;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.DiningTable;
import models.Dish;
import models.User;

public class CartDAOImpl extends DBContext implements ICartDAO {

    Connection connection = null;
    DBContext dBContext = new DBContext();

    @Override
    public Cart checkCart(int user_id, int product_id, int table) {
        try {
            connection = dBContext.openConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CartDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sql = "select * from Cart where [customer_id] = ? and dish_id = ? and table_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, user_id);
            st.setInt(2, product_id);
            st.setInt(3, table);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Cart c = new Cart();
                c.setCustomer_id(rs.getInt(1));
                c.setDish_id(rs.getInt(2));
                c.setQuantity(rs.getInt(3));
                c.setTotal_cost(rs.getInt(4));
                c.setTable_id(rs.getInt(5));
                return c;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }
        return null;
    }

    @Override
    public int getTotalItemInCart(int userId) {
        int total = 0;
        try {
            connection = dBContext.openConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CartDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sql = "select sum(quantity) as totalItem from Cart where customer_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                total = rs.getInt("totalItem");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }
        return total;
    }

    @Override
    public void addToCart(int product_id, int tableId, int quantity,
            int total_cost, int user_id) {
        try {
            connection = dBContext.openConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CartDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String sql = "insert into Cart(customer_id, dish_id,quantity,total_cost, table_id) values(?,?,?,?,?);";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, user_id);
            st.setInt(2, product_id);
            st.setInt(3, quantity);
            st.setInt(4, total_cost);
            st.setInt(5, tableId);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CartDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }
    }

    @Override
    public void updateQuantityProductInCart(int product_id, int table_id,
            int quantity, int total_cost, int user_id) {
        try {
            connection = dBContext.openConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CartDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("pid: " + product_id + " tableId:" + table_id + " quantity: " + quantity + " total_cost: " + total_cost + " c: " + user_id);
        try {
            String sql = "UPDATE [dbo].[Cart] \n"
                    + "   SET [quantity] = ? \n"
                    + "      ,[total_cost] = ? \n"
                    + " WHERE [dish_id] = ? and [customer_id] = ? and table_id=?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, quantity);
            st.setInt(2, total_cost);
            st.setInt(3, product_id);
            st.setInt(4, user_id);
            st.setInt(5, table_id);

            st.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(CartDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }
    }

    @Override
    public List<Cart> getCartInfoByUserId(int user_id, String isTakeaway) {
        try {
            connection = dBContext.openConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CartDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Cart> list = new ArrayList<>();
        String sql = "select * from Cart where [customer_id] = ?";
        if (isTakeaway == null || isTakeaway.equals("true")) {
            sql += " and table_id = -1";
        } else {
            sql += " and table_id <> -1";
        }
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, user_id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Cart c = new Cart();
                c.setCustomer_id(rs.getInt(1));
                c.setDish_id(rs.getInt(2));
                c.setQuantity(rs.getInt(3));
                c.setTotal_cost(rs.getInt(4));
                c.setTable_id(rs.getInt(5));

                IUserDAO ud = new UserDAOImpl();
                User u = ud.getUserById(user_id);
                c.setCustomer(u);
                IDishDAO id = new DishDAOImpl();
                Dish d = id.getDishByID(rs.getInt(2));
                c.setDish(d);
                if (rs.getInt(5) != -1) {
                    ITableDAO td = new TableDAOImpl();
                    DiningTable t = td.getTableById(rs.getInt(5));
                    c.setTable(t);
                }
                list.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }
        return list;
    }

    @Override
    public List<Cart> getCartInfoByUserId(int user_id) {
        try {
            connection = dBContext.openConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CartDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Cart> list = new ArrayList<>();
        String sql = "select * from Cart where [customer_id] = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, user_id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Cart c = new Cart();
                c.setCustomer_id(rs.getInt(1));
                c.setDish_id(rs.getInt(2));
                c.setQuantity(rs.getInt(3));
                c.setTotal_cost(rs.getInt(4));
                c.setTable_id(rs.getInt(5));

                IUserDAO ud = new UserDAOImpl();
                User u = ud.getUserById(user_id);
                c.setCustomer(u);
                IDishDAO id = new DishDAOImpl();
                Dish d = id.getDishByID(rs.getInt(2));
                c.setDish(d);
                if (rs.getInt(5) != -1) {
                    ITableDAO td = new TableDAOImpl();
                    DiningTable t = td.getTableById(rs.getInt(5));
                    c.setTable(t);
                }
                list.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }
        return list;
    }

    @Override
    public void deleteCart(int product_id, int user_id) {
        try {
            connection = dBContext.openConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CartDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String sql = "DELETE FROM [dbo].[Cart]\n"
                    + "      WHERE [dish_id] = ? and [customer_id] = ? ";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, product_id);
            st.setInt(2, user_id);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CartDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }
    }

    @Override
    public Cart getCartByUserIdAndDishId(int userId, int product_id) {
        try {
            connection = dBContext.openConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CartDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sql = "select * from Cart where [customer_id] = ? and dish_id=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            st.setInt(2, product_id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Cart c = new Cart();
                c.setCustomer_id(rs.getInt(1));
                c.setDish_id(rs.getInt(2));
                c.setQuantity(rs.getInt(3));
                c.setTotal_cost(rs.getInt(4));
                c.setTable_id(rs.getInt(5));

                IUserDAO ud = new UserDAOImpl();
                User u = ud.getUserById(userId);
                c.setCustomer(u);
                IDishDAO id = new DishDAOImpl();
                Dish d = id.getDishByID(rs.getInt(2));
                c.setDish(d);
                if (rs.getInt(5) != -1) {
                    ITableDAO td = new TableDAOImpl();
                    DiningTable t = td.getTableById(rs.getInt(5));
                    c.setTable(t);
                }
                return c;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }
        return null;
    }

    @Override
    public void deleteCartByUserId(int user_id, int table) {
        try {
            connection = dBContext.openConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CartDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String sql = "DELETE FROM [dbo].[Cart]\n"
                    + "      WHERE [customer_id] = ? and table_id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, user_id);
            st.setInt(2, table);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CartDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }
    }

    @Override
    public Cart checkCart(int user_id) {
        try {
            connection = dBContext.openConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CartDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sql = "select top 1* from Cart where [customer_id] = ? and table_id <> -1";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, user_id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Cart c = new Cart();
                c.setCustomer_id(rs.getInt(1));
                c.setDish_id(rs.getInt(2));
                c.setQuantity(rs.getInt(3));
                c.setTotal_cost(rs.getInt(4));
                c.setTable_id(rs.getInt(5));
                ITableDAO td = new TableDAOImpl();
                DiningTable t = td.getTableById(rs.getInt(5));
                c.setTable(t);
                return c;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }
        return null;
    }

}
