package ivankuo.com.itbon2018.data;

import android.arch.lifecycle.LiveData;

import javax.inject.Inject;
import javax.inject.Singleton;

import ivankuo.com.itbon2018.api.ApiResponse;
import ivankuo.com.itbon2018.api.GithubService;
import ivankuo.com.itbon2018.api.RepoSearchResponse;
import ivankuo.com.itbon2018.data.db.RepoDao;

@Singleton
public class DataModel {

    private RepoDao repoDao;

    private GithubService githubService;

    @Inject
    public DataModel(RepoDao repoDao, GithubService githubService) {
        this.repoDao = repoDao;
        this.githubService = githubService;
    }

    public LiveData<ApiResponse<RepoSearchResponse>> searchRepo(String query) {
        return githubService.searchRepos(query);
    }
}