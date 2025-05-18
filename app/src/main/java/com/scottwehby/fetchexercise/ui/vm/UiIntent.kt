package com.scottwehby.fetchexercise.ui.vm

sealed interface UiIntent {
    object ScreenOpened : UiIntent
    object Retry : UiIntent
}
