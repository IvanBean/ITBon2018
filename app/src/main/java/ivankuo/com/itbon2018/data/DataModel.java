package ivankuo.com.itbon2018.data;

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

    public void searchRepo(String query, final onDataReadyCallback callback) {
        githubService.searchRepos(query)
                .enqueue(new Callback<RepoSearchResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<RepoSearchResponse> call, @NonNull Response<RepoSearchResponse> response) {
                        callback.onDataReady(response.body().getItems());
                    }

                    @Override
                    public void onFailure(@NonNull Call<RepoSearchResponse> call, @NonNull Throwable t) {
                        // TODO: error handle
                    }
                });
    }

    public interface onDataReadyCallback {
        void onDataReady(List<Repo> data);
    }
}