package com.example.retrofitapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitapplication.databinding.ActivityMainBinding
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private lateinit var apiAdapter : APIAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecylerView()

        lifecycleScope.launchWhenCreated {
            binding.progressB.isVisible = true
            val response = try {
                RetrofitInsance.api.getTodos()
            }
            catch (e:IOException){
                Toast.makeText(applicationContext, "You do not have INTERNET Connection..", Toast.LENGTH_LONG).show()
                binding.progressB.isVisible = false
                return@launchWhenCreated
            }
            catch (e: HttpException){
                Toast.makeText(applicationContext, "Unexpected response!!!", Toast.LENGTH_LONG).show()
                binding.progressB.isVisible = false
                return@launchWhenCreated
            }

            if(response.isSuccessful && response.body()!=null)
            {
                apiAdapter.todos = response.body()!!
            }

            else
            {
                Toast.makeText(applicationContext, "There is something wrong with response!", Toast.LENGTH_LONG).show()
            }

            binding.progressB.isVisible=false
        }
    }

    private fun setupRecylerView() = binding.RView.apply {
        apiAdapter = APIAdapter()
        adapter = apiAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
}