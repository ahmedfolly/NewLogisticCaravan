package com.example.logisticcavan.auth.data;

import android.content.SharedPreferences;

import com.example.logisticcavan.auth.domain.entity.UserInfo;
import com.google.gson.Gson;

public class LocalStorageRepository {

    private SharedPreferences sharedPreferences;
    private Gson gson;
    public LocalStorageRepository(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        this.gson = new Gson();
    }

    public void saveUserInfo(UserInfo userInfo) {
        String userInfoJson = gson.toJson(userInfo);
        sharedPreferences.edit().putString("user_info", userInfoJson).apply();
    }

    public UserInfo getUserInfo() {
        String userInfoJson = sharedPreferences.getString("user_info", null);

        if (userInfoJson != null) {
            return gson.fromJson(userInfoJson, UserInfo.class);
        }
        return null;
    }
}
