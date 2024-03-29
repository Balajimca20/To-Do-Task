package com.example.to_do_list.ui.mainscreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.to_do_list.R
import com.example.to_do_list.data.model.TodosList
import com.example.to_do_list.ui.MainViewModel

@Composable
@Preview(showBackground = true)
fun TaskScreenPreView() {
    TaskScreen(
        onViewClick = {},
        onEditClick = {},
        getToDoItems = {},
        isLoading = false,
        isPaginating = false,
        isEndReached = false,
        todosList = arrayListOf(),
        tabItem = arrayListOf(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    onViewClick: (TodosList) -> Unit,
    onEditClick: () -> Unit,
    isEndReached: Boolean,
    isLoading: Boolean,
    isPaginating: Boolean,
    todosList: List<TodosList>,
    tabItem: List<MainViewModel.TabItem>,
    getToDoItems: () -> Unit,
) {
    val listState = rememberLazyListState()
    val layoutInfo = remember { derivedStateOf { listState.layoutInfo } }
    val isNeedPaginate =
        remember {
            derivedStateOf {
                layoutInfo.value.visibleItemsInfo.lastOrNull()?.index == layoutInfo.value.totalItemsCount - 2
            }
        }
    if (isNeedPaginate.value && !isEndReached && !isPaginating) {
        getToDoItems()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Column {
                    UserProfileScreen(userName = "User Name")
                }

            })
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(all = 16.dp),
                onClick = {
                    onEditClick()
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Check"
                )

            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column {
                    LazyRow {
                        items(tabItem) { item ->
                            TabItemScreen(
                                title = item.title.plus(" " + item.count)
                            )
                        }
                    }
                    LazyColumn(
                        modifier = Modifier.simpleVerticalScrollbar(listState),
                        state = listState
                    ) {
                        itemsIndexed(
                            todosList,
                        ) { index, item ->
                            TodoItem(
                                index = index,
                                title = item.taskName ?: "",
                                description = item.description ?: "",
                                status = item.status ?: "",
                                onItemClick = { onViewClick(item) },
                            )
                        }
                        item {
                            if (isPaginating) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        .padding(vertical = 10.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    CircularProgressIndicator(
                                        color = Color.Red
                                    )
                                }
                            }
                        }
                    }
                }

            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun UserProfileScreenPreview() {
    UserProfileScreen(
        userName = "User Name"
    )
}

@Composable
fun UserProfileScreen(
    userName: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier.size(48.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = Color.Gray,
                contentColor = Color.Gray,
            )
        ) {

        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                )
        ) {
            Text(
                text = "Hi, $userName",
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
            )
            Text(
                text = "Here's What's on the lis today",
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color.Gray
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun TabItemScreenPreview() {
    TabItemScreen(
        title = "All100"
    )
}

@Composable
fun TabItemScreen(
    title: String,
) {
    Surface(
        modifier = Modifier.padding(8.dp),
        border = BorderStroke(1.dp, Color.Black),
        shape = RoundedCornerShape(4.dp)

    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = title
        )
    }

}

@Composable
fun TodoItem(
    index: Int,
    title: String,
    description: String,
    status: String,
    onItemClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick() },
        colors = CardDefaults.cardColors(
            containerColor = getColorItem(index)
        ),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Start,
                    color = Color.Black
                )
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.flag),
                    contentDescription = "",
                    tint = when (status) {
                        MainViewModel.SessionStatus.PENDING.value -> Color(0xFFF2994A)
                        MainViewModel.SessionStatus.ONGOING.value -> Color(0xFFEB5757)
                        MainViewModel.SessionStatus.COMPLETED.value -> Color(0xFF27AE60)
                        else -> Color(0xFFF2994A)
                    }
                )
                Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.schedule_icon),
                    contentDescription = "schedule_icon",
                    tint = Color(0xFF333333)
                )
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                text = description,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start,
                color = Color.Gray
            )
        }

    }
}

fun getColorItem(index: Int): Color {
    return when (index % 3) {
        0 -> Color(0xFFCFF2FF)
        1 -> Color(0xFFFFE2C3)
        2 -> Color(0xFFFFCAD0)
        else -> Color(0xFFCFF2FF)
    }
}


fun Modifier.simpleVerticalScrollbar(
    state: LazyListState,
    width: Dp = 8.dp
): Modifier = composed {
    composed {
        val targetAlpha = if (state.isScrollInProgress) 1f else 0f
        val duration = if (state.isScrollInProgress) 150 else 500
        val alpha by animateFloatAsState(
            targetValue = targetAlpha,
            animationSpec = tween(durationMillis = duration)
        )
        drawWithContent {
            drawContent()
            val firstVisibleElementIndex = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index
            val needDrawScrollbar = state.isScrollInProgress || alpha > 0.0f
            if (needDrawScrollbar && firstVisibleElementIndex != null) {
                val elementHeight = this.size.height / state.layoutInfo.totalItemsCount
                val scrollbarOffsetY = firstVisibleElementIndex * elementHeight
                val scrollbarHeight = state.layoutInfo.visibleItemsInfo.size * elementHeight
                drawRoundRect(
                    color = Color.LightGray,
                    topLeft = Offset(this.size.width - width.toPx(), scrollbarOffsetY),
                    size = Size(width.toPx(), scrollbarHeight),
                    alpha = alpha,
                    cornerRadius = CornerRadius(x = 36.dp.toPx(), y = 36.dp.toPx())
                )
            }
        }
    }
}

