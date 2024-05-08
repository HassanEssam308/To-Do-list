package com.example.todolistapp.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.DBHelper
import com.example.todolistapp.DataTodo
import com.example.todolistapp.R
import com.example.todolistapp.ToDoAdapter
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ShowDataFragment : Fragment() {
    private lateinit var recyclerView_ShowData: RecyclerView
    private lateinit var view_Shimmer: ShimmerFrameLayout
    private lateinit var floatingBtn_add: FloatingActionButton
    private lateinit var tv_noData: TextView
    private lateinit var dbHelper: DBHelper
    private lateinit var toDoAdapter: ToDoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        floatingBtn_add = view.findViewById(R.id.ShowDataFR_FloatingBtn_add)
        tv_noData = view.findViewById(R.id.ShowDataFR_TV_NoData)
        view_Shimmer = view.findViewById(R.id.ShowDataFR_view_ShimmerContainer)
        recyclerView_ShowData = view.findViewById(R.id.ShowDataFR_RecView_data)
        view_Shimmer.startShimmer()
        //Initialize DataBase
        dbHelper = DBHelper(requireContext())
        val toDoList = dbHelper.readAllToDo()

        // Zero state check  if there are data in Sqlite
        if (toDoList.isEmpty()) {
            recyclerView_ShowData.isGone = true
            tv_noData.isVisible = true
        } else {

            Handler(Looper.getMainLooper())
                .postDelayed({
                    view_Shimmer.stopShimmer()
                    view_Shimmer.visibility = View.GONE
                    createRecyclerView(toDoList)
                }, 5000)

        }


        floatingBtn_add.setOnClickListener {
            val action = ShowDataFragmentDirections.actionShowDataFragmentToAddToDoFragment()
            findNavController().navigate(action)
        }


    }

    private fun createRecyclerView(toDoList: ArrayList<DataTodo>) {
        recyclerView_ShowData.isVisible = true
        tv_noData.isGone = true

        recyclerView_ShowData.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        toDoAdapter = ToDoAdapter(toDoList, requireContext())
        recyclerView_ShowData.adapter = toDoAdapter

        toDoAdapter.setOnItemListener(object : ToDoAdapter.RecyclerViewEvent {
            override fun onItemClick(position: Int, dataTodo: DataTodo) {
                val action =
                    ShowDataFragmentDirections.actionShowDataFragmentToUpdateToDoFragment(
                        dataTodo.id
                    )
                findNavController().navigate(action)
            }

            override fun onLongClick(position: Int, dataTodo: DataTodo) {
                deleteToDo(dataTodo)
            }
        })
    }

    private fun deleteToDo(dataTodo: DataTodo) {

        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Delete:${dataTodo.title} ")
        alertDialogBuilder.setMessage("Are you sure you want to Delete this TO DO ?")

        alertDialogBuilder.setPositiveButton("Yes") { _: DialogInterface, i: Int ->

            dbHelper.deleteToDo(dataTodo.id)
            findNavController().navigate(findNavController().currentDestination!!.id)
            Toast.makeText(
                requireContext(),
                "TO DO is Removed",
                Toast.LENGTH_SHORT
            ).show()

        }
        alertDialogBuilder.setNegativeButton(
            "Cancel"
        ) { dialogInterface: DialogInterface, i: Int -> }
        alertDialogBuilder.create().show()


    }


}