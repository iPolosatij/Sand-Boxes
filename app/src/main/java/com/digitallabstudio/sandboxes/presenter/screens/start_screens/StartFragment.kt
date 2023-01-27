package com.digitallabstudio.sandboxes.presenter.screens.start_screens

import android.os.Bundle
import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.digitallabstudio.sandboxes.R
import com.digitallabstudio.sandboxes.databinding.StartFragmentBinding
import com.digitallabstudio.sandboxes.presenter.base.navigation.NavMvvmFragment
import com.digitallabstudio.sandboxes.presenter.screens.errors.ErrorModel
import com.digitallabstudio.sandboxes.utils.extensions.collectWhenStarted
import com.digitallabstudio.sandboxes.utils.navigation.navigateSafe
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class StartFragment: NavMvvmFragment<AppDestination, StartViewModel>(R.layout.start_fragment) {

    override val viewModel: StartViewModel by sharedViewModel()

    private val binding: StartFragmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeVm()
    }



    private fun observeVm() {
        viewModel.apply {
            navigateCommander.collectWhenStarted(viewLifecycleOwner, ::handlerDestination)
            error.collectWhenStarted(viewLifecycleOwner, ::handlerError)
        }
    }

    override fun handlerDestination(destination: AppDestination) {
        val action: NavDirections? = when(destination){
            AppDestination.ToWelcome -> StartFragmentDirections.toWelcomeFrag()
            else -> null
        }
        action?.let { findNavController().navigateSafe(it) }
    }

    private fun handlerError(ex: ErrorModel) {
        when (ex.exception) {

        }
    }
}