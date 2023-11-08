package com.example.newsapp.presentation.details.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopBar(
    onBrowsingClick : () -> Unit,
    onShareClick : () -> Unit,
    onBookmarkClick : () -> Unit,
    onBackClick : () -> Unit
){
    TopAppBar(
        title = {},
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            actionIconContentColor = colorResource(id = R.color.body),
            navigationIconContentColor = colorResource(id = R.color.body)
        ),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(painter = painterResource(id = R.drawable.ic_back_arrow), contentDescription = null )
            }
        },
        actions = {
            IconButton(onClick = onBookmarkClick) {
                Icon(painter = painterResource(id = R.drawable.ic_bookmark), contentDescription = null )
            }

            IconButton(onClick = onShareClick) {
                Icon(imageVector = Icons.Default.Share, contentDescription = null )
            }

            IconButton(onClick = onBrowsingClick) {
                Icon(painter = painterResource(id = R.drawable.ic_network), contentDescription = null )
            }
        }


    )
}


@Preview(showBackground = true)
@Preview(showBackground = true , uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DetailsTopBarPreview(){
    MaterialTheme {
        DetailsTopBar(onBrowsingClick = { /*TODO*/ },
            onShareClick = { /*TODO*/ },
            onBookmarkClick = { /*TODO*/ }) {
        }
    }
}