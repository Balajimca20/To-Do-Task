package com.example.to_do_list.ui.detailscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.example.to_do_list.ui.MainViewModel
import com.example.to_do_list.ui.navigation.NavItem

@Composable
fun DetailRoute(
    viewModel: MainViewModel,
    navController: NavController,
) {

    val uiState by viewModel.uiState.collectAsState()

    DetailScreen(
        onEditClick = {
            viewModel.isEditable(true)
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
        onBackClick = {
            navController.navigateUp()
        },
        onDeleteClick = {
            viewModel.onDeleteClick()
            navController.navigateUp()
        },
        taskName = uiState.selectedItem?.taskName ?: "",
        startDate = uiState.selectedItem?.startDate ?: "",
        description = uiState.selectedItem?.description ?: "",
        assignedTo = if (uiState.selectedItem?.assignedTo?.isBlank() == true) viewModel.userName else uiState.selectedItem?.assignedTo
            ?: "",
        status = uiState.selectedItem?.status ?: "",
    )
}
