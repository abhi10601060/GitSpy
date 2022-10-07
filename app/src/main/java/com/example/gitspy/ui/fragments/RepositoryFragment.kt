package com.example.gitspy.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.gitspy.R
import com.example.gitspy.models.RepoList
import com.example.gitspy.ui.activities.MainActivity
import com.example.gitspy.utility.Resource
import com.example.gitspy.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_repository.*

class RepositoryFragment : Fragment(R.layout.fragment_repository) {

    private lateinit var viewModel : MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        viewModel.getRepos("Karter")

        viewModel.repoList.observe(viewLifecycleOwner , Observer {
            if (it is Resource.Success<RepoList>){
                repotext.text = it.data?.items.toString()
            }
        })

    }
}