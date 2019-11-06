package com.annada.android.sample.squarerepos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.annada.android.sample.squarerepos.R
import com.annada.android.sample.squarerepos.databinding.FragmentReposListBinding
import com.google.android.material.snackbar.Snackbar


class ReposListFragment : Fragment() {

    companion object {
        fun newInstance() = ReposListFragment()
    }

    private lateinit var viewModel: RepoListViewModel
    private lateinit var binding: FragmentReposListBinding
    private var errorSnackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProviders.of(this, ViewModelFactory(activity as AppCompatActivity))
            .get(RepoListViewModel::class.java)
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })



        binding = DataBindingUtil.inflate(
            inflater,
            com.annada.android.sample.squarerepos.R.layout.fragment_repos_list,
            container,
            false
        )
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        binding.reposList.layoutManager = linearLayoutManager
        val dividerItemDecoration = DividerItemDecoration(
            binding.reposList.getContext(),
            linearLayoutManager.getOrientation()
        )
        binding.reposList.addItemDecoration(dividerItemDecoration)

        binding.viewModel = viewModel


        return binding.root
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }
}