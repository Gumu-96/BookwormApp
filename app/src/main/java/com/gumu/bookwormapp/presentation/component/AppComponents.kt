package com.gumu.bookwormapp.presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gumu.bookwormapp.R
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

    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = singleLine,
            label = label,
            modifier = modifier
                .bringIntoViewRequester(bringIntoViewRequester)
                .onFocusChanged {
                    if (it.isFocused) {
                        coroutineScope.launch {
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                },
            visualTransformation = if (isPassword and isPasswordVisible.not()) PasswordVisualTransformation()
            else VisualTransformation.None,
            trailingIcon = {
                if (isPassword) {
                    IconButton(onClick = { isPasswordVisible = isPasswordVisible.not() }) {
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = stringResource(id = R.string.password_visibility_icon_desc)
                        )
                    }
                } else trailingIcon?.let { it() }
            },
            shape = RoundedCornerShape(if (singleLine) 50 else 35),
            maxLines = maxLines,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            isError = isError
        )
        if (isError) {
            errorMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigateBackTopAppBar(
    title: String,
    onBackClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = stringResource(id = R.string.arrow_back_icon_desc))
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
    color: Color = MaterialTheme.colorScheme.primaryContainer
) {
    Surface(
        shape = RoundedCornerShape(percent = 50),
        color = color,
        modifier = modifier
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(
                    horizontal = if (wrapContent) 8.dp else 32.dp,
                    vertical = if(wrapContent) 4.dp else 8.dp
                )
        )
    }
}

@Composable
fun BookStatusItem() {
    ElevatedCard(
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.bookworm),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(width = 150.dp, height = 200.dp)
                        .aspectRatio(3f / 4f)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.tertiary)
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                ) {
                    Text(
                        text = "5",
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onTertiary
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Hello. Android")
            Text(text = "[Ed Burnette]")
            PillShapedText(text = "Reading")
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
