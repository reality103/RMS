/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

public class Cart {

    private int dish_id, customer_id, quantity, total_cost, table_id;
    public Dish dish;
    public User customer;
    public DiningTable table;

    public DiningTable getTable() {
        return table;
    }

    public void setTable(DiningTable table) {
        this.table = table;
    }

    public Cart() {
    }

    public Cart(int dish_id, int customer_id, int quantity, int total_cost, int table) {
        this.dish_id = dish_id;
        this.customer_id = customer_id;
        this.quantity = quantity;
        this.total_cost = total_cost;
        this.table_id = table;
    }

    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public int getDish_id() {
        return dish_id;
    }

    public void setDish_id(int dish_id) {
        this.dish_id = dish_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(int total_cost) {
        this.total_cost = total_cost;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

}
