package ivankuo.com.itbon2018;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ivankuo.com.itbon2018.databinding.MainActivityBinding;

public class MainActivity extends AppCompatActivity {

    private MainActivityBinding binding;

    private MainViewModel viewModel = new MainViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        binding.setViewModel(viewModel);
    }
}