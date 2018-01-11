package ivankuo.com.itbon2018.data;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
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

    public LiveData<Resource<PagedList<Repo>>> search(final String query) {
        return new NetworkBoundResource<PagedList<Repo>, RepoSearchResponse>() {
            @NonNull
            @Override
            protected LiveData<PagedList<Repo>> loadFromDb() {
                return Transformations.switchMap(repoDao.search(query), new Function<RepoSearchResult, LiveData<PagedList<Repo>>>() {
                    @Override
                    public LiveData<PagedList<Repo>> apply(RepoSearchResult searchData) {
                        return new LivePagedListBuilder<>(repoDao.loadById(searchData.repoIds), 30).build();
                    }
                });
            }

            @Override
            protected boolean shouldFetch(@Nullable PagedList<Repo> data) {
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