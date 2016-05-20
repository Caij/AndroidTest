package com.example.caij.androidtest;

import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Caij on 2016/5/19.
 */
public interface GitHub {
    public static final String API_URL = "https://api.github.com";

    @GET("/repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> contributors(
            @Path("owner") String owner,
            @Path("repo") String repo);


    class Factory {
        public static GitHub create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .callbackExecutor(new Executor() {
                        @Override
                        public void execute(Runnable command) {
                            command.run();
                        }
                    })
                    .build();
            return retrofit.create(GitHub.class);
        }
    }
}
