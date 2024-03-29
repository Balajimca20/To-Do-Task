package com.example.to_do_list.mapper


import android.content.Context
import android.util.Log
import com.example.to_do_list.data.dataBase.dao.LocalDataSource
import com.example.to_do_list.data.model.ToDoResponse
import com.example.to_do_list.data.model.users.UserResponse
import com.example.to_do_list.data.network.Response

suspend fun toDoDataMapper(
    response: Response<ToDoResponse?>,
    context: Context,
    localDataSource: LocalDataSource
): ToDoResponse? {
    return when (response) {
        is Response.Failure -> {
            ToDoResponse(
                totalPages = 150,
                limit = 30,
                todos = null
            )
        }
        is Response.NoNetwork -> {
            ToDoResponse(
                totalPages = 150,
                limit = 30,
                todos = null
            )
        }

        is Response.Success -> {
            val remoteData = response.data
            val localData=localDataSource.getAllData()
            // Find missing items by comparing remote and local data
            val missingData = response.data?.todos?.filterNot { remoteItem ->
                localData.any { localItem -> localItem.id == remoteItem.id }
            }
            if(missingData?.isNotEmpty()==true) {
                missingData.forEach { item->
                    localDataSource.insertTodoData(item)
                }
            }
            return remoteData
        }
    }
}

suspend fun userDataMapper(
    response: Response<UserResponse?>,
    context: Context,
    localDataSource: LocalDataSource
): UserResponse? {
    return when (response) {
        is Response.Failure -> {
            UserResponse(
                totalPages = 150,
                limit = 30,
                userList  = null
            )
        }
        is Response.NoNetwork -> {
            UserResponse(
                totalPages = 150,
                limit = 30,
                userList = null
            )
        }

        is Response.Success -> {
            val remoteData = response.data
            val localData=localDataSource.getAllUserData()
            // Find missing items by comparing remote and local data
            val missingData = response.data?.userList?.filterNot { remoteItem ->
                localData.any { localItem -> localItem.id == remoteItem.id }
            }
            if(missingData?.isNotEmpty()==true) {
                Log.e("insertUserData","true")
                missingData.forEach { item->
                    localDataSource.insertUserData(item)
                }
            }
            return remoteData
        }
    }
}
