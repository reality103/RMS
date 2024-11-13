/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.implement;

import context.DBContext;
import dal.interfaces.ICategoryDAO;
import java.sql.Connection;
import models.Category;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryDAOImpl extends context.DBContext implements ICategoryDAO {

    Connection connection = null;
    DBContext dBContext = new DBContext();

    @Override
    public List<Category> getCategoryList() {
        List<Category> list = new ArrayList();
        try {
            connection = dBContext.openConnection();
            String sql = "SELECT * FROM Category";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Category(rs.getInt("id"), rs.getString("name")));
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

}
