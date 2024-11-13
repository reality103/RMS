/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.interfaces;

import java.util.List;
import models.User;

public interface IUserDAO {

    User login(String userName, String passWord);

    public void updateProfile(User user);

    public void registerProfile(User user);

    public int changePassword(int id, String newPassword);

    public int SaveUser(String type, User user);

    public User checkExist(String column, String value, int userid);

    public int DeleteUser(int userId);

    public User getUserById(int userId);

    public int CountCustomer();

    public List<User> getUsers();

    public User getUserByEmail(String u, String email);

    public User getUserByPhone(String u, String phone);

    public void updateUser(User user);

    public void insertUser(User user);
}
