package com.example.todolistapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.todolistapp.DBHelper
import com.example.todolistapp.DataTodo
import com.example.todolistapp.R
import com.google.android.material.textfield.TextInputEditText


class UpdateToDoFragment : Fragment() {

    private lateinit var et_title: TextInputEditText
    private lateinit var et_body: TextInputEditText
    private lateinit var chBox_isDone: CheckBox
    private lateinit var btn_save: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_to_do, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_save = view.findViewById(R.id.Update_Btn_Save)
        et_title = view.findViewById(R.id.Update_TE_TitleInput)
        et_body = view.findViewById(R.id.Update_TE_BodyInput)
        chBox_isDone = view.findViewById(R.id.Update_ChBox_Done)
        val dbHelper = DBHelper(requireContext())
        val toDoId = arguments?.getInt("toDoId")
        val toDo = dbHelper.readOneToDoById(toDoId!!)

        et_title.setText(toDo!!.title)
        et_body.setText(toDo.body)
        chBox_isDone.isChecked = toDo.isDone

        btn_save.setOnClickListener {
            if (et_title.text.toString().isEmpty()) {
                Toast.makeText(activity, "Enter Title Of To Do", Toast.LENGTH_LONG).show()

            }else{
                dbHelper.updateToDo(DataTodo(
                    toDo.id,
                    et_title.text.toString(),
                    et_body.text.toString(),
                    chBox_isDone.isChecked
                ))
                val action=UpdateToDoFragmentDirections.actionUpdateToDoFragmentToShowDataFragment()
                findNavController().navigate(action)
            }
        }

    }
}