package com.digitallabstudio.sandboxes.presenter.screens.start_screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.digitallabstudio.sandboxes.R
import com.digitallabstudio.sandboxes.data.room.data.Bd_data
import com.digitallabstudio.sandboxes.databinding.WelcomeFragmentBinding
import com.digitallabstudio.sandboxes.presenter.base.adapter.MultiItemsAdapter
import com.digitallabstudio.sandboxes.presenter.base.navigation.NavMvvmFragment
import com.digitallabstudio.sandboxes.presenter.screens.ListItem
import com.digitallabstudio.sandboxes.presenter.screens.errors.ErrorModel
import com.digitallabstudio.sandboxes.utils.extensions.collectWhenStarted
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class WelcomeFragment: NavMvvmFragment<AppDestination, WelcomeViewModel>(R.layout.welcome_fragment) {

    private  val args: WelcomeFragmentArgs by navArgs()

    private val logout: Boolean by lazy {
        args.logout
    }

    private val adapterList by lazy { MultiItemsAdapter(listOf(ListItem(::onClick))) }

    private fun onClick(bd: Bd_data){
        Toast.makeText(requireContext(), bd.name, Toast.LENGTH_SHORT ).show()
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

                }
            }
        }
    }

    private fun setUpBinding(){
        binding.apply {
            list.adapter = adapterList
            var list = listOf(
                Bd_data("1", "Alex"),
                Bd_data("2", "Sergey"),
                Bd_data("3", "Oleg"),
                Bd_data("4", "Vitali"),
                Bd_data("5", "Nik"),
                Bd_data("6", "Anton"),
                Bd_data("7", "Petr"),
                Bd_data("8", "Alex"),
                Bd_data("9", "Alex"),
                Bd_data("10", "Alex"),
                Bd_data("11", "Alex"),
                Bd_data("12", "Alex"),
                Bd_data("13", "Alex"),
                Bd_data("14", "Alex"),
                Bd_data("15", "Alex"),
                Bd_data("16", "Alex"),
                Bd_data("17", "Alex"),
                Bd_data("18", "Alex"),
                Bd_data("19", "Alex"),
                Bd_data("20", "Alex")
            )
            adapterList.submitList(list)
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