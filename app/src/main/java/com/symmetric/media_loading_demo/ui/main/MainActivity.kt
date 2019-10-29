package com.symmetric.media_loading_demo.ui.main

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.symmetric.media_loading_demo.BR
import com.symmetric.media_loading_demo.ui.base.BaseActivity
import com.symmetric.media_loading_demo.R
import com.symmetric.media_loading_demo.databinding.ActivityMainBinding
import kotlin.reflect.KClass

class MainActivity(
    override val bindingVariable: Int = BR.viewModel,
    override val layoutId: Int = R.layout.activity_main,
    override val viewModel: KClass<MainViewModel> = MainViewModel::class
) : BaseActivity<ActivityMainBinding, MainViewModel>(),
    MainNavigator,
    View.OnClickListener {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun setUpView() {
        mViewModel.setNavigator(this)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)


        appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

    override fun emptyIntent() {
    }

    override fun fetchData() {
    }

    override fun listenToVariables() {
    }

    override fun onClick(v: View?) {
        if (v == null) return

        when (v.id) {
        }
    }

}
