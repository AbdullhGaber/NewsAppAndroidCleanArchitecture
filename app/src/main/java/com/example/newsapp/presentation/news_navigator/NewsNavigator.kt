package com.example.newsapp.presentation.news_navigator

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapp.R
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.bookmark.BookMarkScreen
import com.example.newsapp.presentation.bookmark.BookmarkViewModel
import com.example.newsapp.presentation.details.DetailsScreen
import com.example.newsapp.presentation.details.DetailsViewModel
import com.example.newsapp.presentation.details.components.DetailsEvent
import com.example.newsapp.presentation.home.HomeScreen
import com.example.newsapp.presentation.home.HomeViewModel
import com.example.newsapp.presentation.nav_graph.Route
import com.example.newsapp.presentation.news_navigator.components.BottomNavigationItem
import com.example.newsapp.presentation.news_navigator.components.NewsBottomNavigation
import com.example.newsapp.presentation.search.SearchScreen
import com.example.newsapp.presentation.search.SearchViewModel

@Composable
fun NewsNavigator(){

    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = "home"),
            BottomNavigationItem(icon = R.drawable.ic_search, text = "search"),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, text = "bookmark"),
        )
    }

    val navController = rememberNavController()

    val backStackState = navController.currentBackStackEntryAsState().value

    var selectedItemIdx by rememberSaveable{
        mutableStateOf(0)
    }

   selectedItemIdx = when(backStackState?.destination?.route){
        Route.HomeScreen.route -> 0
        Route.SearchScreen.route -> 1
        Route.BookmarkScreen.route -> 2
        else -> 0
   }

    val isBottomBarHidden = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.DetailsScreen.route
    }

   Scaffold(
       modifier = Modifier.fillMaxSize(),
       bottomBar = {
          if(!isBottomBarHidden){
              NewsBottomNavigation(
                  navMenuItems = bottomNavigationItems,
                  selected = selectedItemIdx,
                  onItemClick = { index ->
                      when(index) {
                          0 -> navigateToTab(navController,Route.HomeScreen.route)
                          1 -> navigateToTab(navController,Route.SearchScreen.route)
                          2 -> navigateToTab(navController,Route.BookmarkScreen.route)
                      }
                  }
              )
          }
       }
   ) {
       val bottomPadding = it.calculateBottomPadding()
       NavHost(
           navController = navController,
           startDestination = Route.HomeScreen.route,
           modifier = Modifier.padding(bottom = bottomPadding),
       ){
           composable(route = Route.HomeScreen.route){
              val viewModel : HomeViewModel = hiltViewModel()
              val articles = viewModel.news.collectAsLazyPagingItems()
              HomeScreen(
                  articles = articles,
                  navigateToSearch = { /*TODO*/ },
                  navigateToDetails = {article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                  }
              )
          }
           composable(route = Route.SearchScreen.route){
              val viewModel : SearchViewModel = hiltViewModel()
              SearchScreen(
                  state = viewModel.state.value,
                  event = viewModel::onEvent,
                  navigateToDetails = { article ->
                      navigateToDetails(navController , article)
                  }
              )
          }
           composable(route = Route.DetailsScreen.route){

               val viewModel : DetailsViewModel = hiltViewModel()
               if(viewModel.sideEffect != null) {
                   Toast.makeText(LocalContext.current , viewModel.sideEffect , Toast.LENGTH_LONG).show()
                   viewModel.onEvent(DetailsEvent.RemoveSideEffect)
               }
               navController.previousBackStackEntry?.savedStateHandle?.get<Article>("article")?.
               let{ article ->
                   DetailsScreen(article = article, event = viewModel::onEvent , navigateUp = {
                       navController.navigateUp()
                   })
               }
           }
           composable(route = Route.BookmarkScreen.route){
               val viewModel : BookmarkViewModel = hiltViewModel()
               val state = viewModel.articleListState.value
               BookMarkScreen(
                   state = state,
                   navigateToDetails = { article ->
                       navigateToDetails(navController, article)
                   },
               )
           }
       }
   }

}

fun navigateToTab(navController: NavController , route : String){
    navController.navigate(route){
        navController.graph.startDestinationRoute?.let { homeScreen ->

            popUpTo(homeScreen){
                saveState = true
            }

            restoreState = true

            launchSingleTop = true

        }
    }
}

private fun navigateToDetails(navController: NavController , article: Article){

    navController.currentBackStackEntry?.savedStateHandle?.set("article" , article)

    navController.navigate(
        Route.DetailsScreen.route
    )

}
