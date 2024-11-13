/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.implement;

import dal.interfaces.ITableDAO;
import models.DiningTable;
import context.DBContext;
import java.sql.Connection;
import models.Category;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TableDAOImpl extends DBContext implements ITableDAO {

    Connection connection = null;
    DBContext dBContext = new DBContext();

    @Override
    public List<DiningTable> getAvalableTables() {
        List<DiningTable> list = new ArrayList();
        try {
            connection = dBContext.openConnection();
            String sql = "SELECT dt.*\n"
                    + "FROM DiningTable dt\n"
                    + "LEFT JOIN [Order] o ON dt.id = o.table_id\n"
                    + "WHERE o.table_id IS NULL;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new DiningTable(rs.getInt("id"), rs.getString("name"), rs.getString("capacity")));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
                Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return list;
    }

    @Override
    public DiningTable getTableById(int aInt) {
        try {
            connection = dBContext.openConnection();
            String sql = "SELECT * FROM DiningTable  where id=? ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, aInt);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new DiningTable(rs.getInt("id"), rs.getString("name"), rs.getString("capacity"));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
                Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return null;
    }

    @Override
    public List<DiningTable> getAllTable() {
        List<DiningTable> list = new ArrayList();
        try {
            connection = dBContext.openConnection();
            String sql = "SELECT * "
                    + "FROM DiningTable ";

            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new DiningTable(rs.getInt("id"), rs.getString("name"), rs.getString("capacity")));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBContext.closeConnection(connection);
            } catch (SQLException e) {
                Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return list;
    }

    @Override
    public void updateTable(DiningTable d) {
        try {
            connection = dBContext.openConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DishDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sql = "Update DiningTable set name = ?, capacity = ? where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, d.getName());
            st.setString(2, d.getCapacity());
            st.setInt(3, d.getId());
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

    @Override
    public int createTable(DiningTable d) {
        int imageId = -1;
        try {
            connection = dBContext.openConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DishDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sql = "INSERT INTO DiningTable (name, capacity) VALUES (?,?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, d.getName());
            st.setString(2, d.getCapacity());

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

}
