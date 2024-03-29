package com.example.to_do_list.ui.addtaskscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.example.to_do_list.ui.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddTaskScreenRoute(
    viewModel: MainViewModel,
    navController: NavController,
) {

    val uiState by viewModel.uiState.collectAsState()

    AddTaskScreen(
        taskName = viewModel.taskName,
        startDate = viewModel.startDate,
        description = viewModel.description,
        priorityType = viewModel.priorityType,
        assignedTo = viewModel.assignedTo.username?:"",
        status = viewModel.status,
        onTaskValueChange = { viewModel.onTaskValueChange(it) },
        onDateChange = { viewModel.onDateChange(it) },
        onDescriptionValueChange = { viewModel.onDescriptionValueChange(it) },
        onPriorityValueChange = { viewModel.onPriorityValueChange(it) },
        onStatusValueChange = { viewModel.onStatusValueChange(it) },
        onAssignedToValueChange = { viewModel.onAssignedToValueChange(it) },
        priorityItem = viewModel.priorityItem,
        statusItem = viewModel.statusItem,
        assignedToItem = uiState.userList.map { it.username?:"" },
        onBackClick = { navController.navigateUp() },
        onSaveClick = {
            viewModel.onSaveClick()
            navController.navigateUp()
        }

    )
}