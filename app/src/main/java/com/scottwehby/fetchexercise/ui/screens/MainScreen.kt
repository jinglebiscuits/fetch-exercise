package com.scottwehby.fetchexercise.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.scottwehby.fetchexercise.data.model.Group
import com.scottwehby.fetchexercise.data.model.Item
import com.scottwehby.fetchexercise.ui.vm.MainViewModel
import com.scottwehby.fetchexercise.ui.vm.UiIntent
import com.scottwehby.fetchexercise.ui.vm.UiState
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    vm: MainViewModel,
    modifier: Modifier = Modifier
) {
    val state = vm.state.collectAsState()
    val scope = rememberCoroutineScope()

    Box(modifier = modifier.fillMaxSize()) {
        when (val currentState = state.value) {
            is UiState.Error -> ErrorScreen(
                onRetry = { scope.launch { vm.sendIntent(UiIntent.Retry) } },
                modifier = Modifier.fillMaxSize()
            )

            UiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
            UiState.NoNetwork -> NoNetworkScreen(
                onRetry = { scope.launch { vm.sendIntent(UiIntent.Retry) } },
                modifier = Modifier.fillMaxSize()
            )

            is UiState.Success -> SuccessScreen(
                groups = currentState.groupedItems,
                onRefresh = {
                    scope.launch {
                        vm.sendIntent(UiIntent.Retry)
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier, onRetry: () -> Unit) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("An error occurred.")
            Button(onClick = onRetry) { Text("Retry") }
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun NoNetworkScreen(modifier: Modifier = Modifier, onRetry: () -> Unit) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("No network connection.")
            Button(onClick = onRetry) { Text("Retry") }
        }
    }
}

@Composable
private fun SuccessScreen(
    groups: List<Group>,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(groups) { group ->
            GroupRow(group = group)
        }
    }
}

@Composable
private fun GroupRow(group: Group) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "List ${group.listId}",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(group.items) { item ->
                ItemCard(item = item)
            }
        }
    }
}

@Composable
private fun ItemCard(item: Item) {
    Card(
        modifier = Modifier.size(72.dp)
    ) {
        Box(Modifier.fillMaxSize()) {
            Text(
                text = item.id.toString(),
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(4.dp)
            )

            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Preview
@Composable
private fun ItemCardPreview() {
    ItemCard(Item(1, 1, "Item 1"))
}

@Preview(showBackground = true)
@Composable
private fun GroupRowPreview() {
    GroupRow(
        Group(
            1,
            listOf(
                Item(1, 1, "Item 1"),
                Item(2, 1, "Item 2"),
            )
        )
    )
}
