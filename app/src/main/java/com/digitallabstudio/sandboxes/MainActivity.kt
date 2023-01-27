package com.digitallabstudio.sandboxes

import androidx.fragment.app.FragmentManager
import com.digitallabstudio.sandboxes.presenter.base.BaseActivity
import com.digitallabstudio.sandboxes.presenter.screens.errors.ErrorModel
import com.digitallabstudio.sandboxes.utils.auxiliary.FragmentCloseInterface

class MainActivity() : BaseActivity(R.layout.activity_main), FragmentCloseInterface {

    override fun handlerProgress(visibility: Boolean) {
    }

    override fun handlerErrors(error: ErrorModel, fragmentManager: FragmentManager) {
    }
}