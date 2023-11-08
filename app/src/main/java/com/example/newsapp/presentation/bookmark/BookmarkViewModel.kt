package com.example.newsapp.presentation.bookmark

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    val newsUseCases: NewsUseCases
) : ViewModel() {

    private val _articleListState = mutableStateOf(BookMarkState())
    val articleListState : State<BookMarkState> = _articleListState

    init {
        viewModelScope.launch {
            getArticles()
        }
    }

    suspend fun getArticles(){
        newsUseCases.getRoomArticles().onEach {
            _articleListState.value = _articleListState.value.copy(articles = it)
        }.launchIn(viewModelScope)
    }
}