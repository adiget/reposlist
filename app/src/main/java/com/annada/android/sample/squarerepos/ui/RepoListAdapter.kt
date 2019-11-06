package com.annada.android.sample.squarerepos.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.annada.android.sample.squarerepos.R
import com.annada.android.sample.squarerepos.databinding.ItemRepoBinding
import com.annada.android.sample.squarerepos.db.entities.Repo

class RepoListAdapter : RecyclerView.Adapter<RepoListAdapter.ViewHolder>() {
    private lateinit var repoList:List<Repo>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemRepoBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_repo, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoListAdapter.ViewHolder, position: Int) {
        holder.bind(repoList[position])
    }

    fun updateRepoList(repoList:List<Repo>){
        this.repoList = repoList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if(::repoList.isInitialized) repoList.size else 0
    }


    class ViewHolder(private val binding: ItemRepoBinding):RecyclerView.ViewHolder(binding.root){
        private val viewModel = RepoViewModel()

        fun bind(repo:Repo){
            viewModel.bind(repo)
            binding.viewModel = viewModel
        }
    }
}