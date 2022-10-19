package com.example.gitspy.ui.fragments.prs_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitspy.R
import com.example.gitspy.adapters.PrNotificationAdapter
import com.example.gitspy.ui.activities.StatsActivity
import com.example.gitspy.viewmodels.StatsViewModel
import kotlinx.android.synthetic.main.fragment_pr_notification.*

class PrNotificationsFragment : Fragment(R.layout.fragment_pr_notification) {

    lateinit var  viewModel: StatsViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as StatsActivity).viewModel

        viewModel.savedPrNotifications.observe(viewLifecycleOwner , Observer {
            val adapter = PrNotificationAdapter()
            adapter.submitList(it.reversed())
            prNotificationRV.adapter = adapter
            prNotificationRV.layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL , false)
        })

    }
}