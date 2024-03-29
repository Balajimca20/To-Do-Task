package com.example.to_do_list.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.to_do_list.ui.MainViewModel
import com.example.to_do_list.ui.addtaskscreen.AddTaskScreenRoute
import com.example.to_do_list.ui.mainscreen.TaskItemRoute
import com.example.to_do_list.ui.detailscreen.DetailRoute

@Composable
fun NavigationGraph(
    navController: NavHostController,
    viewModel: MainViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = NavItem.Todo.route
    ) {
        composable(NavItem.Todo.route) {
            TaskItemRoute(
                viewModel = viewModel,
                navController = navController,
            )
        }
        composable(NavItem.TaskDetail.route) {
            DetailRoute(
                viewModel = viewModel,
                navController = navController,
            )
        }
        composable(NavItem.AddTask.route) {
            AddTaskScreenRoute(
                viewModel=viewModel,
                navController = navController,
            )
        }

    }
}
