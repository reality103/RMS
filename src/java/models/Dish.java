/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.List;

public class Dish {

    private int id;
    private String name;
    private int price;
    private String description;
    private String brief_info;
    private Boolean is_available;
    private int category_id;
    private Category category;
    private List<Image> Images;

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public List<Image> getImages() {
        return Images;
    }

    public void setImages(List<Image> Images) {
        this.Images = Images;
    }

    public Dish() {
    }

    public Dish(int id, String name, int price, String description, String brief_info, Boolean status, int category_id, Category category, List<Image> Images) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.brief_info = brief_info;
        this.is_available = status;
        this.category_id = category_id;
        this.category = category;
        this.Images = Images;
    }

    public Dish(int id, String name, int price, String description, String brief_info, Boolean status, int category_id) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.brief_info = brief_info;
        this.is_available = status;
        this.category_id = category_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrief_info() {
        return brief_info;
    }

    public void setBrief_info(String brief_info) {
        this.brief_info = brief_info;
    }

    public Boolean getIs_available() {
        return is_available;
    }

    public void setIs_available(Boolean is_available) {
        this.is_available = is_available;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
