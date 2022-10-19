package com.example.gitspy.ui.fragments.prs_fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitspy.R
import androidx.lifecycle.Observer
import com.example.gitspy.adapters.PullRequestAdapter
import com.example.gitspy.models.pulls.PullRequests
import com.example.gitspy.ui.activities.StatsActivity
import com.example.gitspy.utility.Resource
import com.example.gitspy.viewmodels.StatsViewModel
import kotlinx.android.synthetic.main.fragment_merged_pr.*
import kotlinx.android.synthetic.main.fragment_open_pr.*

class MergedPrFragment : Fragment(R.layout.fragment_merged_pr) {

    lateinit var viewModel: StatsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel= (activity as StatsActivity).viewModel

        viewModel.closedPullRequests.observe(viewLifecycleOwner , Observer {

            when(it){

                is Resource.Success<PullRequests> -> {
                    val adapter = PullRequestAdapter("close")
                    adapter.submitList(it.data)
                    mergedPrRV.adapter = adapter
                    mergedPrRV.layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL , false)
                }
                is Resource.Error<PullRequests> -> {
                    Log.d("ABHI", "onViewCreated: Error Occures")
                }
            }
        })
    }
}