package com.scottwehby.fetchexercise.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.scottwehby.fetchexercise.data.model.Item
import com.scottwehby.fetchexercise.data.repository.StubItemRepository
import com.scottwehby.fetchexercise.ui.theme.FetchExerciseTheme
import com.scottwehby.fetchexercise.ui.vm.MainViewModel
import com.scottwehby.fetchexercise.ui.vm.MainViewModelFactory
import com.scottwehby.fetchexercise.ui.vm.UiIntent
import com.scottwehby.fetchexercise.ui.vm.UiState
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
                    val uiState = vm.state.collectAsState()
                    when (val state = uiState.value) {
                        is UiState.Error -> TODO()
                        UiState.Loading -> {
                            Text("Loading")
                        }
                        UiState.NoNetwork -> {
                            Text("No network")
                        }
                        is UiState.Success -> {
                            Item(
                                state.groupedItems.values.first()[0],
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
        lifecycleScope.launch {
            vm.sendIntent(UiIntent.ScreenOpened)
        }
    }
}

@Composable
fun Item(item: Item, modifier: Modifier = Modifier) {
    Text(
        text = item.name,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun ItemPreview() {
    FetchExerciseTheme {
        Item(Item(1, 1, "Item 1"))
    }
}
