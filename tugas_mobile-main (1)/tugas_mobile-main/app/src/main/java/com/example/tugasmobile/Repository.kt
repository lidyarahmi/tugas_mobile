package com.example.tugasmobile

import com.example.tugasmobile.network.ApiService

class Repository (private val apiService: ApiService){
    fun getCharacters(page:String) = apiService.fetchCharacters(page)
}