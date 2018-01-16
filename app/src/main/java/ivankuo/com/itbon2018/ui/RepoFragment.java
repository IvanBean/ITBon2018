package ivankuo.com.itbon2018.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import ivankuo.com.itbon2018.api.RepoSearchResponse;
import ivankuo.com.itbon2018.api.RepoSearchResponseAndUser;
import ivankuo.com.itbon2018.data.model.Repo;
import ivankuo.com.itbon2018.data.model.Resource;
import ivankuo.com.itbon2018.data.model.User;
import ivankuo.com.itbon2018.databinding.RepoFragmentBinding;
import ivankuo.com.itbon2018.di.Injectable;
import retrofit2.Response;
import timber.log.Timber;

public class RepoFragment extends Fragment implements Injectable {

    public static final String TAG = "Repo";

    @Inject
    ViewModelProvider.Factory factory;

    private RepoFragmentBinding binding;

    private RepoViewModel viewModel;

    private RepoAdapter repoAdapter = new RepoAdapter(new ArrayList<Repo>());

    private CompositeDisposable disposables = new CompositeDisposable();

    public static RepoFragment newInstance() {
        return new RepoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = RepoFragmentBinding.inflate(inflater, container, false);

        binding.edtQuery.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    doSearch();
                    return true;
                }
                return false;
            }
        });

        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSearch();
            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(repoAdapter);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, factory).get(RepoViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        viewModel.getRepos().observe(this, new Observer<Resource<List<Repo>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Repo>> resource) {
                repoAdapter.swapItems(resource.data);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposables.clear();
    }

    private void doSearch() {
        String query = binding.edtQuery.getText().toString();
        viewModel.searchRepo(query);
        dismissKeyboard();
    }

    private void dismissKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm =
                    (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void flatMapExample() {
        String query = "android";
        disposables.add(viewModel.searchRepoRX(query)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Response<RepoSearchResponse>, ObservableSource<Repo>>() {
                    @Override
                    public ObservableSource<Repo> apply(Response<RepoSearchResponse> response) throws Exception {
                        List<Repo> repos = response.body().getItems();
                        return Observable.fromIterable(repos);
                    }
                })
                .flatMap(new Function<Repo, ObservableSource<Response<User>>>() {
                    @Override
                    public ObservableSource<Response<User>> apply(Repo repo) throws Exception {
                        return viewModel.getUser(repo.owner.login);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Response<User>>() {
                    @Override
                    public void onNext(Response<User> response) {
                        Timber.d("code " + response.code());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    private void zipExample() {
        Observable.zip(viewModel.searchRepoRX("google"), viewModel.getUser("google"),
                new BiFunction<Response<RepoSearchResponse>, Response<User>, RepoSearchResponseAndUser>() {
                    @Override
                    public RepoSearchResponseAndUser apply(Response<RepoSearchResponse> response,
                                                           Response<User> response2) throws Exception {
                        RepoSearchResponse repoSearchResponse = response.body();
                        User user = response2.body();
                        return new RepoSearchResponseAndUser(repoSearchResponse, user);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<RepoSearchResponseAndUser>() {
                    @Override
                    public void onNext(RepoSearchResponseAndUser repoSearchResponseAndUser) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}