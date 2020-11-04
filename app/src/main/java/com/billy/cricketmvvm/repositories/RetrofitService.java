package com.billy.cricketmvvm.repositories;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.billy.cricketmvvm.repositories.CricketApi.BASE_URL;

public class RetrofitService {
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <S> S createService(Class<S> serviceClass){
        return retrofit.create(serviceClass);
    }
}

