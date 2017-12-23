package ivankuo.com.itbon2018;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;

import ivankuo.com.itbon2018.util.SingleLiveEvent;

public class MainViewModel extends ViewModel {

    public final ObservableBoolean isLoading = new ObservableBoolean(false);

    public final MutableLiveData<String> mData = new MutableLiveData<>();

    public final SingleLiveEvent<String> toastText = new SingleLiveEvent<>();

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
                mData.setValue(data);
                toastText.setValue("下載完成");
                isLoading.set(false);
            }
        });
    }
}