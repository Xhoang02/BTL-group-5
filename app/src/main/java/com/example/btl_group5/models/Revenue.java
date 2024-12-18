package com.example.btl_group5.models;

public class Revenue {
    private int id;
    private String client;
    private int total;

    public Revenue(int id, String client, int total) {
        this.id = id;
        this.client = client;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}