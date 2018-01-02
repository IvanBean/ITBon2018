package ivankuo.com.itbon2018.data;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ivankuo.com.itbon2018.api.ApiResponse;
import ivankuo.com.itbon2018.api.GithubService;
import ivankuo.com.itbon2018.api.RepoSearchResponse;
import ivankuo.com.itbon2018.data.db.RepoDao;
import ivankuo.com.itbon2018.data.model.Repo;
import ivankuo.com.itbon2018.data.model.RepoSearchResult;
import ivankuo.com.itbon2018.data.model.Resource;
import ivankuo.com.itbon2018.util.AbsentLiveData;

@Singleton
public class RepoRepository {

    private RepoDao repoDao;

    private GithubService githubService;

    @Inject
    public RepoRepository(RepoDao repoDao, GithubService githubService) {
        this.repoDao = repoDao;
        this.githubService = githubService;
    }

    public LiveData<Resource<List<Repo>>> search(final String query) {
        return new NetworkBoundResource<List<Repo>, RepoSearchResponse>() {
            @NonNull
            @Override
            protected LiveData<List<Repo>> loadFromDb() {
                return Transformations.switchMap(repoDao.search(query), new Function<RepoSearchResult, LiveData<List<Repo>>>() {
                    @Override
                    public LiveData<List<Repo>> apply(RepoSearchResult searchData) {
                        if (searchData == null) {
                            return AbsentLiveData.create();
                        } else {
                            return repoDao.loadOrdered(searchData.repoIds);
                        }
                    }
                });
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Repo> data) {
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RepoSearchResponse>> createCall() {
                return githubService.searchRepos(query);
            }

            @Override
            protected void saveCallResult(@NonNull RepoSearchResponse item) {
                List<Integer> repoIds = item.getRepoIds();
                RepoSearchResult repoSearchResult =
                        new RepoSearchResult(query, repoIds, item.getTotal());

                repoDao.insertRepos(item.getItems());
                repoDao.insert(repoSearchResult);
            }
        }.asLiveData();
    }
}