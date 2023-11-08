package com.example.newsapp.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.*
import com.example.newsapp.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.presentation.Dimens
import com.example.newsapp.presentation.Dimens.ArticleCardSize
import com.example.newsapp.presentation.Dimens.ExtraSmallPadding
import com.example.newsapp.presentation.Dimens.MediumPadding1

fun Modifier.shimmerEffect() = composed {
     val transition = rememberInfiniteTransition()
     val alpha = transition.animateFloat(
         initialValue = 0.2f, targetValue = 0.9f,
         animationSpec =
         infiniteRepeatable(
             animation = tween(1000),
             repeatMode = RepeatMode.Reverse,
         ),
     ).value
    
    background(color = colorResource(id = R.color.shimmer).copy(alpha = alpha))
}

@Composable
fun ArticleCardShimmerEffect(
    modifier : Modifier = Modifier
){
    Row(modifier = modifier) {
        Box(
            modifier = modifier
                .size(ArticleCardSize)
                .clip(MaterialTheme.shapes.medium)
                .shimmerEffect(),
        )

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = modifier
                .padding(horizontal = MediumPadding1)
                .height(ArticleCardSize),
        ){
            Box(
                modifier = modifier
                    .shimmerEffect()
                    .fillMaxWidth()
                    .height(30.dp)
                    .padding(horizontal = MediumPadding1)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = modifier
                        .shimmerEffect()
                        .fillMaxWidth(0.5f)
                        .height(15.dp)
                        .padding(horizontal = MediumPadding1)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true , uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ArticleCardShimmerEffectPreview(){
    MaterialTheme {
        ArticleCardShimmerEffect()
    }
}