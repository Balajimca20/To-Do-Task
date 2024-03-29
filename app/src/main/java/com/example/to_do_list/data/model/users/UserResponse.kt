package com.example.to_do_list.data.model.users

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Keep
data class UserResponse(
    @SerializedName("total")
    val totalPages: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("users")
    var userList: List<UserList>?,
)

@Keep
@Immutable
@Entity(tableName = "Users")
data class UserList(
    @PrimaryKey(autoGenerate = true)
    val uId:Int=0,
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: String?="",
    @ColumnInfo(name = "username")
    @SerializedName("username")
    val username: String?="",
    @ColumnInfo(name = "age")
    @SerializedName("age")
    val age: String?="",
    @ColumnInfo(name = "gender")
    val gender: String?="",
    @ColumnInfo(name = "email")
    val email: String?="",
    @ColumnInfo(name = "image")
    val image: String?="",
)

