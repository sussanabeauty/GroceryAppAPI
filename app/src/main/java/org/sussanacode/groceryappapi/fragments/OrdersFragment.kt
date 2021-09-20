package org.sussanacode.groceryappapi.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.sussanacode.groceryappapi.databinding.FragmentOrdersBinding

class OrdersFragment : Fragment() {

    lateinit var binding: FragmentOrdersBinding
//    lateinit var orderList: ArrayList<>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentOrdersBinding.inflate(layoutInflater, container, false)

        Toast.makeText(activity, "Orders Page", Toast.LENGTH_LONG).show()
        return binding.root
    }
}