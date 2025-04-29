package com.gumu.bookwormapp.presentation.animation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import com.gumu.bookwormapp.presentation.animation.AnimationConstants.SCREEN_TRANSITION_DELAY_MILLIS
import com.gumu.bookwormapp.presentation.animation.AnimationConstants.SCREEN_TRANSITION_DURATION_MILLIS

enum class ScaleTransitionDirection {
    Inwards, Outwards
}

fun scaleIntoContainer(
    direction: ScaleTransitionDirection = ScaleTransitionDirection.Inwards,
    initialScale: Float = if (direction == ScaleTransitionDirection.Outwards) 0.8f else 1.2f
): EnterTransition {
    return scaleIn(
        animationSpec = tween(SCREEN_TRANSITION_DURATION_MILLIS, SCREEN_TRANSITION_DELAY_MILLIS),
        initialScale = initialScale
    ) + fadeIn(tween(SCREEN_TRANSITION_DURATION_MILLIS, SCREEN_TRANSITION_DELAY_MILLIS))
}

fun scaleOutOfContainer(
    direction: ScaleTransitionDirection = ScaleTransitionDirection.Outwards,
    targetScale: Float = if (direction == ScaleTransitionDirection.Inwards) 0.8f else 1.2f
): ExitTransition {
    return scaleOut(
        animationSpec = tween(SCREEN_TRANSITION_DURATION_MILLIS, SCREEN_TRANSITION_DELAY_MILLIS),
        targetScale = targetScale
    ) + fadeOut(tween(SCREEN_TRANSITION_DURATION_MILLIS, SCREEN_TRANSITION_DELAY_MILLIS))
}

fun fadeInScreen(): EnterTransition = fadeIn(
    tween(SCREEN_TRANSITION_DURATION_MILLIS, SCREEN_TRANSITION_DELAY_MILLIS)
)

fun fadeOutScreen(): ExitTransition = fadeOut(
    tween(SCREEN_TRANSITION_DURATION_MILLIS, SCREEN_TRANSITION_DELAY_MILLIS)
)
