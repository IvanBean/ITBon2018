package ivankuo.com.itbon2018.ui;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.text.TextUtils;

import javax.inject.Inject;

import ivankuo.com.itbon2018.api.ApiResponse;
import ivankuo.com.itbon2018.data.DataModel;
import ivankuo.com.itbon2018.api.RepoSearchResponse;
import ivankuo.com.itbon2018.util.AbsentLiveData;

public class RepoViewModel extends ViewModel {

    public final ObservableBoolean isLoading = new ObservableBoolean(false);

    private final MutableLiveData<String> query = new MutableLiveData<>();

    private final LiveData<ApiResponse<RepoSearchResponse>> repos;

    private DataModel mDataModel;

    @Inject
    public RepoViewModel(DataModel dataModel) {
        super();
        mDataModel = dataModel;
        repos = Transformations.switchMap(query, new Function<String, LiveData<ApiResponse<RepoSearchResponse>>>() {
            @Override
            public LiveData<ApiResponse<RepoSearchResponse>> apply(String userInput) {
                if (TextUtils.isEmpty(userInput)) {
                    return AbsentLiveData.create();
                } else {
                    return mDataModel.searchRepo(userInput);
                }
            }
        });
    }

    LiveData<ApiResponse<RepoSearchResponse>> getRepos() {
        return repos;
    }

    void searchRepo(String userInput) {
        query.setValue(userInput);
    }
}