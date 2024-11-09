package com.example.btl_group5.models;

public class MonChinh {
    private int id1;
    private int img1;
    private String name1;
    private int price1;

    public MonChinh(int id1, int img1, String name1, int price1) {
        this.id1 = id1;
        this.img1 = img1;
        this.name1 = name1;
        this.price1 = price1;
    }

    public int getId1() {
        return id1;
    }

    public void setId1(int id1) {
        this.id1 = id1;
    }

    public int getImg1() {
        return img1;
    }

    public void setImg1(int img1) {
        this.img1 = img1;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public int getPrice1() {
        return price1;
    }

    public void setPrice1(int price1) {
        this.price1 = price1;
    }
}