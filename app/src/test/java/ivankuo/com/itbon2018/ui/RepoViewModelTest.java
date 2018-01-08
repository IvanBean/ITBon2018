package ivankuo.com.itbon2018.ui;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;

import java.util.List;

import ivankuo.com.itbon2018.data.RepoRepository;
import ivankuo.com.itbon2018.data.model.Repo;
import ivankuo.com.itbon2018.data.model.Resource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class RepoViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutor = new InstantTaskExecutorRule();

    private RepoViewModel viewModel;

    private RepoRepository repository;

    @Before
    public void init() {
        repository = mock(RepoRepository.class);
        viewModel = new RepoViewModel(repository);
    }

    @Test
    public void testNotNull() {
        assertThat(viewModel.getRepos(), notNullValue());
        verify(repository, never()).search(anyString());
    }

    @Test
    public void dontFetchWithoutObservers() {
        viewModel.searchRepo("foo");
        verify(repository, never()).search(anyString());
    }

    @Test
    public void fetchWhenObserved() {
        ArgumentCaptor<String> input = ArgumentCaptor.forClass(String.class);
        viewModel.getRepos().observeForever(mock(Observer.class));
        viewModel.searchRepo("foo");
        verify(repository, times(1)).search(input.capture());
        assertThat(input.getValue(), is("foo"));
    }

    @Test
    public void search() {
        Observer<Resource<List<Repo>>> observer = mock(Observer.class);
        viewModel.getRepos().observeForever(observer);
        viewModel.searchRepo("foo");
        verify(repository).search("foo");
    }

    @Test
    public void nullSearchInput() {
        Observer<Resource<List<Repo>>> observer = mock(Observer.class);
        viewModel.searchRepo("foo");
        viewModel.searchRepo(null);
        viewModel.getRepos().observeForever(observer);
        verify(observer).onChanged(null);
    }
}