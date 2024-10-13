package com.example.logisticcavan.common.utils;

import com.example.logisticcavan.products.categories.Category;

import java.util.List;

public class CategoriesListLocal {
    public static final List<Category> categories = List.of(new Category("All", false),new Category("Meat", false), new Category("Chicken", false),
            new Category("Seafood", false), new Category("Fast food", false), new Category("Desserts", false),
            new Category("Other", false));
}
