package com.ad_coding.appsearchapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoffeeViewModel(
    private val coffeeSearchManager: CoffeeSearchManager
) : ViewModel() {

    private var _state = MutableStateFlow(CoffeeState())
    val state = _state.asStateFlow()

    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            coffeeSearchManager.initialize()
            val coffeeList = (0..50).map {
                Coffee(
                    namespace = "coffee",
                    id = it.toString(),
                    score = it,
                    name = "Coffee $it"
                )
            }

            coffeeSearchManager.addCoffeeList(coffeeList)
        }
    }

    fun onSearchValueChange(query: String) {
        _state.update {
            it.copy(
                searchText = query
            )
        }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            val coffeeList = coffeeSearchManager.searchCoffee(query)
            _state.update {
                it.copy(
                    coffeeList = coffeeList
                )
            }
        }
    }

    override fun onCleared() {
        coffeeSearchManager.closeSession()
        super.onCleared()
    }
}