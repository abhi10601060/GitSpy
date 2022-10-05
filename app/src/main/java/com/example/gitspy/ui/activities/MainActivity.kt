package com.example.gitspy.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.gitspy.R
import com.example.gitspy.utility.GitSpyApplication
import com.example.gitspy.viewmodels.MainViewModel
import com.example.gitspy.viewmodels.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationBar.setupWithNavController(navController)

        val repository = (application as GitSpyApplication).repository
        viewModel = ViewModelProvider(this , MainViewModelFactory(repository)).get(MainViewModel::class.java)

    }
}