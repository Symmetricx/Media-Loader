
package com.symmetric.media_loading_demo.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment

import javax.inject.Inject
import kotlin.reflect.KClass


abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel<*>> : DaggerFragment() {

    var baseActivity: BaseActivity<*, *>? = null

    private var mRootView: View? = null

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
        get() = baseActivity != null && baseActivity!!.isNetworkConnected

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            val activity = context as BaseActivity<*, *>?
            this.baseActivity = activity
            activity!!.onFragmentAttached()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this, factory).get(viewModel.java)
        setHasOptionsMenu(false)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mRootView = mViewDataBinding?.getRoot()
        return mRootView
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding?.setVariable(bindingVariable, mViewModel)
        mViewDataBinding?.lifecycleOwner = this
        mViewDataBinding?.executePendingBindings()

        setUpView()
        emptyIntent()
        fetchData()
        listenToVariables()
    }

    fun hideKeyboard() {
        if (baseActivity != null) {
            baseActivity!!.hideKeyboard()
        }
    }

    fun openActivityOnTokenExpire() {
        if (baseActivity != null) {
            baseActivity!!.openActivityOnTokenExpire()
        }
    }

    private fun performDependencyInjection() {
        AndroidSupportInjection.inject(this)
    }

    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }
}
