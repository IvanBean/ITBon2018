package ivankuo.com.itbon2018;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import ivankuo.com.itbon2018.databinding.MainActivityBinding;

public class MainActivity extends AppCompatActivity {

    private MainActivityBinding binding;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        GithubViewModelFactory factory = new GithubViewModelFactory(new DataModel());
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
        binding.setViewModel(viewModel);

        viewModel.mData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String data) {
                binding.txtHelloWord.setText(data);
            }
        });

        viewModel.toastText.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String text) {
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}