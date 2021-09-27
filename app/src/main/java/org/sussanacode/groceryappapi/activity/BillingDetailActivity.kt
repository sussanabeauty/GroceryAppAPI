package org.sussanacode.groceryappapi.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import org.sussanacode.groceryappapi.R
import org.sussanacode.groceryappapi.databinding.ActivityBillingDetailBinding
import org.sussanacode.groceryappapi.model.ShippingAddress
import org.sussanacode.groceryappapi.sql.AddressDao

class BillingDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityBillingDetailBinding
    lateinit var requestQueue: RequestQueue
    lateinit var addressDao: AddressDao



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBillingDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        requestQueue = Volley.newRequestQueue(baseContext)
        addressDao = AddressDao(baseContext)


        binding.btnback.setOnClickListener {
            startActivity(Intent(baseContext, HomeScreenActivity::class.java))
        }

        setUpEvents()

    }



    private fun setUpEvents() {
        binding.btnCofirmdetails.setOnClickListener {


            val username = binding.etusername.text.toString()
            val mobile = binding.etmobile.text.toString()
            val email = binding.etemail.text.toString()
            val houseNo = binding.etdeliveryaddressnum.text.toString().toInt()
            val address = binding.etdeliveryaddress.text.toString()
            val city = binding.etdeliverycity.text.toString()
            val state = binding.etdeliverystate.text.toString()
            val zip = binding.etdeliveryzip.text.toString()
            val isPrimary = if(binding.isPrimary.isChecked){ 1 }else { 0 }

            val deliveryAddress = ShippingAddress(0, username, mobile, email, houseNo, address, city, state, zip, isPrimary)

            val savedAddress = addressDao.addAddress(deliveryAddress)

            if(savedAddress){
                Toast.makeText(baseContext, "Address was successfully added", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(baseContext, "Address was not successfully added", Toast.LENGTH_LONG).show()
            }
        }

    }

}