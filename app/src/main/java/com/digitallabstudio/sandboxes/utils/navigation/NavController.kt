package com.digitallabstudio.sandboxes.utils.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

fun NavController.navigateSafe(
    directions: NavDirections
) {
    val action = currentDestination?.getAction(directions.actionId)
    if (action != null) {
        navigate(directions)
    }
}

fun NavController.navigateSafe(
    actionId: Int
) {
    val action = currentDestination?.getAction(actionId)
    if (action != null) {
        navigate(actionId)
    }
}

fun NavController.navigateSafe(
    deepLink: Uri
) {
    val action = graph.hasDeepLink(deepLink)
    if (action) {
        navigate(deepLink)
    }
}

fun NavController.navigateSafe(
    deepLink: Uri,
    navOptions: NavOptions
) {
    val action = graph.hasDeepLink(deepLink)
    if (action) {
        navigate(deepLink, navOptions)
    }
}
