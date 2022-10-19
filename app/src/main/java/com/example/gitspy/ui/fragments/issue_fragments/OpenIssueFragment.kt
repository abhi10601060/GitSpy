package com.example.gitspy.ui.fragments.issue_fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitspy.R
import com.example.gitspy.adapters.IssueAdapter
import com.example.gitspy.models.issues.Issues
import com.example.gitspy.ui.activities.StatsActivity
import com.example.gitspy.utility.Resource
import com.example.gitspy.viewmodels.StatsViewModel
import kotlinx.android.synthetic.main.fragment_open_issue.*

class OpenIssueFragment : Fragment(R.layout.fragment_open_issue) {

    lateinit var viewModel: StatsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as StatsActivity).viewModel

        viewModel.OpenIssues.observe(viewLifecycleOwner , Observer {

            when(it){

                is Resource.Success<Issues> -> {
                    Log.d("ABHI", "onViewCreated: success")
                    val adapter = IssueAdapter()
                    adapter.submitList(it.data)
                    openIssueRV.adapter = adapter
                    openIssueRV.layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL , false)
                }
                is Resource.Error<Issues> ->{
                    Log.d("ABHI", "onViewCreated: Error occured")
                }
            }



        })
    }
}