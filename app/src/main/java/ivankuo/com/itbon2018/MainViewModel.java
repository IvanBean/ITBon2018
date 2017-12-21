package ivankuo.com.itbon2018;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

public class MainViewModel {

    public final ObservableField<String> mData = new ObservableField<>();

    public final ObservableBoolean isLoading = new ObservableBoolean(false);

    private DataModel dataModel = new DataModel();

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