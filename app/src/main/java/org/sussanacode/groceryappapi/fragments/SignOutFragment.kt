package org.sussanacode.groceryappapi.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.sussanacode.groceryappapi.databinding.FragmentSignoutBinding

class SignOutFragment : Fragment() {

    lateinit var binding: FragmentSignoutBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentSignoutBinding.inflate(layoutInflater, container, false)

        Toast.makeText(activity, "SignOut Page", Toast.LENGTH_LONG).show()
        return binding.root
    }
}