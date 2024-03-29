package com.example.to_do_list.di


import androidx.room.Room
import com.example.to_do_list.data.dataBase.UserInfoDatabase
import com.example.to_do_list.data.network.ApiProvider
import com.example.to_do_list.data.network.PreferenceManager
import com.example.to_do_list.repository.ToDoListRepository
import com.example.to_do_list.ui.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


object AppModule {
    fun appModules() = viewModelModules + repoModules + commonModules

    private val commonModules = module {
        single {
            Room.databaseBuilder(
                androidContext(),
                UserInfoDatabase::class.java,
                "To-Do-Task-db"
            ).fallbackToDestructiveMigration().build()
        }
        single { get<UserInfoDatabase>().localDao() }
        single { ApiProvider.client }
        single { PreferenceManager(androidContext()) }

    }

    private val repoModules = module {
        single { ToDoListRepository(get(), get(), get()) }
    }

    private val viewModelModules = module {
       viewModel { MainViewModel(get()) }
    }

}