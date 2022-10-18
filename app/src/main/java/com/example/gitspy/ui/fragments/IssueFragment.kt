package com.example.gitspy.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitspy.R
import com.example.gitspy.adapters.IssueAdapter
import com.example.gitspy.ui.activities.StatsActivity
import com.example.gitspy.viewmodels.StatsViewModel
import kotlinx.android.synthetic.main.fragment_issues.*

class IssueFragment : Fragment(R.layout.fragment_issues) {

    lateinit var adapter: IssueAdapter
    lateinit var viewModel : StatsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as StatsActivity).viewModel

        viewModel.issue.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                adapter = IssueAdapter()
                adapter.submitList(it.reversed())
                issueRV.adapter = adapter
                issueRV.layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL , false)
            }
        })

    }
}