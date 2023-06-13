package com.example.tugasmobile

import MainAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.example.tugasmobile.network.Character



class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.characterLiveData.observe(this, { state->
            processCharactersResponse(state)
        })
    }

    private fun processCharactersResponse(state: ScreenState<List<Character>>){

        val pb = findViewById<ProgressBar>(R.id.progressBar)

        when(state){
            is ScreenState.Loading ->{
                pb.visibility = View.VISIBLE
            }
            is ScreenState.Success -> {
                state.data?.let { characters ->
                    val adapter = MainAdapter(characters)
                    val recyclerView = findViewById<RecyclerView>(R.id.charactersRv)
                    recyclerView?.layoutManager =
                        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    recyclerView?.adapter = adapter
                }
                pb.visibility = View.GONE
            }
            is ScreenState.Error ->{
                pb.visibility = View.GONE
                val view = pb.rootView
                Snackbar.make(view, state.message ?: "An error occurred", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}
