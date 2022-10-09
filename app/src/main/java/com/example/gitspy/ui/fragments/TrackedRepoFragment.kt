package com.example.gitspy.ui.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gitspy.R
import com.example.gitspy.adapters.RepoAdapter
import com.example.gitspy.adapters.TrackedRepoAdapter
import com.example.gitspy.ui.activities.MainActivity
import com.example.gitspy.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.faragment_tracked_repo.*

class TrackedRepoFragment :  Fragment(R.layout.faragment_tracked_repo) {

    private lateinit var viewModel : MainViewModel
    private lateinit var  adapter : TrackedRepoAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        adapter = TrackedRepoAdapter(requireContext())

        viewModel.trackedRepos.observe(viewLifecycleOwner , Observer {
            if (it!=null){
                adapter.submitList(it)
                trackedRepoRV.adapter = adapter
                trackedRepoRV.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            }

        })

        adapter.onCancelClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Un-track Repo")
                .setMessage("Are you sure , you want to un-track this repository...")
                .setPositiveButton("Yes" , DialogInterface.OnClickListener{
                    dialog , i -> viewModel.deleteRepo(item = it)
                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->  } )
                .create().show()

        }

    }
}