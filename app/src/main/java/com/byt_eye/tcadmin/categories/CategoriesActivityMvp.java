package com.byt_eye.tcadmin.categories;

import com.byt_eye.tcadmin.modals.CategoryResponse;

import java.util.List;

public interface CategoriesActivityMvp {
    void onGettingLangCategoriesList(String language, List<CategoryResponse> categoriesList);

}
