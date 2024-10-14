package com.example.logisticcavan.common.di;

import static com.example.logisticcavan.common.utils.Constant.PREF_NAME;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class ModuleApp {

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

}
