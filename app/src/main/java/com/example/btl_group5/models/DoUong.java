package com.example.btl_group5.models;

public class DoUong {
    private int id;
    private int img;
    private String name;
    private int price;

    public DoUong(int id, int img, String name, int price) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
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
}