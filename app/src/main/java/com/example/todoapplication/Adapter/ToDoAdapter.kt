package com.example.todoapplication;

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapplication.MainActivity
import com.example.todoapplication.Model.ToDoModel
import com.example.todoapplication.R
import com.example.todoapplication.Utils.DatabaseHandler

class ToDoAdapter(private val db: DatabaseHandler, private val activity: MainActivity) :
    RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {

    private var todoList: List<ToDoModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        db.openDatabase()
        val item = todoList!![position]
        holder.task.text = item.task
        holder.task.textSize = 18f
        holder.task.isChecked = toBoolean(item.status.toInt())
        holder.task.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                db.updateStatus(item.id, 1)
            } else {
                db.updateStatus(item.id, 0)
            }
        }
    }

    override fun getItemCount(): Int {
        return todoList?.size ?: 0
    }

    private fun toBoolean(n: Int): Boolean {
        return n != 0
    }

    // Converted setTasks method to Kotlin
    fun setTasks(todoList: List<ToDoModel>) {
        this.todoList = todoList
        notifyDataSetChanged() // Notify adapter after updating the data
    }

    fun getContext(): Context {
        return activity
    }

    fun deleteItem(position: Int) {
        val item = todoList?.get(position)
        item?.let {
            db.deleteTask(it.id)
            todoList = todoList?.toMutableList()?.apply { removeAt(position) }
            notifyItemRemoved(position)
        }
    }

    fun editItem(position: Int) {
        val item = todoList?.get(position)
        item?.let {
            val bundle = Bundle().apply {
                putInt("id", it.id)
                putString("task", it.task)
            }
            val fragment = AddNewTask().apply {
                arguments = bundle
            }
            fragment.show(activity.supportFragmentManager, AddNewTask.TAG)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val task: CheckBox = view.findViewById(R.id.todoCheckBox)
    }
}
