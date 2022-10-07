package com.example.gitspy.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitspy.R
import com.example.gitspy.adapters.RepoAdapter
import com.example.gitspy.models.RepoList
import com.example.gitspy.ui.activities.MainActivity
import com.example.gitspy.utility.Resource
import com.example.gitspy.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_repository.*

class RepositoryFragment : Fragment(R.layout.fragment_repository) {

    private lateinit var viewModel : MainViewModel
    private lateinit var adapter: RepoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        repoSearchButton.setOnClickListener(View.OnClickListener {
            val repoName = repoSearchBox.text.toString()
            viewModel.getRepos(repoName)
        })

        adapter = RepoAdapter(requireContext())
        viewModel.repoList.observe(viewLifecycleOwner , Observer {
            when(it){

                is Resource.Success<RepoList> -> {
                    searchRepoProgressBar.visibility = View.GONE
                    adapter.submitList(it.data?.items)
                    searchRepoRV.adapter = adapter
                    searchRepoRV.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                }

                is Resource.Loading<RepoList> -> {
                   searchRepoProgressBar.visibility = View.VISIBLE
                }

                is Resource.Error<RepoList> -> {
                    searchRepoProgressBar.visibility = View.GONE
                    Log.d("ABHI", "onViewCreated: Error happened during fetching repos...")
                }

            }
        })





    }
}