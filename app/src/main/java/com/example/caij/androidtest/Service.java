package com.example.caij.androidtest;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Caij on 2016/5/19.
 */
public class Service {

    private GitHub mGitHub;

    public Service(GitHub gitHub){
        this.mGitHub = gitHub;
    }

    public Response<List<Contributor>>  loadContributors(String owner, String repo) throws IOException {
        Call<List<Contributor>> call = mGitHub.contributors(owner, repo);
        return call.execute();
    }

    public void  loadContributors(String owner, String repo, final Callback<List<Contributor>> callback) {
        mGitHub.contributors(owner, repo).enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }
}
