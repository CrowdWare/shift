package at.crowdware.shift.ui.widgets

import androidx.navigation.NavController

object NavigationManager {
    private var navController: NavController? = null

    fun setNavController(controller: NavController) {
        navController = controller
    }

    fun navigate(route: String) {
        navController!!.navigate(route)
    }
}