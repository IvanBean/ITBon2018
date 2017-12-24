package ivankuo.com.itbon2018.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;

import java.util.List;

import ivankuo.com.itbon2018.data.DataModel;
import ivankuo.com.itbon2018.data.model.Repo;

public class RepoViewModel extends ViewModel {

    public final ObservableBoolean isLoading = new ObservableBoolean(false);

    private final MutableLiveData<List<Repo>> repos = new MutableLiveData<>();

    private DataModel dataModel;

    public RepoViewModel(DataModel dataModel) {
        super();
        this.dataModel = dataModel;
    }

    LiveData<List<Repo>> getRepos() {
        return repos;
    }

    void searchRepo(String query) {

        isLoading.set(true);

        dataModel.searchRepo(query, new DataModel.onDataReadyCallback() {
            @Override
            public void onDataReady(List<Repo> data) {
                repos.setValue(data);
                isLoading.set(false);
            }
        });
    }
}