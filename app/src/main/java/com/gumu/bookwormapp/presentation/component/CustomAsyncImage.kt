package com.gumu.bookwormapp.presentation.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.gumu.bookwormapp.presentation.theme.BookwormAppTheme

@Composable
fun CustomAsyncImage(
    modifier: Modifier = Modifier,
    model: Any?,
    contentDescription: String?,
    contentScale: ContentScale = ContentScale.Crop
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(model)
            .crossfade(true)
            .diskCachePolicy(CachePolicy.ENABLED)
            .build(),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier
    ) {
        when (painter.state) {
            is AsyncImagePainter.State.Loading -> Icon(
                imageVector = Icons.Default.Image,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
            else -> Icon(
                imageVector = Icons.Default.BrokenImage,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Preview
@Composable
private fun CustomAsyncImagePreview() {
    BookwormAppTheme {
        CustomAsyncImage(
            model = null,
            contentDescription = null
        )
    }
}
