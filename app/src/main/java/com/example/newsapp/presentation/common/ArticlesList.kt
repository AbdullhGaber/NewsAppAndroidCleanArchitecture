package com.example.newsapp.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.Dimens.ExtraSmallPadding2
import com.example.newsapp.presentation.Dimens.MediumPadding1

@Composable
fun ArticleList(
    modifier : Modifier = Modifier,
    articles : LazyPagingItems<Article>,
    onClick : (Article) -> Unit
){

    if(handlePagingResult(articles)){
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(MediumPadding1),
                contentPadding = PaddingValues(all = ExtraSmallPadding2)
            ){
                items(articles.itemCount){
                    articles[it]?.let { article ->
                        ArticleCard(article = article , onClick = { onClick.invoke(article) } )
                    }
                }
            }
    }
}

@Composable
fun ArticleList(
    modifier : Modifier = Modifier,
    articles : List<Article>,
    onClick : (Article) -> Unit
){
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(MediumPadding1),
                contentPadding = PaddingValues(all = ExtraSmallPadding2)
            ){
                items(articles.size){
                    val article = articles[it]
                    ArticleCard(article = article, onClick = { onClick.invoke(article) } )
                }
            }
}

@Composable
fun handlePagingResult(
    articles : LazyPagingItems<Article>
) : Boolean{

    val loadState = articles.loadState

    val error = when{
        loadState.refresh is LoadState.Error -> {loadState.refresh as LoadState.Error}
        loadState.prepend is LoadState.Error -> {loadState.prepend as LoadState.Error}
        loadState.append is LoadState.Error -> {loadState.append as LoadState.Error}
        else -> null
    }

    return when {
        loadState.refresh is LoadState.Loading -> {
            ShimmerEffect()
            false
        }

        error != null -> {
            EmptyScreen()
            false
        }

        else -> true
    }

}

@Composable
fun ShimmerEffect(){
    Column(verticalArrangement = Arrangement.spacedBy(MediumPadding1)) {
        repeat(10){
            ArticleCardShimmerEffect(
                modifier = Modifier.padding(horizontal = MediumPadding1)
            )
        }
    }
}