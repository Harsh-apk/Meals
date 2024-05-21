package com.harsh_kumar.meals.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harsh_kumar.meals.apiService.mealService
import com.harsh_kumar.meals.types.Meal
import kotlinx.coroutines.launch

class SearchMealViewModel: ViewModel() {
    data class SearchMealState(
        val loading: Boolean = false,
        val meal: List<Meal>?=null,
        val error: String? = null

    )
    val searchQuery : MutableState<String> = mutableStateOf("Search")
    val _searchMealState = mutableStateOf(SearchMealState())
    val searchMealState : State<SearchMealState> = _searchMealState

    init{

    }
    class MyException(message:String): Exception(message)

    fun fetchSearchMeal( ) = try{
        if(searchQuery.value=="Search"||searchQuery.value.isEmpty()){
            throw MyException(message = "Meal name can't be empty")
        } else {
            _searchMealState.value = _searchMealState.value.copy(
                loading = true,
                meal = null,
                error = null,
            )
            viewModelScope.launch {
               val searchResponse =  mealService.searchMeal(searchQuery.value!!)
                _searchMealState.value = _searchMealState.value.copy(
                    loading = false,
                    meal = searchResponse.meals,
                    error = null,
                )
            }

        }


    }catch (e:Exception){
        _searchMealState.value = _searchMealState.value.copy(
            loading = false,
            meal = null,
            error = "Error : ${e.message}",
        )
    }

}