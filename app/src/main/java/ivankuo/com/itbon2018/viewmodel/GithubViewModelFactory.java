package ivankuo.com.itbon2018.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import ivankuo.com.itbon2018.data.DataModel;
import ivankuo.com.itbon2018.ui.RepoViewModel;

@Singleton
public class GithubViewModelFactory implements ViewModelProvider.Factory {

    private DataModel dataModel;

    @Inject
    public GithubViewModelFactory(DataModel dataModel) {
        this.dataModel = dataModel;
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