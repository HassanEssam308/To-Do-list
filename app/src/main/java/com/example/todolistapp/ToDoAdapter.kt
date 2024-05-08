package com.example.todolistapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.fragments.ShowDataFragment
import com.google.android.material.switchmaterial.SwitchMaterial

class ToDoAdapter(
    private val listOfToDo: ArrayList<DataTodo>,
    private val context: Context
) : RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {

    private lateinit var recyclerViewEvent: RecyclerViewEvent

    fun setOnItemOnLongClickListener(listener: RecyclerViewEvent) {
        recyclerViewEvent = listener

    }

    fun setOnItemListener(listener: RecyclerViewEvent) {
        recyclerViewEvent = listener

    }

    // ViewHolder
    class ViewHolder(itemView: View, listener: RecyclerViewEvent, listOfToDo: ArrayList<DataTodo>) :
        RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.RowView_TV_Title)
        val body = itemView.findViewById<TextView>(R.id.RowView_TV_Body)
        val isDone = itemView.findViewById<SwitchMaterial>(R.id.RowView_SwitchBtn_IsDone)

        init {
            itemView.setOnLongClickListener {
                listener.onLongClick(adapterPosition, listOfToDo[adapterPosition])
                return@setOnLongClickListener true
            }

            itemView.setOnClickListener {
//              Log.d("setOnClickListener","setOnClickListener")
                listener.onItemClick(adapterPosition, listOfToDo[adapterPosition])
            }
        }





    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_view_to_do, parent, false), recyclerViewEvent, listOfToDo
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = listOfToDo[position]
        holder.title.text = item.title
        holder.body.text = item.body
        holder.isDone.isChecked = item.isDone
        holder.isDone.setOnClickListener {
            val dbHelper = DBHelper(context)
            dbHelper.updateToDo(
                DataTodo(
                    item.id, item.title,
                    item.body, holder.isDone.isChecked
                )
            )
//            Toast.makeText(context, "${holder.isDone.isChecked}", Toast.LENGTH_SHORT).show()
        }

    }

    override fun getItemCount(): Int {
        return listOfToDo.size
    }

    interface RecyclerViewEvent {
        //        fun onItemClick(position: Int,dataTodo: DataTodo)
        fun onItemClick(position: Int,dataTodo: DataTodo)
        fun onLongClick(position: Int,dataTodo: DataTodo)
    }
}