package com.example.caij.androidtest;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Caij on 2016/5/19.
 */
public class ContributorsPresent {

    private ContributorsView mContributorsView;
    private Service mGitHub;

    public ContributorsPresent(ContributorsView contributorsView, Service github){
        mContributorsView = contributorsView;
        mGitHub = github;
    }

    public void loadContributors(String owner, String repo) {
        try {
            Response<List<Contributor>> response = mGitHub.loadContributors(owner, repo);
            mContributorsView.onSucess(response.body());
        } catch (IOException e) {
            mContributorsView.onError();
        }
    }

    public void loadContributorsOfCallBack(String owner, String repo) {
        mGitHub.loadContributors(owner, repo, new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                mContributorsView.onSucess(response.body());
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {
                mContributorsView.onError();
            }
        });

    }
}
