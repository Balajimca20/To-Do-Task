package com.example.to_do_list.data.network



import com.example.to_do_list.data.model.ToDoResponse
import com.example.to_do_list.data.model.users.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ServiceApi {

    @GET("users")
    suspend fun getUsers(): Response<UserResponse?>

    @GET("todos")
    suspend fun getTodos(
        @Query("page") page:Int?,
        @Query("limit") limit:Int?=30,
        @Query("skip") skip:Int?=0,
    ) : Response<ToDoResponse?>


}