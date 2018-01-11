package ivankuo.com.itbon2018.ui;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import java.util.List;

import javax.inject.Inject;

import ivankuo.com.itbon2018.data.RepoRepository;
import ivankuo.com.itbon2018.data.model.Repo;
import ivankuo.com.itbon2018.data.model.Resource;
import ivankuo.com.itbon2018.util.AbsentLiveData;

public class RepoViewModel extends ViewModel {

    private final MutableLiveData<String> query = new MutableLiveData<>();

    private final LiveData<Resource<PagedList<Repo>>> repos;

    private RepoRepository mRepoRepository;

    @Inject
    public RepoViewModel(RepoRepository repoRepository) {
        super();
        mRepoRepository = repoRepository;
        repos = Transformations.switchMap(query, new Function<String, LiveData<Resource<PagedList<Repo>>>>() {
            @Override
            public LiveData<Resource<PagedList<Repo>>> apply(String userInput) {
                if (userInput == null || userInput.isEmpty()) {
                    return AbsentLiveData.create();
                } else {
                    return mRepoRepository.search(userInput);
                }
            }
        });
    }

    LiveData<Resource<PagedList<Repo>>> getRepos() {
        return repos;
    }

    void searchRepo(String userInput) {
        query.setValue(userInput);
    }
}