package com.example.logisticcavan.products.categories;

public class Category {
    private String categoryName ="";

    public Category(String categoryName,boolean isSelected){
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

}
