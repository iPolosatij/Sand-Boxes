package com.digitallabstudio.sandboxes.presenter.screens.start_screens

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.digitallabstudio.sandboxes.R
import com.digitallabstudio.sandboxes.databinding.WelcomeFragmentBinding
import com.digitallabstudio.sandboxes.presenter.base.navigation.NavMvvmFragment
import com.digitallabstudio.sandboxes.presenter.screens.errors.ErrorModel
import com.digitallabstudio.sandboxes.utils.extensions.collectWhenStarted
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class WelcomeFragment: NavMvvmFragment<AppDestination, WelcomeViewModel>(R.layout.welcome_fragment) {

    private  val args: WelcomeFragmentArgs by navArgs()

    private val logout: Boolean by lazy {
        args.logout
    }

    override val viewModel: WelcomeViewModel by sharedViewModel()

    private val binding: WelcomeFragmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeVm()
        setUpBinding()
        setUpBackPressed()
    }

    override fun handlerDestination(destination: AppDestination) {
    }

    private fun observeVm() {
        viewModel.apply {
            navigateCommander.collectWhenStarted(viewLifecycleOwner, ::handlerDestination)
            error.collectWhenStarted(viewLifecycleOwner, ::handlerError)

            message.observe(viewLifecycleOwner) {
                it.getFirstOrNull()?.let { message ->
                    binding.timer.text = message
                }
            }
        }
    }

    private fun setUpBinding(){
        binding.apply {
            start.setOnClickListener {
                if (start.text == "Start"){
                    start.text = "Stop"
                    viewModel.startTimer()
                }else{
                    start.text = "Start"
                    viewModel.stopTimer()
                }

            }
            stop.setOnClickListener {
                viewModel.counter = 0.00
                timer.text = "0"
            }
        }
    }

    private fun setUpBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)
    }



    private fun handlerError(ex: ErrorModel) {
        when (ex.exception) {

        }
    }
}
enum class WelcomeState{ Login, Registration}