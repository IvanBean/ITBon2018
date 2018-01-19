package ivankuo.com.itbon2018.ui

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import java.util.Objects

import ivankuo.com.itbon2018.data.model.Repo
import ivankuo.com.itbon2018.databinding.RepoItemBinding

class RepoAdapter constructor(private val items: MutableList<Repo>?) : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    inner class RepoViewHolder(private val binding: RepoItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(repo: Repo) {
            binding.repo = repo
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RepoItemBinding.inflate(layoutInflater, parent, false)
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = items!![position]
        holder.bind(repo)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    fun swapItems(newItems: List<Repo>?) {
        if (newItems == null) {
            val oldSize = this.items!!.size
            this.items.clear()
            notifyItemRangeRemoved(0, oldSize)
        } else {
            val result = DiffUtil.calculateDiff(RepoDiffCallback(this.items, newItems))
            this.items!!.clear()
            this.items.addAll(newItems)
            result.dispatchUpdatesTo(this)
        }
    }

    private inner class RepoDiffCallback internal constructor(private val mOldList: List<Repo>?, private val mNewList: List<Repo>?) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return mOldList?.size ?: 0
        }

        override fun getNewListSize(): Int {
            return mNewList?.size ?: 0
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldId = mOldList!![oldItemPosition].id
            val newId = mNewList!![newItemPosition].id
            return oldId == newId
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldRepo = mOldList!![oldItemPosition]
            val newRepo = mNewList!![newItemPosition]
            return oldRepo == newRepo
        }
    }
}