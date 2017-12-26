package ivankuo.com.itbon2018.data;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import ivankuo.com.itbon2018.api.GithubService;
import ivankuo.com.itbon2018.api.RetrofitManager;
import ivankuo.com.itbon2018.data.model.Repo;
import ivankuo.com.itbon2018.data.model.RepoSearchResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataModel {

    private GithubService githubService = RetrofitManager.getAPI();

    public MutableLiveData<List<Repo>> searchRepo(String query) {
        final MutableLiveData<List<Repo>> repos = new MutableLiveData<>();
        githubService.searchRepos(query)
                .enqueue(new Callback<RepoSearchResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<RepoSearchResponse> call, @NonNull Response<RepoSearchResponse> response) {
                        repos.setValue(response.body().getItems());
                    }

                    @Override
                    public void onFailure(@NonNull Call<RepoSearchResponse> call, @NonNull Throwable t) {
                        // TODO: error handle
                    }
                });
        return repos;
    }
}