package ivankuo.com.itbon2018.ui;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Objects;

import ivankuo.com.itbon2018.data.model.Repo;
import ivankuo.com.itbon2018.databinding.RepoItemBinding;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {

    private List<Repo> items;

    RepoAdapter(List<Repo> items) {
        this.items = items;
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
        Repo repo = items.get(position);
        holder.bind(repo);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void clearItems() {
        int size = this.items.size();
        this.items.clear();
        notifyItemRangeRemoved(0, size);
    }

    void swapItems(List<Repo> newItems) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new RepoDiffCallback(this.items, newItems));
        this.items.clear();
        this.items.addAll(newItems);
        result.dispatchUpdatesTo(this);
    }

    private class RepoDiffCallback extends DiffUtil.Callback {

        private List<Repo> mOldList;
        private List<Repo> mNewList;

        RepoDiffCallback(List<Repo> oldList, List<Repo> newList) {
            this.mOldList = oldList;
            this.mNewList = newList;
        }

        @Override
        public int getOldListSize() {
            return mOldList != null ? mOldList.size() : 0;
        }

        @Override
        public int getNewListSize() {
            return mNewList != null ? mNewList.size() : 0;
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            int oldId = mOldList.get(oldItemPosition).id;
            int newId = mNewList.get(newItemPosition).id;
            return Objects.equals(oldId, newId);
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            Repo oldRepo = mOldList.get(oldItemPosition);
            Repo newRepo = mNewList.get(newItemPosition);
            return Objects.equals(oldRepo, newRepo);
        }
    }
}