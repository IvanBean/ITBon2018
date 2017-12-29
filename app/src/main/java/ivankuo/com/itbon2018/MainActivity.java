package ivankuo.com.itbon2018;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import ivankuo.com.itbon2018.api.GithubService;
import ivankuo.com.itbon2018.ui.RepoFragment;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Inject
    GithubService githubService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (githubService != null) {
            Timber.d("Hello Dagger!");
        }
        String tag = RepoFragment.TAG;
        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            RepoFragment fragment = RepoFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment, tag)
                    .commit();
        }
    }
}