package com.example.to_do_list.ui.navigation

import com.example.to_do_list.R


sealed class NavItem(
    val title: Int,
    val icon: Int,
    val route: String,
) {
    object Todo : NavItem(
        title = R.string.home,
        icon  = R.drawable.ic_launcher_foreground,
        route = "todo",
    )
    object TaskDetail : NavItem(
        title = R.string.task,
        icon  = R.drawable.ic_launcher_foreground,
        route = "detail",
    )
    object AddTask : NavItem(
        title = R.string.home,
        icon  = R.drawable.ic_launcher_foreground,
        route = "AddTask",
    )
}