/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dal.interfaces;

import java.util.List;
import models.DiningTable;

public interface ITableDAO {

    List<DiningTable> getAvalableTables();

    public DiningTable getTableById(int aInt);

    public List<DiningTable> getAllTable();

    public void updateTable(DiningTable d);

    public int createTable(DiningTable d);
}
