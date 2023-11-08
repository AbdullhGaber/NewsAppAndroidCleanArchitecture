package com.example.newsapp.presentation.nav_graph

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapp.presentation.home.HomeScreen
import com.example.newsapp.presentation.home.HomeViewModel
import com.example.newsapp.presentation.news_navigator.NewsNavigator
import com.example.newsapp.presentation.onboarding.OnBoardingScreen
import com.example.newsapp.presentation.onboarding.OnBoardingViewModel
import com.example.newsapp.presentation.search.SearchScreen
import com.example.newsapp.presentation.search.SearchViewModel

@Composable
fun NavGraph(
    startDestination: String
){

   val navController = rememberNavController()
   NavHost(navController = navController , startDestination = startDestination ){
       //nav-graph group (start)
       navigation(
           route = Route.AppStartNavigation.route,
           startDestination = Route.OnBoardingScreen.route
       ){
           composable(route = Route.OnBoardingScreen.route){
               val viewModel : OnBoardingViewModel = hiltViewModel()
               OnBoardingScreen(
                   event = viewModel::onEvent
               )
           }
       }

       //nav-graph group (the main app (home))
       navigation(
           route = Route.NewsNavigation.route,
           startDestination = Route.NewsNavigatorScreen.route
       ){
           composable(route = Route.NewsNavigatorScreen.route){
                NewsNavigator()
           }
       }

   }
}