package com.example.gitspy.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitspy.R
import com.example.gitspy.adapters.CommitAdapter
import com.example.gitspy.models.commits.CommitList
import com.example.gitspy.ui.activities.StatsActivity
import com.example.gitspy.utility.Resource
import com.example.gitspy.viewmodels.StatsViewModel
import kotlinx.android.synthetic.main.fragment_commits.*

class CommitsFragment : Fragment(R.layout.fragment_commits) {

    lateinit var  viewModel: StatsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as StatsActivity).viewModel

        viewModel.commits.observe(viewLifecycleOwner , Observer {

            when(it){
                is Resource.Success<CommitList> -> {
                    val adapter = CommitAdapter()
                    adapter.submitList(it.data)
                    commitsRV.adapter = adapter
                    commitsRV.layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL  , false)
                }
                is Resource.Error<CommitList> -> {
                    Log.d("ABHI", "onViewCreated: Error happened")
                }

            }
        })
    }
}