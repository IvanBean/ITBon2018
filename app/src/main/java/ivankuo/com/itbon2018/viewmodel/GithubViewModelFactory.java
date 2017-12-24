package ivankuo.com.itbon2018.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import ivankuo.com.itbon2018.data.DataModel;
import ivankuo.com.itbon2018.ui.RepoViewModel;

public class GithubViewModelFactory implements ViewModelProvider.Factory {

    private DataModel dataModel;

    public GithubViewModelFactory() {
        this.dataModel = new DataModel();
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RepoViewModel.class)) {
            return (T) new RepoViewModel(dataModel);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}