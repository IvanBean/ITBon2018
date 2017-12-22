package ivankuo.com.itbon2018;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

public class MainViewModel extends ViewModel {

    public final ObservableField<String> mData = new ObservableField<>();

    public final ObservableBoolean isLoading = new ObservableBoolean(false);

    private DataModel dataModel;

    public MainViewModel(DataModel dataModel) {
        super();
        this.dataModel = dataModel;
    }

    public void refresh() {

        isLoading.set(true);

        dataModel.retrieveData(new DataModel.onDataReadyCallback() {
            @Override
            public void onDataReady(String data) {
                mData.set(data);
                isLoading.set(false);
            }
        });
    }
}