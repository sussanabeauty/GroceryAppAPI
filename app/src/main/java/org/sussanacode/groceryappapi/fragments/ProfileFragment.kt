package org.sussanacode.groceryappapi.fragments

import android.content.Context
import android.location.Address
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.sussanacode.groceryappapi.databinding.FragmentProfileBinding
import org.sussanacode.groceryappapi.model.Payment
import org.sussanacode.groceryappapi.model.ShippingAddress
import org.sussanacode.groceryappapi.sql.AddressDao
import org.sussanacode.groceryappapi.sql.PaymentDao

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    lateinit var addressDao: AddressDao
    lateinit var paymentDao: PaymentDao


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        addressDao = AddressDao(requireActivity())
        paymentDao = PaymentDao(requireActivity())

        Toast.makeText(activity, "Profile Page", Toast.LENGTH_LONG).show()

        setupEvents()

        return binding.root

    }

    private fun setupEvents() {

        val sharedPref = context?.getSharedPreferences("user_checked", Context.MODE_PRIVATE)
        val firstname = sharedPref?.getString("User_FIRSTNAME", "")
        val email = sharedPref?.getString("User_Key", "")

        binding.tvfirstname.text = firstname
        binding.tvusername.text = email.toString()
        Log.d("user name and email", "$firstname     $email")

        val address: ArrayList<ShippingAddress>? = addressDao.getAddress()
        val payment: ArrayList<Payment>? = paymentDao.getPayment()


        var state = ""
        var city = ""

        //get address state and city
        address?.let {
            for (i in it){
                state = i.state
                city = i.city
            }
        }

        binding.tvcitystate.text = "$city, $state"



        //get payment method
        var isPrimary = 0
        payment?.let {
            it.forEach {  pay ->
                if(pay.isPrimary  == 1)
                    isPrimary = 1
            }
        }

        binding.tvpayment.text = "Payment method is $isPrimary =>  Card"


    }


}