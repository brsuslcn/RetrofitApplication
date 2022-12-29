package com.example.retrofitapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitapplication.databinding.ActivityMainBinding.inflate
import com.example.retrofitapplication.databinding.ChildLayoutBinding


class APIAdapter : RecyclerView.Adapter<APIAdapter.ApiViewHolder>() {
    inner class ApiViewHolder(val binding: ChildLayoutBinding) : RecyclerView.ViewHolder(binding.root)


    private val diffCallback = object : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var todos: List<Todo>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }


    override fun getItemCount() = todos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApiViewHolder {
       return ApiViewHolder(ChildLayoutBinding.inflate(
           LayoutInflater.from(parent.context),
           parent,
           false
       ))
    }

    override fun onBindViewHolder(holder: ApiViewHolder, position: Int) {
        holder.binding.apply {

            val todo = todos[position]
            txtTitle.text = todo.title
            cBox.isChecked = todo.completed
        }
    }

}



