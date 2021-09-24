package org.sussanacode.groceryappapi.activity

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import org.sussanacode.groceryappapi.databinding.ActivityUpdateAddressBinding
import org.sussanacode.groceryappapi.model.ShippingAddress
import org.sussanacode.groceryappapi.sql.AddressDao

class UpdateAddressActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateAddressBinding
    lateinit var addressDao: AddressDao
    lateinit var myaddress: ShippingAddress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpEvents()

        binding.btnback.setOnClickListener {
            startActivity(Intent(baseContext, AddressViewActivity::class.java))
        }


    }

    private fun setUpEvents() {
        addressDao = AddressDao(baseContext)
        myaddress = intent.extras?.getSerializable("address") as ShippingAddress

        binding.etusername.setText(myaddress.name)
        binding.etmobile.setText(myaddress.mobile)
        binding.etemail.setText(myaddress.email)
        binding.etdeliveryaddress.setText(myaddress.address)
        binding.etdeliverycity.setText(myaddress.city)
        binding.etdeliverystate.setText(myaddress.state)
        binding.etdeliveryzip.setText(myaddress.zip)


        binding.btnupdateAdd.setOnClickListener {
            myaddress.name = binding.etusername.text.toString()
            myaddress.mobile = binding.etmobile.text.toString()
            myaddress.email = binding.etemail.text.toString()
            myaddress.address = binding.etdeliveryaddress.text.toString()
            myaddress.city = binding.etdeliverycity.text.toString()
            myaddress.state = binding.etdeliverystate.text.toString()
            myaddress.zip = binding.etdeliveryzip.text.toString()

            updateAddress(myaddress)
        }

    }

    private fun updateAddress(myaddress: ShippingAddress) {

        val updatedAddress = addressDao.updateAddress(myaddress)

        if(updatedAddress){
            val dataIntent = Intent()
            dataIntent.putExtra("address", myaddress)
            setResult(RESULT_OK, dataIntent)
            Toast.makeText(baseContext, "Address has been updated successfully", Toast.LENGTH_LONG).show()
            finish()
        } else {
            Toast.makeText(baseContext, "Failed to update address record", Toast.LENGTH_LONG).show()
        }
    }
}