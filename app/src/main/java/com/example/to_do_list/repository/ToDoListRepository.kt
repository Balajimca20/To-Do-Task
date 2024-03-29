package com.example.to_do_list.repository

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.to_do_list.data.dataBase.dao.LocalDataSource
import com.example.to_do_list.mapper.toDoDataMapper
import com.example.to_do_list.mapper.userDataMapper
import com.example.to_do_list.data.model.ToDoResponse
import com.example.to_do_list.data.model.TodosList
import com.example.to_do_list.data.model.users.UserList
import com.example.to_do_list.data.model.users.UserResponse
import com.example.to_do_list.data.network.ServiceApi
import com.example.to_do_list.data.network.handleResponse
import kotlinx.coroutines.flow.Flow


class ToDoListRepository(
    private val serviceApi: ServiceApi,
    private val context: Context,
    private val localDataSource: LocalDataSource,
) {
    suspend fun getUsers(): Result<UserResponse> {
        val handleResponse = handleResponse {
            serviceApi.getUsers()
        }
        val response = userDataMapper(handleResponse, context, localDataSource)
        return Result.success(
            UserResponse(
                totalPages = response?.totalPages ?: 0,
                limit = response?.limit ?: 0,
                userList = response?.userList ?: arrayListOf(),
            )
        )

    }

    suspend fun toDoResponseResult(page: Int): Result<ToDoResponse> {
        val handleResponse = handleResponse {
            serviceApi.getTodos(
                page = page,
            )
        }
        val response = toDoDataMapper(handleResponse, context, localDataSource)
        return Result.success(
            ToDoResponse(
                totalPages = response?.totalPages ?: 0,
                limit = response?.limit ?: 0,
                todos = response?.todos ?: arrayListOf(),
            )
        )

    }

    var getToDoList: Flow<List<TodosList>> = localDataSource.getToDoItem()
    var getUserList: Flow<List<UserList>> = localDataSource.getAllUser()

    suspend fun insertTodoItem(
        isEditable: Boolean,
        uId: Int, userInfo: TodosList,
    ) {
        if (isEditable) {
            localDataSource.updateTask(
                taskName = userInfo.taskName ?: "",
                startDate = userInfo.startDate ?: "",
                description = userInfo.description ?: "",
                assignedTo = userInfo.assignedTo ?: "",
                status = userInfo.status ?: "",
                userId = userInfo.userId ?: "",
                uId = uId,
            )
            Log.e("isEdit", "isEditable $userInfo")
        } else {
            localDataSource.insertTodoData(userInfo)
        }
    }

    suspend fun deleteByIdTodoItem(uId: Int) {
        localDataSource.deleteByIdTodo(uId)
    }


}