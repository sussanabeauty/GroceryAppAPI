package org.sussanacode.groceryappapi.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONException
import org.json.JSONObject
import org.sussanacode.groceryappapi.adapters.ProductAdapter
import org.sussanacode.groceryappapi.databinding.FragmentProfileBinding
import org.sussanacode.groceryappapi.model.Product

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    lateinit var requestQueue: RequestQueue


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

       // loadProfile()

        Toast.makeText(activity, "Profile Page", Toast.LENGTH_LONG).show()
        return binding.root
    }

}