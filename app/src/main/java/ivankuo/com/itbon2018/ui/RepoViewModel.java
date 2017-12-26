package ivankuo.com.itbon2018.ui;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.text.TextUtils;

import java.util.List;

import ivankuo.com.itbon2018.data.DataModel;
import ivankuo.com.itbon2018.data.model.Repo;
import ivankuo.com.itbon2018.util.AbsentLiveData;

public class RepoViewModel extends ViewModel {

    public final ObservableBoolean isLoading = new ObservableBoolean(false);

    private final MutableLiveData<String> query = new MutableLiveData<>();

    private final LiveData<List<Repo>> repos;

    private DataModel dataModel;

    public RepoViewModel(final DataModel dataModel) {
        super();
        this.dataModel = dataModel;
        repos = Transformations.switchMap(query, new Function<String, LiveData<List<Repo>>>() {
            @Override
            public LiveData<List<Repo>> apply(String userInput) {
                if (TextUtils.isEmpty(userInput)) {
                    return AbsentLiveData.create();
                } else {
                    return dataModel.searchRepo(userInput);
                }
            }
        });
    }

    LiveData<List<Repo>> getRepos() {
        return repos;
    }

    void searchRepo(String userInput) {
        query.setValue(userInput);
    }
}