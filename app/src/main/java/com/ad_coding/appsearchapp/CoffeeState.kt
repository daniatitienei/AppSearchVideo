package com.ad_coding.appsearchapp

data class CoffeeState(
    val searchText: String = "",
    val coffeeList: List<Coffee> = emptyList()
)
