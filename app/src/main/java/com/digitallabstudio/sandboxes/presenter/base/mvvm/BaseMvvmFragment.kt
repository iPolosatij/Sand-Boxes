package com.digitallabstudio.sandboxes.presenter.base.mvvm

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import com.digitallabstudio.sandboxes.domain.model.exception.NetworkException
import com.digitallabstudio.sandboxes.presenter.base.BaseActivity
import com.digitallabstudio.sandboxes.presenter.base.BaseFragment
import com.digitallabstudio.sandboxes.utils.extensions.collectWhenStarted


abstract class BaseMvvmFragment<VM : BaseViewModel>(@LayoutRes layoutId: Int, private val observeProgress: Boolean = true, private val observeError: Boolean = true) : BaseFragment(layoutId) {

    protected abstract val viewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeBaseVm()
    }

    private fun observeBaseVm() {
        viewModel.apply {
            progressEvent.collectWhenStarted(viewLifecycleOwner) {
                if (observeProgress) {
                    (requireActivity() as BaseActivity).handlerProgress(it)
                }
            }

            errorEvent.collectWhenStarted(viewLifecycleOwner) {
                if (it.exception !is NetworkException.Unauthorized && observeError) {
                    (requireActivity() as BaseActivity).handlerErrors(it, childFragmentManager)
                }
            }
        }
    }
}