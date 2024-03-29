package com.example.to_do_list.ui.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.to_do_list.ui.MainViewModel
import com.example.to_do_list.ui.navigation.NavigationGraph

@Composable
fun MainScreen(viewModel: MainViewModel){

    val navController = rememberNavController()

    Scaffold(
        content = { paddingValues->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
            ) {
               NavigationGraph(navController = navController, viewModel = viewModel)
            }
        }
    )
}