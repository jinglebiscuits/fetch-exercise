package com.scottwehby.fetchexercise.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.scottwehby.fetchexercise.data.repository.StubItemRepository
import com.scottwehby.fetchexercise.ui.screens.MainScreen
import com.scottwehby.fetchexercise.ui.theme.FetchExerciseTheme
import com.scottwehby.fetchexercise.ui.vm.MainViewModel
import com.scottwehby.fetchexercise.ui.vm.MainViewModelFactory
import com.scottwehby.fetchexercise.ui.vm.UiIntent
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val vm: MainViewModel by viewModels {
        MainViewModelFactory(StubItemRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FetchExerciseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        vm = vm,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
        lifecycleScope.launch {
            vm.sendIntent(UiIntent.ScreenOpened)
        }
    }
}
