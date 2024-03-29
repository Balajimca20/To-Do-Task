package com.example.to_do_list.ui


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_do_list.data.model.TodosList
import com.example.to_do_list.data.model.users.UserList
import com.example.to_do_list.pagingSource.PagingSource
import com.example.to_do_list.repository.ToDoListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainViewModel(
    private val repository: ToDoListRepository,
) : ViewModel() {

    var taskName by mutableStateOf("")
        private set
    var startDate by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set
    var priorityType by mutableStateOf("")
        private set
    var assignedTo by mutableStateOf(UserList())
        private set
    var status by mutableStateOf("")
        private set
    var isEditable = false
    var userName by mutableStateOf("")
        private set


    val priorityItem = listOf("Low", "Medium", "High")
    val statusItem = listOf("ToDo ", "In-Progress", "Completed")


    private var currentPage: Int = 1
    private var totalPages: Int = 1

    private val viewModelState = MutableStateFlow(DashboardViewModelState(isLoading = true))
    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, viewModelState.value.toUiState())


    private val pagination = PagingSource(
        initialKey = 1,
        onLoadUpdated = { loadState ->
            viewModelState.update { it.copy(isPaginating = if (!viewModelState.value.isLoading) loadState else false) }
        },
        onRequest = { nextPage ->
            repository.toDoResponseResult(
                nextPage,
            )
        },
        getNextKey = {
            currentPage + 1
        },
        onError = { viewModelState.update { it.copy(isLoading = false) } },
        onSuccess = { items, newKey ->
            currentPage = newKey
            totalPages = items.totalPages
            viewModelState.update {
                it.copy(
                    lastUpdate = System.currentTimeMillis(),
                    isLoading = false,
                )
            }
        }
    )

    init {
        getUserData()
        getToDoItems()
        getDataItem()
    }

    private fun getUserData() {
        viewModelScope.launch {
            repository.getUsers()
            repository.getUserList.collect { item ->
                viewModelState.update {
                    it.copy(
                        isPaginating = false,
                        userList = item
                    )
                }
            }
        }
    }

    private fun getDataItem() {
        viewModelScope.launch {
            repository.getToDoList.collect { item ->
                viewModelState.update {
                    it.copy(
                        isPaginating = false,
                        todosList = item,
                        tabItem= listOf(TabItem(title="ALL", count = item.size),
                                TabItem(title=SessionStatus.ONGOING.value, count = item.count { item->item.status==SessionStatus.ONGOING.value }),
                                TabItem(title=SessionStatus.COMPLETED.value, count = item.count { item->item.status==SessionStatus.COMPLETED.value }))
                    )
                }
            }
        }
    }

    fun getToDoItems() {
        viewModelScope.launch {
            if (currentPage <= totalPages) {
                viewModelState.update { it.copy(isPaginating = !viewModelState.value.isLoading) }
                pagination.loadNextItems()
            }
        }
    }

    fun selectedToDoItem(item: TodosList) {
        userName = viewModelState.value.userList.find { it.id == item.userId }?.username ?: ""
        viewModelState.update {
            it.copy(
                lastUpdate = System.currentTimeMillis(),
                selectedItem = item,
            )
        }
    }

    fun onDeleteClick() {
        viewModelScope.launch {
            repository.deleteByIdTodoItem(viewModelState.value.selectedItem?.uId ?: 0)
        }
    }

    // Add or update Task
    fun onTaskValueChange(selectValue: String) {
        taskName = selectValue
    }

    fun onDateChange(date: Date) {
        val localDisplay = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        startDate = localDisplay.format(date)
    }

    fun onDescriptionValueChange(selectValue: String) {
        description = selectValue
    }

    fun onPriorityValueChange(selectValue: String) {
        priorityType = selectValue
    }

    fun onStatusValueChange(selectValue: String) {
        status = selectValue
    }

    fun onAssignedToValueChange(name: String) {
        assignedTo = viewModelState.value.userList.find { it.username == name } ?: UserList()
    }

    fun onSaveClick() {
        viewModelScope.launch {
            repository.insertTodoItem(
                isEditable = isEditable,
                uId = viewModelState.value.selectedItem?.uId ?: 0,
                userInfo = TodosList(
                    taskName = taskName,
                    startDate = startDate,
                    description = description,
                    assignedTo = assignedTo.username,
                    status = status,
                    userId = assignedTo.id
                )
            )
            taskName =  ""
            startDate = ""
            description =""
            assignedTo = UserList()
            status =  ""
        }
    }

    fun isEditable(type: Boolean) {
        if (type) {
            isEditable = true
            taskName = viewModelState.value.selectedItem?.taskName ?: ""
            startDate = viewModelState.value.selectedItem?.startDate ?: ""
            description = viewModelState.value.selectedItem?.description ?: ""
            assignedTo =
                viewModelState.value.userList.find { it.id == viewModelState.value.selectedItem?.userId }
                    ?: UserList()
            status = viewModelState.value.selectedItem?.status ?: ""
        }
    }

    data class DashboardViewModelState(
        val lastUpdate: Long = System.currentTimeMillis(),
        val isLoading: Boolean = false,
        val isPaginating: Boolean = false,
        var isEndReached: Boolean = false,
        val todosList: List<TodosList> = arrayListOf(),
        val userList: List<UserList> = arrayListOf(),
        val tabItem: List<TabItem> = arrayListOf(),
        val selectedItem: TodosList? = null,

        ) {
        fun toUiState(): DashboardUiState {
            return DashboardUiState(
                isLoading = isLoading,
                isPaginating = isPaginating,
                lastUpdate = lastUpdate,
                todosList = todosList,
                isEndReached = isEndReached,
                selectedItem = selectedItem,
                userList = userList,
                tabItem=tabItem,
            )
        }
    }

    data class DashboardUiState(
        val isLoading: Boolean,
        val isPaginating: Boolean,
        val lastUpdate: Long,
        val todosList: List<TodosList>,
        val userList: List<UserList>,
        val selectedItem: TodosList?,
        val tabItem:List<TabItem>,
        val isEndReached: Boolean,
    )

    enum class SessionStatus(val value: String) {
        PENDING("ToDo"),
        ONGOING("In-Progress"),
        COMPLETED("Completed"),
    }

    data class TabItem(var title: String,var count:Int)


}