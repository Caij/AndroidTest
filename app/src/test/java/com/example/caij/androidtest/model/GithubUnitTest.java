package com.example.caij.androidtest.model;

import com.example.caij.androidtest.Contributor;
import com.example.caij.androidtest.GitHub;
import com.example.caij.androidtest.Service;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class GithubUnitTest {


    @Test
    public void loadContributors() throws Exception {
        GitHub github =  GitHub.Factory.create();
        Service service = new Service(github);
        List<Contributor> contributors = service.loadContributors("square", "retrofit").body();
        for (Contributor contributor : contributors) {
            System.out.println(contributor.login + " (" + contributor.contributions + ")");
        }
        assertNotNull(contributors);
        assertNotNull(contributors.get(0));
    }

    @Test
    public void loadContributorsOfCallback() throws Exception {
        GitHub github =  GitHub.Factory.create();
        Service service = new Service(github);
        final CountDownLatch latch = new CountDownLatch(1);
        service.loadContributors("square", "retrofit", new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                assertNotNull(response.body());
                assertNotNull(response.body().get(0));
//                for (Contributor contributor : response.body()) {
//                    System.out.println(contributor.login + " (" + contributor.contributions + ")");
//                }
                latch.countDown();
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {
                System.out.println("error");
                latch.countDown();
            }
        });
        latch.await(10, TimeUnit.SECONDS);
    }
}