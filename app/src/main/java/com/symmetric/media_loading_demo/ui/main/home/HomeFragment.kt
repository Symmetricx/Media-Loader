package com.symmetric.media_loading_demo.ui.main.home


import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.symmetric.media_loading_demo.BR
import com.symmetric.media_loading_demo.ui.base.BaseFragment

import com.symmetric.media_loading_demo.R
import com.symmetric.media_loading_demo.data.remote.Status
import com.symmetric.media_loading_demo.databinding.HomeFragmentBinding
import com.symmetric.media_loading_demo.utils.AppLogger
import com.symmetric.medialoader.MediaLoader
import com.symmetric.medialoader.network.RequestHandler


import kotlin.reflect.KClass

class HomeFragment(
    override val bindingVariable: Int = BR.viewModel,
    override val layoutId: Int = R.layout.home_fragment,
    override val viewModel: KClass<HomeViewModel> = HomeViewModel::class
) : BaseFragment<HomeFragmentBinding, HomeViewModel>(),
    HomeNavigator {
    private val adapter = PicturesAdapter(ArrayList())

    override fun setUpView() {
        mViewModel.setNavigator(this)


        val linearLayoutManager = context?.let { LinearLayoutManager(it) }
        mViewDataBinding!!.list.layoutManager = linearLayoutManager
        mViewDataBinding!!.list.itemAnimator = DefaultItemAnimator()
        mViewDataBinding?.list?.adapter = adapter
    }

    override fun emptyIntent() {
    }

    override fun fetchData() {
    }

    override fun listenToVariables() {
        mViewModel.pictures.observe(this, Observer { picturesResult ->
            if (picturesResult.status == Status.SUCCESS && picturesResult.data != null)
            {
                picturesResult.data.count().let { Toast.makeText(context, it.toString(),Toast.LENGTH_LONG) }
                picturesResult.data?.let { adapter.addItems(it) }
            }

        })
    }

}
