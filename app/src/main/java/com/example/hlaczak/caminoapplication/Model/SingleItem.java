package com.example.hlaczak.caminoapplication.Model;

public class SingleItem {

    private String category;
    private String name;
    private String amount;
    private String weigth;

    public SingleItem(String category, String name, String amount, String weigth) {
        this.category = category;
        this.name = name;
        this.amount = amount;
        this.weigth = weigth;
    }

    public SingleItem() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getWeigth() {
        return weigth;
    }

    public void setWeigth(String weigth) {
        this.weigth = weigth;
    }
}
