package com.gumu.bookwormapp.presentation.ui.common

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

@Composable
fun <State, ScreenEvent> ScreenWrapper(
    viewModel: BaseViewModel<State, ScreenEvent>,
    navController: NavController,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is UiEvent.Error -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
                is UiEvent.NavigateBack -> navController.popBackStack()
                is UiEvent.NavigateTo -> navController.navigate(event.route) {
                    event.popUpTo?.let {
                        popUpTo(it) { inclusive = true }
                    }
                }
            }
        }
    }

    content()
}
