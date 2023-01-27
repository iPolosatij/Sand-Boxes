package com.digitallabstudio.sandboxes.presenter.base

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.digitallabstudio.sandboxes.presenter.screens.errors.ErrorModel

abstract class BaseActivity(@LayoutRes layoutId: Int) : AppCompatActivity(layoutId) {

    abstract fun handlerProgress(visibility: Boolean)

    abstract fun handlerErrors(error: ErrorModel, fragmentManager: FragmentManager = supportFragmentManager)
}