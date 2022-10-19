package com.example.gitspy.ui.fragments.issue_fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitspy.R
import com.example.gitspy.adapters.IssueNotificationAdapter
import com.example.gitspy.ui.activities.StatsActivity
import com.example.gitspy.viewmodels.StatsViewModel
import kotlinx.android.synthetic.main.fragment_issue_notifications.*

class IssueNotificationFragment : Fragment(R.layout.fragment_issue_notifications) {

    lateinit var  viewModel: StatsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as StatsActivity).viewModel

        viewModel.issue.observe(viewLifecycleOwner , Observer {
            val adapter = IssueNotificationAdapter()
            adapter.submitList(it.reversed())
            issueNotificationRV.adapter = adapter
            issueNotificationRV.layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL , false)
        })
    }
}