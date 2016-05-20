package com.example.caij.androidtest.present;

import com.example.caij.androidtest.Contributor;
import com.example.caij.androidtest.ContributorsPresent;
import com.example.caij.androidtest.ContributorsView;
import com.example.caij.androidtest.Service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ContributorsPresentUnitTest {

    private ContributorsPresent mContributorsPresent;
    private ContributorsView mContributorsView;
    private Service mGithubService;


    @Before
    public void setUp() throws Exception {
        mContributorsView = mock(ContributorsView.class);
        mGithubService = mock(Service.class);
        mContributorsPresent = new ContributorsPresent(mContributorsView, mGithubService);
    }


    @Test
    public void loadContributors() throws Exception {
        List<Contributor> contributors = new ArrayList<>();
        contributors.add(new Contributor("JK", 1));
        contributors.add(new Contributor("CJ", 2));
        when(mGithubService.loadContributors("square", "retrofit")).thenReturn(Response.success(contributors));
        mContributorsPresent.loadContributors("square", "retrofit");
        verify(mGithubService).loadContributors("square", "retrofit");
        verify(mContributorsView).onSucess(contributors);
//        verify(mContributorsView).onError();
    }

    @Test
    public void loadContributorsOfCallback() throws Exception {
        List<Contributor> contributors = new ArrayList<>();
        contributors.add(new Contributor("JK", 1));
        contributors.add(new Contributor("CJ", 2));
        mContributorsPresent.loadContributorsOfCallBack("square", "retrofit");
        ArgumentCaptor<Callback> mLoadTasksCallbackCaptor = ArgumentCaptor.forClass(Callback.class);
        verify(mGithubService).loadContributors(eq("square"), eq("retrofit"), mLoadTasksCallbackCaptor.capture());
        mLoadTasksCallbackCaptor.getValue().onResponse(null, Response.success(contributors));
        verify(mContributorsView).onSucess(contributors);
    }
}