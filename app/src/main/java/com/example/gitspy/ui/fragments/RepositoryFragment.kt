package com.example.gitspy.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitspy.R
import com.example.gitspy.adapters.RepoAdapter
import com.example.gitspy.models.Item
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
            hideKeyboard()
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

        adapter.setOnItemClickListener {
            val bundle = Bundle()
            bundle.putString("url" , it.html_url)
            findNavController().navigate(R.id.action_repositoryFragment_to_githubFragment , bundle)
        }

        adapter.setOnTrackClickListener {
            Log.d("ABHI", "onViewCreated: ${it.toString()}")
            viewModel.addToTrack(it)
        }

    }
    fun hideKeyboard(){
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken , 0)
    }


}