package com.digitallabstudio.sandboxes.presenter.base.navigation

import androidx.annotation.LayoutRes
import com.digitallabstudio.sandboxes.presenter.base.mvvm.BaseMvvmFragment
import com.digitallabstudio.sandboxes.presenter.base.mvvm.BaseViewModel

abstract class NavMvvmFragment<D : Destination, VM : BaseViewModel>(@LayoutRes layoutId: Int) :
    BaseMvvmFragment<VM>(layoutId) {

    protected abstract fun handlerDestination(destination: D)
}
