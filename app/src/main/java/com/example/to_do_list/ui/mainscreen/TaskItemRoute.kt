package com.example.to_do_list.ui.mainscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.example.to_do_list.ui.MainViewModel
import com.example.to_do_list.ui.navigation.NavItem

@Composable
fun TaskItemRoute(
    viewModel: MainViewModel,
    navController: NavController,
) {

    val uiState by viewModel.uiState.collectAsState()

    TaskScreen(
        tabItem=uiState.tabItem,
        isLoading = uiState.isLoading,
        isPaginating = uiState.isPaginating,
        isEndReached = uiState.isEndReached,
        todosList = uiState.todosList,
        onViewClick = { item ->
            viewModel.selectedToDoItem(item)
            navController.navigate(NavItem.TaskDetail.route) {
                navController.graph.startDestinationRoute?.let { screenRoute ->
                    popUpTo(screenRoute) {
                        saveState = true
                    }
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        onEditClick = {
            viewModel.isEditable(false)
            navController.navigate(NavItem.AddTask.route) {
                navController.graph.startDestinationRoute?.let { screenRoute ->
                    popUpTo(screenRoute) {
                        saveState = true
                    }
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        getToDoItems = {
            viewModel.getToDoItems()
        },

    )
}
