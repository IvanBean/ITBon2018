package ivankuo.com.itbon2018.ui;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import ivankuo.com.itbon2018.R;
import ivankuo.com.itbon2018.data.model.Repo;
import ivankuo.com.itbon2018.data.model.Resource;
import ivankuo.com.itbon2018.testing.SingleFragmentActivity;
import ivankuo.com.itbon2018.util.EspressoTestUtil;
import ivankuo.com.itbon2018.util.RecyclerViewMatcher;
import ivankuo.com.itbon2018.util.TaskExecutorWithIdlingResourceRule;
import ivankuo.com.itbon2018.util.TestUtil;
import ivankuo.com.itbon2018.util.ViewModelUtil;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class RepoFragmentTest {

    @Rule
    public ActivityTestRule<SingleFragmentActivity> activityRule =
            new ActivityTestRule<>(SingleFragmentActivity.class, true, true);

    @Rule
    public TaskExecutorWithIdlingResourceRule executorRule =
            new TaskExecutorWithIdlingResourceRule();

    private RepoViewModel viewModel;

    private MutableLiveData<Resource<List<Repo>>> repos = new MutableLiveData<>();

    @Before
    public void init() {
        EspressoTestUtil.disableProgressBarAnimations(activityRule);
        RepoFragment repoFragment = new RepoFragment();
        viewModel = mock(RepoViewModel.class);
        when(viewModel.getRepos()).thenReturn(repos);
        repoFragment.factory = ViewModelUtil.createFor(viewModel);
        activityRule.getActivity().setFragment(repoFragment);
    }

    @Test
    public void search() {
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())));
        onView(withId(R.id.edtQuery)).perform(typeText("foo"));
        onView(withId(R.id.btnSearch)).perform(click());
        verify(viewModel).searchRepo("foo");
        repos.postValue(Resource.<List<Repo>>loading(null));
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()));
    }

    @Test
    public void loadResults() {
        Repo repo = TestUtil.createRepo("foo", "bar", "desc");
        repos.postValue(Resource.success(Arrays.asList(repo)));
        onView(listMatcher().atPosition(0)).check(matches(hasDescendant(withText("foo/bar"))));
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())));
    }

    @NonNull
    private RecyclerViewMatcher listMatcher() {
        return new RecyclerViewMatcher(R.id.recyclerView);
    }
}