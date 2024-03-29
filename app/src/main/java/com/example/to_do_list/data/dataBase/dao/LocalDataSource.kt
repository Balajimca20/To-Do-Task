package com.example.to_do_list.data.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.to_do_list.data.model.TodosList
import com.example.to_do_list.data.model.users.UserList
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalDataSource {

    @Query("SELECT * FROM Todos")
    fun getToDoItem(): Flow<List<TodosList>>

    @Query("SELECT * FROM Todos")
    suspend fun getAllData(): List<TodosList>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTodoData(discover: TodosList)

    @Query("DELETE FROM Todos")
    suspend fun deleteAllToDo()

    @Query("delete from Todos where uId = :userId")
    suspend fun deleteByIdTodo(userId: Int)

    @Query("UPDATE Todos SET taskName = :taskName, startDate = :startDate, description = :description, assignedTo = :assignedTo, status = :status, userId = :userId WHERE uId =:uId")
    suspend fun updateTask(
        taskName: String,
        startDate: String,
        description: String,
        assignedTo: String,
        status: String,
        userId: String,
        uId: Int
    )


    @Query("SELECT * FROM Users")
    fun getAllUser(): Flow<List<UserList>>

    @Query("SELECT * FROM Users")
    suspend fun getAllUserData(): List<UserList>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserData(user: UserList)
}