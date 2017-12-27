package ivankuo.com.itbon2018.data;

import android.arch.lifecycle.LiveData;

import ivankuo.com.itbon2018.api.ApiResponse;
import ivankuo.com.itbon2018.api.GithubService;
import ivankuo.com.itbon2018.api.RetrofitManager;
import ivankuo.com.itbon2018.data.model.RepoSearchResponse;

public class DataModel {

    private GithubService githubService = RetrofitManager.getAPI();

    public LiveData<ApiResponse<RepoSearchResponse>> searchRepo(String query) {
        return githubService.searchRepos(query);
    }
}