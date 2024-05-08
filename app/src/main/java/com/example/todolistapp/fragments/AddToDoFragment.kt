package com.example.todolistapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.todolistapp.DBHelper
import com.example.todolistapp.DataTodo
import com.example.todolistapp.R
import com.google.android.material.textfield.TextInputEditText


class AddToDoFragment : Fragment() {
    lateinit var tv_title: TextInputEditText
    lateinit var tv_body: TextInputEditText
    lateinit var btn_save: Button
    var isDone = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_to_do, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        tv_title = view.findViewById(R.id.Add_TE_TitleInput)
        tv_body = view.findViewById(R.id.Add_TE_BodyInput)
        btn_save = view.findViewById(R.id.Add_Btn_Save)

        btn_save.setOnClickListener {

            if (tv_title.text.isNullOrEmpty()) {
                Toast.makeText(activity, "Enter Title Of To Do", Toast.LENGTH_SHORT).show()
            } else if (tv_body.text.isNullOrEmpty()) {
                Toast.makeText(activity, "Enter Details Of To Do", Toast.LENGTH_SHORT).show()
            } else {
                 val dbHelper= DBHelper(requireContext())
                dbHelper.insertToDo(
                    DataTodo(
                        0,
                        tv_title.text.toString(),
                        tv_body.text.toString(),
                        isDone
                    )
                )
                Toast.makeText(activity, "saved To Do", Toast.LENGTH_SHORT).show()
                var action=AddToDoFragmentDirections.actionAddToDoFragmentToShowDataFragment()
                findNavController().navigate(action)

            }


        }
    }


}