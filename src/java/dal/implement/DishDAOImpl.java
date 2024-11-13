/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.implement;

import context.DBContext;
import dal.interfaces.IDishDAO;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import models.Dish;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Image;
import java.sql.Statement;

public class DishDAOImpl extends context.DBContext implements IDishDAO {

    Connection connection = null;
    DBContext dBContext = new DBContext();

    @Override
    public List<Dish> filterPaging(int index, int record_per_page, String searchKey, String categoryId, String type, String value) {
        List<Dish> list = new ArrayList<>();
        String query = "select * from Dish where is_available = 1 and category_id " + categoryId + " and name like N'%" + searchKey + "%' "
                + "order by " + value + " " + type + " offset ? rows fetch next ? rows only;";
        try {
            connection = dBContext.openConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, (index - 1) * record_per_page);
            ps.setInt(2, record_per_page);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Dish d = new Dish();
                d.setId(rs.getInt("id"));
                d.setName(rs.getString("name"));
                d.setBrief_info(rs.getString("brief_info"));
                d.setDescription(rs.getString("description"));
                d.setPrice(rs.getInt("price"));
                d.setIs_available(rs.getBoolean("is_available"));
                d.setCategory_id(rs.getInt("category_id"));
                d.setImages(getImagesOfDish(rs.getInt("id")));

                list.add(d);
            }
        } catch (SQLException e) {
            Logger.getLogger(DishDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DishDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }

        return list;
    }

    private List<Image> getImagesOfDish(int aInt) {
        List<Image> list = new ArrayList<>();
        String sql = "select * from DishImage di join Image i on di.image_id = i.id where di.dish_id=?";
        try {
            connection = dBContext.openConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, aInt);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Image i = new Image();
                i.setId(rs.getInt("id"));
                i.setUrl(rs.getString("url"));
                list.add(i);
            }
        } catch (SQLException e) {
            Logger.getLogger(DishDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DishDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }
        return list;
    }

    @Override
    public int countDishByCondition(String searchKey, String categoryId) {
        try {
            // index: trang click
            connection = dBContext.openConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DishDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        String query = "select count(*) from Dish where category_id " + categoryId + "  and name like N'%" + searchKey + "%' ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(DishDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }
        return -1;
    }

    @Override
    public List<Dish> getAll() {
        List<Dish> list = new ArrayList<>();
        String query = "select * from Dish ";
        try {
            connection = dBContext.openConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Dish d = new Dish();
                d.setId(rs.getInt("id"));
                d.setName(rs.getString("name"));
                d.setBrief_info(rs.getString("brief_info"));
                d.setDescription(rs.getString("description"));
                d.setPrice(rs.getInt("price"));
                d.setIs_available(rs.getBoolean("is_available"));
                d.setCategory_id(rs.getInt("category_id"));
                d.setImages(getImagesOfDish(rs.getInt("id")));

                list.add(d);
            }
        } catch (SQLException e) {
            Logger.getLogger(DishDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DishDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }

        return list;
    }

    @Override
    public Dish getDishByID(int product_id) {
        String query = "select * from Dish where id=?";
        try {
            connection = dBContext.openConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, product_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Dish d = new Dish();
                d.setId(rs.getInt("id"));
                d.setName(rs.getString("name"));
                d.setBrief_info(rs.getString("brief_info"));
                d.setDescription(rs.getString("description"));
                d.setPrice(rs.getInt("price"));
                d.setIs_available(rs.getBoolean("is_available"));
                d.setCategory_id(rs.getInt("category_id"));
                d.setImages(getImagesOfDish(rs.getInt("id")));

                return d;
            }
        } catch (SQLException e) {
            Logger.getLogger(DishDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DishDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }
        return null;
    }

    @Override
    public List<Dish> getRelatedDish(int product_id, int category_id) {
        List<Dish> list = new ArrayList<>();
        String query = "select * from Dish where category_id=? and id <> ? ";
        try {
            connection = dBContext.openConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, category_id);
            ps.setInt(2, product_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Dish d = new Dish();
                d.setId(rs.getInt("id"));
                d.setName(rs.getString("name"));
                d.setBrief_info(rs.getString("brief_info"));
                d.setDescription(rs.getString("description"));
                d.setPrice(rs.getInt("price"));
                d.setIs_available(rs.getBoolean("is_available"));
                d.setCategory_id(rs.getInt("category_id"));
                d.setImages(getImagesOfDish(rs.getInt("id")));

                list.add(d);
            }
        } catch (SQLException e) {
            Logger.getLogger(DishDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DishDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }
        return list;
    }

    @Override
    public int getTotalProduct() {
        try {
            connection = dBContext.openConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DishDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sql = "select COUNT(id) from Dish";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(DishDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }
        return 0;
    }

    @Override
    public void updateDish(Dish d) {
        try {
            connection = dBContext.openConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DishDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sql = "UPDATE Dish set name = ? , price = ? , description = ? ,"
                + " brief_info = ?, is_available = ? , category_id = ? where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, d.getName());
            st.setInt(2, d.getPrice());
            st.setString(3, d.getDescription());
            st.setString(4, d.getBrief_info());
            st.setBoolean(5, d.getIs_available());
            st.setInt(6, d.getCategory_id());
            st.setInt(7, d.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(DishDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }
    }

    @Override
    public int createDish(Dish d) {
        int dishId = -1;
        try {
            connection = dBContext.openConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DishDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sql = "Insert into Dish (name,price,description,"
                + " brief_info, is_available , category_id) values (?,?,?,?,?,?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, d.getName());
            st.setInt(2, d.getPrice());
            st.setString(3, d.getDescription());
            st.setString(4, d.getBrief_info());
            st.setBoolean(5, true);
            st.setInt(6, d.getCategory_id());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                dishId = rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(DishDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }
        return dishId;

    }

    @Override
    public int createImage(String base64Image) {
        int imageId = -1;
        try {
            connection = dBContext.openConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DishDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sql = "INSERT INTO Image (url) VALUES (?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, base64Image);

            // Thực thi câu lệnh
            st.executeUpdate();

            // Lấy ID tự động sinh ra
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                imageId = rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(DishDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }

        return imageId;
    }

    @Override
    public void saveImageToDish(int newdishId, int newimageId) {
        try {
            connection = dBContext.openConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DishDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sql = "INSERT INTO DishImage (dish_id, image_id) VALUES (?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, newdishId);
            st.setInt(2, newimageId);

            // Thực thi câu lệnh
            st.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(DishDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
            }
        }
    }

}
