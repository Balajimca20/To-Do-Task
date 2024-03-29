package com.example.to_do_list.data.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.to_do_list.data.dataBase.dao.LocalDataSource
import com.example.to_do_list.data.model.TodosList
import com.example.to_do_list.data.model.users.UserList
import kotlinx.coroutines.CoroutineScope


@Database(entities = [TodosList::class, UserList::class], version = 1, exportSchema = false)
abstract class UserInfoDatabase : RoomDatabase() {
    abstract fun localDao(): LocalDataSource

    companion object {
        @Volatile
        private var INSTANCE: UserInfoDatabase? = null

        fun getDatabase(ctx: Context, scope: CoroutineScope): UserInfoDatabase {
            return when (val temp = INSTANCE) {
                null -> synchronized(this) {
                    Room.databaseBuilder(
                        ctx.applicationContext, UserInfoDatabase::class.java,
                        "user_info"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }

                else -> temp
            }
        }
    }

    /* abstract fun tvDetailDao(): TvDetailDataSource*/
}