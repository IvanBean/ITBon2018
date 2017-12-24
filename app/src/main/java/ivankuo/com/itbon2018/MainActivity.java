package ivankuo.com.itbon2018;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ivankuo.com.itbon2018.ui.RepoFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        String tag = RepoFragment.TAG;
        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            RepoFragment fragment = RepoFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment, tag)
                    .commit();
        }
    }
}