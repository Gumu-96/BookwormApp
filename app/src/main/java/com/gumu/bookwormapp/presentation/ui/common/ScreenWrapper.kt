package com.gumu.bookwormapp.presentation.ui.common

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@Composable
fun <State, ScreenEvent> ScreenWrapper(
    viewModel: BaseViewModel<State, ScreenEvent>,
    navController: NavController,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    ObserveAsEvents(viewModel.uiEvents) { event ->
        when (event) {
            is UiEvent.ShowToast -> {
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

    content()
}

@Composable
private fun <T> ObserveAsEvents(flow: Flow<T>, onEvent: (T) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(flow, lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                flow.collect(onEvent)
            }
        }
    }
}
