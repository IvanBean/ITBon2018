package ivankuo.com.itbon2018.ui;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import ivankuo.com.itbon2018.api.RepoSearchResponse;
import ivankuo.com.itbon2018.data.RepoRepository;
import ivankuo.com.itbon2018.data.model.Repo;
import ivankuo.com.itbon2018.data.model.Resource;
import ivankuo.com.itbon2018.data.model.User;
import ivankuo.com.itbon2018.util.AbsentLiveData;
import retrofit2.Response;

public class RepoViewModel extends ViewModel {

    private final MutableLiveData<String> query = new MutableLiveData<>();

    public final LiveData<Resource<List<Repo>>> repos;

    private RepoRepository repository;

    @Inject
    public RepoViewModel(RepoRepository repoRepository) {
        super();
        repository = repoRepository;
        repos = Transformations.switchMap(query, new Function<String, LiveData<Resource<List<Repo>>>>() {
            @Override
            public LiveData<Resource<List<Repo>>> apply(String userInput) {
                if (userInput == null || userInput.isEmpty()) {
                    return AbsentLiveData.create();
                } else {
                    return repository.search(userInput);
                }
            }
        });
    }

    LiveData<Resource<List<Repo>>> getRepos() {
        return repos;
    }

    void searchRepo(String userInput) {
        query.setValue(userInput);
    }

    Observable<Response<RepoSearchResponse>> searchRepoRX(String query) {
        return repository.searchRepoRX(query);
    }

    Observable<Response<User>> getUser(String login) {
        return repository.getUser(login);
    }
}