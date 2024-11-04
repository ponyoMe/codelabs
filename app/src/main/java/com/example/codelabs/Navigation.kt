package com.example.codelabs

import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

//in navigation we start from adding dependency
//this code works
//added currentBackStackEntry by navcontroller, and then checked
// w new value curr= currentBackStackEntry?.destination?.route
//this is diff than bottom nav

@Composable
fun NavLogic(){
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopNav(navController) }
    ) {innerpadding ->
        NavGraph(navController, Modifier.padding(innerpadding))
    }
}

sealed class Routes(val icon: ImageVector, val route:String){
    object FirstPage : Routes(Icons.Filled.Home,"home")
    object SecondPage : Routes(Icons.Filled.Search,"search")
    object ThirdPage : Routes(Icons.Filled.Person,"profile")
}




@Composable
fun TopNav(navController: NavHostController){
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    NavigationBar {
        NavigationBarItem(
            icon = { Routes.FirstPage.icon },
            selected = currentRoute == Routes.FirstPage.route,
            onClick = { navController.navigate(Routes.FirstPage.route) },
            label = { Text(text="Home") },
            //interactionSource =
        )
        NavigationBarItem(
            icon = { Routes.SecondPage.icon },
            selected = currentRoute==Routes.SecondPage.route,
            onClick = { navController.navigate(Routes.SecondPage.route){

            } },
            label = { Text(text="Search") },
        )
        NavigationBarItem(
            icon = { Routes.ThirdPage.icon },
            selected = currentRoute==Routes.ThirdPage.route,
            onClick = { navController.navigate(Routes.ThirdPage.route){
            } },
            label = { Text(text="Profile") },
        )
    }

}

@Composable
fun FirstScreen(){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Text(text="this is Home page", fontSize = 20.sp)
    }
}

@Composable
fun SecondScreen(){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Text(text="this is Search page", fontSize = 20.sp)
    }
}

@Composable
fun ThirdScreen(){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Text(text="this is Profile page", fontSize = 20.sp)
    }
}



@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier){

    NavHost(navController, startDestination = Routes.FirstPage.route ){
        composable(Routes.FirstPage.route) {
            FirstScreen()
        }
        composable(Routes.SecondPage.route) {
            SecondScreen()
        }
        composable(Routes.ThirdPage.route) {
            ThirdScreen()
        }
    }



}