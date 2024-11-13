/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dal.interfaces;

import models.DateObject;

public interface IDateDAO {

    public DateObject get7day();

    public int countDayByStartEnd(String start, String end);
}
