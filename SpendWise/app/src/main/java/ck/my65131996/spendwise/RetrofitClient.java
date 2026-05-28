package ck.my65131996.spendwise;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static Retrofit getClient() {

        return new Retrofit.Builder()

                .baseUrl("https://api.imgbb.com/")

                .addConverterFactory(
                        GsonConverterFactory.create())

                .build();
    }
}