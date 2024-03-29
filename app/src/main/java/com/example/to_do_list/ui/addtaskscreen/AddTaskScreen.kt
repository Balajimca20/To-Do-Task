package com.example.to_do_list.ui.addtaskscreen

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.to_do_list.R
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

@Preview(showBackground = true)
@Composable
fun AddTaskScreenPreview() {
    AddTaskScreen(
        taskName = "taskName",
        startDate = "viewModel.startDate",
        description = "viewModel.description",
        priorityType = "viewModel.priorityType",
        assignedTo = "viewModel.assignedTo",
        status = "viewModel.assignedTo",
        onTaskValueChange = {},
        onDateChange = {},
        onDescriptionValueChange = {},
        onPriorityValueChange = {},
        onStatusValueChange = {},
        onAssignedToValueChange = {},
        priorityItem = arrayListOf(),
        statusItem = arrayListOf(),
        assignedToItem = arrayListOf(),
        onBackClick = {},
        onSaveClick = {},

        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    taskName: String,
    startDate: String,
    description: String,
    priorityType: String,
    status: String,
    assignedTo: String,
    onTaskValueChange: (String) -> Unit,
    onDateChange: (Date) -> Unit,
    onDescriptionValueChange: (String) -> Unit,
    onPriorityValueChange: (String) -> Unit,
    onAssignedToValueChange: (String) -> Unit,
    onStatusValueChange: (String) -> Unit,
    priorityItem: List<String>,
    statusItem: List<String>,
    assignedToItem: List<String>,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
) {
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        snackbarHost = { SnackbarHost(snackBarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(all = 16.dp),
                onClick = {
                    if (taskName.isBlank() || startDate.isBlank() || description.isBlank() ||
                        priorityType.isBlank() || status.isBlank() || assignedTo.isBlank()
                    )
                        scope.launch {
                            snackBarHostState.showSnackbar("Validation error")
                        }
                    else onSaveClick()
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Check"
                )

            }
        },
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(id = R.string.create_new_to_do_list))
            },
                navigationIcon = {
                    IconButton(onClick = {
                        onBackClick()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "close"
                        )
                    }
                })
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                CreateTaskField(
                    labelName = stringResource(id = R.string.task_name),
                    selectedText = taskName,
                    onValueChange = onTaskValueChange
                )
                Text(text = stringResource(id = R.string.start_date))
                TextField(
                    value = startDate,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = {
                            showDatePickerDialog(
                                context = context,
                                datePicked = onDateChange
                            )
                        }) {
                            Icon(imageVector = Icons.Default.DateRange, contentDescription = "date")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                CreateTaskField(
                    labelName = stringResource(id = R.string.description),
                    selectedText = description,
                    onValueChange = onDescriptionValueChange
                )

                Column {
                    Text(text = stringResource(id = R.string.priority_level))
                    ExposedDropdownMenuBox(
                        selectedText = priorityType,
                        onValueChange = onPriorityValueChange,
                        dropDownItem = priorityItem
                    )
                }

                Column {
                    Text(text = stringResource(id = R.string.status))
                    ExposedDropdownMenuBox(
                        selectedText = status,
                        onValueChange = onStatusValueChange,
                        dropDownItem = statusItem
                    )
                }
                Column {
                    Text(text = stringResource(id = R.string.assigned_to))
                    ExposedDropdownMenuBox(
                        selectedText = assignedTo,
                        onValueChange = onAssignedToValueChange,
                        dropDownItem = assignedToItem
                    )
                }

            }
        }
    )
}

@Composable
fun CreateTaskField(
    labelName: String,
    selectedText: String,
    onValueChange: (String) -> Unit
) {
    Text(
        text = labelName
    )
    TextField(
        value = selectedText,
        onValueChange = {
            onValueChange(it)
        },
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBox(
    selectedText: String,
    onValueChange: (String) -> Unit,
    dropDownItem: List<String>,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                dropDownItem.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            onValueChange(item)
                            //  selectedText = item
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}


fun showDatePickerDialog(
    context: Context,
    datePicked: (Date) -> Unit,
) {
    val cal = Calendar.getInstance()
    val year: Int
    val month: Int
    val day: Int
    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, yearPick: Int, monthPick: Int, dayOfMonth: Int ->
            val days = if (dayOfMonth < 10) "0$dayOfMonth"
            else "$dayOfMonth"
            val date = "$yearPick-${monthPick + 1}-$days"
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthPick)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            datePicked(cal.time)
            //  datePicked(date)
        }, year, month, day
    )
    datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
    datePickerDialog.show()
}
