
package com.symmetric.media_loading_demo.ui.base

import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.symmetric.media_loading_demo.utils.NetworkUtils
import dagger.android.support.DaggerAppCompatActivity

import javax.inject.Inject
import kotlin.reflect.KClass


abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : DaggerAppCompatActivity(),
    BaseFragment.Callback{


    protected var mViewDataBinding: T? = null

    protected lateinit var mViewModel: V


    @Inject
    lateinit var factory: ViewModelProvider.Factory

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract val bindingVariable: Int

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract val viewModel: KClass<V>

    val isNetworkConnected: Boolean
        get() = NetworkUtils.isNetworkConnected(applicationContext)

    /**
     * set up view and any necessary
     * binding or setting any views
     * for fetching the data.
     */
    abstract fun setUpView()

    /**
     * get any data sent to and needed
     * for fetching the data.
     */
    abstract fun emptyIntent()

    /**
     * fetch data
     */
    abstract fun fetchData()

    /**
     * listen to to variables (LiveData variables)
     * that will be updated with data,
     */
    abstract fun listenToVariables()

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()

        setUpView()
        emptyIntent()
        fetchData()
        listenToVariables()
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    fun openActivityOnTokenExpire() {
//        startActivity(LoginActivity.newIntent(this))
        finish()
    }


    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        mViewModel = ViewModelProviders.of(this, factory).get(viewModel.java)

        if (mViewDataBinding != null) {
            mViewDataBinding!!.setVariable(bindingVariable, mViewModel)
            mViewDataBinding!!.executePendingBindings()
        }
    }

}

