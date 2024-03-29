package com.example.to_do_list.data.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Keep
data class ToDoResponse(
    @SerializedName("total")
    val totalPages: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("todos")
    var todos: List<TodosList>?,
)

@Keep
@Immutable
@Entity(tableName = "Todos")
data class TodosList(
    @PrimaryKey(autoGenerate = true)
    val uId:Int=0,
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Int? = 0,
    @ColumnInfo(name = "taskName")
    @SerializedName("todo")
    val taskName: String?="",
    @ColumnInfo(name = "completed")
    @SerializedName("completed")
    val completed: Boolean?=false,
    @ColumnInfo(name = "userId")
    @SerializedName("userId")
    val userId: String?="",
    @ColumnInfo(name = "status")
    val status: String?="",
    @ColumnInfo(name = "startDate")
    val startDate: String?="",
    @ColumnInfo(name = "description")
    val description: String?="",
    @ColumnInfo(name = "priorityType")
    val priorityType: String?="",
    @ColumnInfo(name = "assignedTo")
    var assignedTo: String?="",
)

