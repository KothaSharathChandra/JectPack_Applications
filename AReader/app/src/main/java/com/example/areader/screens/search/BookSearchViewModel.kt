package com.example.areader.screens.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.areader.data.Resource
import com.example.areader.model.Item
import com.example.areader.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
//class BookSearchViewModel @Inject constructor(private val repository: BookRepository):ViewModel() {
//    private val listOfBooks: MutableState<DataOrException<List<Item>, Boolean, Exception>> =
//        mutableStateOf(DataOrException(null, true, Exception("")))
//
//    init{
//        searchBooks("android")
//    }
//
//    private fun searchBooks(query: String) {
//      viewModelScope.launch {
//          if(query.isEmpty()){
//              return@launch
//          }
//          listOfBooks.value.loading=true
//         listOfBooks.value=repository.getBooks(query)
//          Log.d("Search", "searchBooks: ${listOfBooks.value.data.toString()}")
//          if(listOfBooks.value.data.toString().isNotEmpty()) listOfBooks.value.loading=false
//
//      }
//    }
//}

@HiltViewModel
class BookSearchViewModel @Inject constructor(private val repository: BookRepository) :ViewModel(){

    var list: List<Item> by mutableStateOf(listOf())

    var isLoading:Boolean by mutableStateOf(true)

    init{
        loadBooks()
    }

    private fun loadBooks() {
       searchBooks("android")
    }

     fun searchBooks(query: String) {
        viewModelScope.launch(Dispatchers.Default) {

            if(query.isEmpty()){
                return@launch
            }
            try{
               when(val response =repository.getBooks(query)){
                   is Resource.Success -> {
                list = response.data!!
                       if(list.isNotEmpty()) isLoading =false

                   }
                   is Resource.Error ->{
                       isLoading =false
                       Log.d("NetWork", "searchBooks: Failed getting books")
                   }
                   else -> {isLoading =false}
               }
            }catch(exception: Exception){
                isLoading = false
                Log.d("NetWork", "searchBooks: ${exception.message.toString()} ")
            }
        }

    }
}