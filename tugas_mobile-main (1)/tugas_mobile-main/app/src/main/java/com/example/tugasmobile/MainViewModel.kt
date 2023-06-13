package com.example.tugasmobile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tugasmobile.network.ApiClient
import com.example.tugasmobile.network.Character
import com.example.tugasmobile.network.CharacterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: Repository = Repository(ApiClient.apiService)) : ViewModel() {

    private val _charactersLiveData = MutableLiveData<ScreenState<List<Character>>>()
    val characterLiveData: LiveData<ScreenState<List<Character>>>
        get() = _charactersLiveData

    init {
        fetchCharacter()
    }

    private fun fetchCharacter() {
        val client = repository.getCharacters("1")
        _charactersLiveData.postValue(ScreenState.Loading(null))
        client.enqueue(object : Callback<CharacterResponse> {
            override fun onResponse(
                call: Call<CharacterResponse>,
                response: Response<CharacterResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.result?.let { characters ->
                        _charactersLiveData.postValue(ScreenState.Success(characters))
                    }
                } else {
                    _charactersLiveData.postValue(ScreenState.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                //Log.d("Failure", t.message.toString())
                _charactersLiveData.postValue(ScreenState.Error("An error occurred", null))
            }
        })
    }
}
