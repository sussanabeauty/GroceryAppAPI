package org.sussanacode.groceryappapi.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.sussanacode.groceryappapi.activity.LogInActivity
import org.sussanacode.groceryappapi.databinding.FragmentSignoutBinding

class SignOutFragment : Fragment() {

    lateinit var binding: FragmentSignoutBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentSignoutBinding.inflate(layoutInflater, container, false)


        signoutUser()
        return binding.root
    }

    private fun signoutUser() {

        val sharedPref = context?.getSharedPreferences("user_checked", Context.MODE_PRIVATE)

        if (sharedPref != null) {

            sharedPref?.edit()?.remove("User_Key")?.apply()
            sharedPref?.edit()?.remove("Pass_Key")?.apply()

            Toast.makeText(context, " User Log Out ", Toast.LENGTH_LONG).show()
            startActivity(Intent(activity, LogInActivity::class.java))
        } else {
            Toast.makeText(context, " User does not exit ", Toast.LENGTH_LONG).show()
        }
    }
}