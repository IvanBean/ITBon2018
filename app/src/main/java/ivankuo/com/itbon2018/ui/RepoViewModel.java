package ivankuo.com.itbon2018.ui;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;

import java.util.List;

import javax.inject.Inject;

import ivankuo.com.itbon2018.data.RepoRepository;
import ivankuo.com.itbon2018.data.model.Repo;
import ivankuo.com.itbon2018.data.model.Resource;
import ivankuo.com.itbon2018.util.AbsentLiveData;

public class RepoViewModel extends ViewModel {

    private final MutableLiveData<String> query = new MutableLiveData<>();

    private final LiveData<Resource<List<Repo>>> repos;

    private RepoRepository mRepoRepository;

    @Inject
    public RepoViewModel(RepoRepository repoRepository) {
        super();
        mRepoRepository = repoRepository;
        repos = Transformations.switchMap(query, new Function<String, LiveData<Resource<List<Repo>>>>() {
            @Override
            public LiveData<Resource<List<Repo>>> apply(String userInput) {
                if (TextUtils.isEmpty(userInput)) {
                    return AbsentLiveData.create();
                } else {
                    return mRepoRepository.search(userInput);
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
}