package ivankuo.com.itbon2018;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class GithubViewModelFactory implements ViewModelProvider.Factory {

    private DataModel dataModel;

    public GithubViewModelFactory(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(dataModel);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}