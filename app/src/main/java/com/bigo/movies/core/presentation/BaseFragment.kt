package com.bigo.movies.core.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

abstract class BaseFragment : Fragment() {

    abstract val layoutRes: Int

    private val loadingVisibleObserver = Observer<Boolean> {
        setLoadingVisible(it)
    }

    private val errorObserver = Observer<String> {
        showError(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutRes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView(view)
    }

    abstract fun initView(view: View)

    protected fun observeLoadingVisible(viewModel: BaseViewModel, owner: LifecycleOwner) {
        viewModel.loadingVisibleLiveData.observe(owner, loadingVisibleObserver)
    }

    protected fun observeError(viewModel: BaseViewModel, owner: LifecycleOwner) {
        viewModel.errorLiveData.observe(owner, errorObserver)
    }

    protected open fun setLoadingVisible(visible: Boolean) {}

    protected open fun showError(error: String) {}
}