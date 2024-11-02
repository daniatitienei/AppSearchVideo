package com.ad_coding.appsearchapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ad_coding.appsearchapp.ui.theme.AppSearchAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppSearchAppTheme {
                val viewModel = CoffeeViewModel(coffeeSearchManager = CoffeeSearchManager(this))

                val state by viewModel.state.collectAsStateWithLifecycle()

                LazyColumn {
                    item {
                        TextField(
                            value = state.searchText,
                            onValueChange = viewModel::onSearchValueChange,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }

                    items(state.coffeeList, key = { it.id }) { coffee ->
                        ListItem(
                            headlineContent = {
                                Text(text = coffee.name)
                            },
                            modifier = Modifier.animateItem()
                        )
                    }
                }
            }
        }
    }
}
