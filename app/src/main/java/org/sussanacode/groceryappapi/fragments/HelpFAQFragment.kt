package org.sussanacode.groceryappapi.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.sussanacode.groceryappapi.databinding.FragmentHelpFaqBinding
import org.sussanacode.groceryappapi.databinding.FragmentProfileBinding

class HelpFAQFragment : Fragment() {

    lateinit var binding: FragmentHelpFaqBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHelpFaqBinding.inflate(layoutInflater, container, false)

        Toast.makeText(activity, "Help Page", Toast.LENGTH_LONG).show()
        return binding.root
    }
}