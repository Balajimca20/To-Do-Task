package com.example.to_do_list.ui.detailscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.to_do_list.R

@Composable
@Preview(showBackground = true)
fun DetailScreenPreView() {
    DetailScreen(
        onEditClick = {},
        onBackClick = {},
        onDeleteClick = {},
        taskName = "taskName",
        startDate = "11/02/2024",
        description = "viewModel.description",
        assignedTo = "viewModel.assignedTo",
        status = "Completed",
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    onEditClick: () -> Unit,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit,
    taskName: String,
    startDate: String,
    description: String,
    status: String,
    assignedTo: String,

    ) {
    Scaffold(
        modifier = Modifier.background(Color.White),
        topBar = {
            TopAppBar(title = {
            },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onEditClick) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit"
                        )
                    }
                    IconButton(onClick = onDeleteClick) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "delete"
                        )
                    }
                })
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
                    .padding(16.dp),
            ) {
                Text(
                    text = taskName,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Row {
                    ContentScreen(
                        title = stringResource(id = R.string.start_date),
                        startDate = startDate
                    )
                    PriorityScreen(priorityType = status)
                }
                Spacer(modifier = Modifier.padding(8.dp))
                ContentScreen(
                    title = stringResource(id = R.string.description),
                    startDate = description
                )
                Spacer(modifier = Modifier.padding(8.dp))
                ContentScreen(
                    title = stringResource(id = R.string.assigned_to),
                    startDate = assignedTo
                )


            }
        }
    )
}

@Composable
fun ContentScreen(
    title: String,
    startDate: String,

    ) {
    Column {
        Text(
            text = title,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Text(
            text = startDate,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        )

    }
}

@Composable
fun PriorityScreen(
    priorityType: String
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = stringResource(id = R.string.priority_level),
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
        )
        Spacer(modifier = Modifier.padding(2.dp))
        if (priorityType.isNotBlank())
            Text(
                modifier = Modifier
                    .background(
                        Color(0xFFEB5757),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(
                        vertical =
                        4.dp, horizontal = 6.dp
                    ),
                text = priorityType,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Color.White,
            )

    }
}
