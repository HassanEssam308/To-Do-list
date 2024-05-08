package com.example.todolistapp.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.todolistapp.R
import com.facebook.shimmer.ShimmerFrameLayout


class ShimmerFragment : Fragment() {
    lateinit var view_Shimmer: ShimmerFrameLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shimmer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view_Shimmer = view.findViewById(R.id.Shimmer_view_container)
        view_Shimmer.startShimmer()

        Handler(Looper.getMainLooper()).postDelayed({
            view_Shimmer.stopShimmer()

//            view_Shimmer.visibility = View.GONE
        }, 5000)
    }


}