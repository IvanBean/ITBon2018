package ivankuo.com.itbon2018.data;

import android.arch.lifecycle.LiveData;

import javax.inject.Inject;
import javax.inject.Singleton;

import ivankuo.com.itbon2018.api.ApiResponse;
import ivankuo.com.itbon2018.api.GithubService;
import ivankuo.com.itbon2018.api.RetrofitManager;
import ivankuo.com.itbon2018.data.model.RepoSearchResponse;

@Singleton
public class DataModel {

    private GithubService githubService;

    @Inject
    public DataModel(GithubService githubService) {
        this.githubService = githubService;
    }

    public LiveData<ApiResponse<RepoSearchResponse>> searchRepo(String query) {
        return githubService.searchRepos(query);
    }
}