package com.gumu.bookwormapp.presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.gumu.bookwormapp.R
import com.gumu.bookwormapp.domain.model.Book
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CustomOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = false,
    label: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isPassword: Boolean = false,
    isError: Boolean = false,
    errorMessage: String? = null,
    maxLines: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()
    var isPasswordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .bringIntoViewRequester(bringIntoViewRequester)
            .onFocusChanged {
                if (it.isFocused) {
                    coroutineScope.launch {
                        bringIntoViewRequester.bringIntoView()
                    }
                }
            },
        value = value,
        onValueChange = onValueChange,
        singleLine = singleLine,
        label = label,
        visualTransformation = if (isPassword and isPasswordVisible.not()) PasswordVisualTransformation()
            else VisualTransformation.None,
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = { isPasswordVisible = isPasswordVisible.not() }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff
                            else Icons.Default.Visibility,
                        contentDescription = stringResource(
                            id = if (isPasswordVisible) R.string.hide_password_icon_desc
                                else R.string.show_password_icon_desc
                        )
                    )
                }
            } else trailingIcon?.let { it() }
        },
        shape = RoundedCornerShape(if (singleLine) 50 else 35),
        maxLines = maxLines,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        isError = isError,
        supportingText = {
            if (isError) errorMessage?.let { Text(text = it, fontSize = 14.sp) }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigateBackTopAppBar(
    title: @Composable () -> Unit,
    onBackClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = title,
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.arrow_back_icon_desc)
                )
            }
        },
        scrollBehavior = scrollBehavior,
        actions = actions
    )
}

@Composable
fun PillShapedText(
    modifier: Modifier = Modifier,
    text: String,
    wrapContent: Boolean = false,
    color: Color = MaterialTheme.colorScheme.primaryContainer,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Surface(
        shape = RoundedCornerShape(percent = 50),
        color = color,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(
                    horizontal = if (wrapContent) 8.dp else 32.dp,
                    vertical = if(wrapContent) 4.dp else 8.dp
                )
        ) {
            leadingIcon?.let { it() }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier

            )
            Spacer(modifier = Modifier.width(4.dp))
            trailingIcon?.let { it() }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookStatusItem(
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        onClick = {},
//        colors = CardDefaults.elevatedCardColors(
//            containerColor = MaterialTheme.colorScheme.primary
//        ),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.width(160.dp)
        ) {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.bookworm),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(width = 160.dp, height = 200.dp)
                        .aspectRatio(3f / 4f)
                )
                PillShapedText(
                    text = "5",
                    color = MaterialTheme.colorScheme.tertiary,
                    wrapContent = true,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = ""
                        )
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.BottomEnd)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "Hello. Android",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "[Ed Burnette]",
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Reading",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookItem(
    modifier: Modifier = Modifier,
    book: Book,
    onClick: (Book) -> Unit
) {
    OutlinedCard(
        onClick = { onClick(book) },
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            CustomAsyncImage(
                model = book.thumbnail,
                contentDescription = book.title,
                modifier = Modifier
                    .size(width = 110.dp, height = 150.dp)
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Text(
                    text = book.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(id = R.string.book_author_label, book.authors ?: "[]"),
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = stringResource(
                        id = R.string.book_published_date_label,
                        book.publishedDate ?: stringResource(id = R.string.book_unknown_data)
                    ),
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = book.categories?.toString() ?: "[]",
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun CustomAsyncImage(
    modifier: Modifier = Modifier,
    model: Any?,
    contentDescription: String?
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(model)
            .crossfade(true)
            .diskCachePolicy(CachePolicy.ENABLED)
            .build(),
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier
    ) {
        when (painter.state) {
            is AsyncImagePainter.State.Loading -> Icon(
                imageVector = Icons.Default.Image,
                contentDescription = stringResource(id = R.string.empty_desc),
                modifier = Modifier.size(40.dp)
            )
            is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
            else -> Icon(
                imageVector = Icons.Default.BrokenImage,
                contentDescription = stringResource(id = R.string.empty_desc),
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Composable
fun LoadingOverlay() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}
