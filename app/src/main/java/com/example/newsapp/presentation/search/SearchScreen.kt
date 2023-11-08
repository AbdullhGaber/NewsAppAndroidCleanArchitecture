package com.example.newsapp.presentation.search

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.Dimens.MediumPadding1
import com.example.newsapp.presentation.common.ArticleList
import com.example.newsapp.presentation.common.SearchBar


@Composable
fun SearchScreen(
    state : SearchState,
    event: (SearchEvent) -> Unit,
    navigateToDetails : (Article) -> Unit
){
    Column(
        modifier = Modifier
            .padding(
                top = MediumPadding1,
                start = MediumPadding1,
                end = MediumPadding1,
            )
            .statusBarsPadding(),
    ) {
        SearchBar(
            text = state.searchQuery, readOnly = false,
            onValueChange = {
                event(SearchEvent.UpdateSearchQuery(it))
            },
            onSearch = { event(SearchEvent.SearchNews) },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        )

        Spacer(modifier = Modifier.height(MediumPadding1))

        state.articles?.let {
            val articles = it.collectAsLazyPagingItems()
            ArticleList(
                articles = articles,
                onClick = { article ->
                    navigateToDetails(article)
                },
            )
        }
    }
}
