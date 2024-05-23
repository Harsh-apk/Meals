package com.harsh_kumar.meals.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harsh_kumar.meals.apiService.mealService
import com.harsh_kumar.meals.model.SearchMealState
import kotlinx.coroutines.launch

class SearchMealViewModel : ViewModel() {

    val searchQuery: MutableState<String> = mutableStateOf("")
    private val _searchMealState = mutableStateOf(SearchMealState())
    val searchMealState: State<SearchMealState> = _searchMealState

    class MyException(message: String) : Exception(message)

    fun fetchSearchMeal() = try {
        if (searchQuery.value.isEmpty()) {
            throw MyException(message = "Meal name can't be empty")
        } else {
            _searchMealState.value = _searchMealState.value.copy(
                loading = true,
                meals = null,
                error = null,
            )
            viewModelScope.launch {
                val searchResponse = mealService.searchMeal(searchQuery.value)
                _searchMealState.value = _searchMealState.value.copy(
                    loading = false,
                    meals = searchResponse.meals,
                    error = null,
                )
            }
        }

    } catch (e: Exception) {
        _searchMealState.value = _searchMealState.value.copy(
            loading = false,
            meals = null,
            error = "Error : ${e.message}",
        )
    }

}
