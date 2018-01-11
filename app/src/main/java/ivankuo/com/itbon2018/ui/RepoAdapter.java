package ivankuo.com.itbon2018.ui;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Objects;

import ivankuo.com.itbon2018.data.model.Repo;
import ivankuo.com.itbon2018.databinding.RepoItemBinding;

public class RepoAdapter extends PagedListAdapter<Repo, RepoAdapter.RepoViewHolder> {

    RepoAdapter() {
        super(DIFF_CALLBACK);
    }

    class RepoViewHolder extends RecyclerView.ViewHolder{

        private final RepoItemBinding binding;

        RepoViewHolder(RepoItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Repo repo) {
            binding.setRepo(repo);
            binding.executePendingBindings();
        }
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RepoItemBinding binding = RepoItemBinding.inflate(layoutInflater, parent, false);
        return new RepoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RepoViewHolder holder, int position) {
        Repo repo = getItem(position);
        holder.bind(repo);
    }

    private static final DiffCallback<Repo> DIFF_CALLBACK = new DiffCallback<Repo>() {
        @Override
        public boolean areItemsTheSame(@NonNull Repo oldRepo, @NonNull Repo newRepo) {
            return Objects.equals(oldRepo.id, newRepo.id);
        }
        @Override
        public boolean areContentsTheSame(@NonNull Repo oldRepo, @NonNull Repo newRepo) {
            return Objects.equals(oldRepo.name, newRepo.name) &&
                    Objects.equals(oldRepo.description, newRepo.description);
        }
    };
}