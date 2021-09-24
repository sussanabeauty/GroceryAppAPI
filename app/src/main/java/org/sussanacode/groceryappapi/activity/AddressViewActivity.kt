package org.sussanacode.groceryappapi.activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import org.sussanacode.groceryappapi.adapters.AddressAdapter
import org.sussanacode.groceryappapi.databinding.ActivityAddressViewBinding
import org.sussanacode.groceryappapi.model.ShippingAddress
import org.sussanacode.groceryappapi.sql.AddressDao

class AddressViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddressViewBinding
    lateinit var addressAdapter: AddressAdapter
    lateinit var addressDao: AddressDao
    var addresses: ArrayList<ShippingAddress>? = null
    var position = -1
    val REQUEST_CODE_EDIT_ADDRESS = 500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddAddress.setOnClickListener {
            startActivity(Intent(baseContext, BillingDetailActivity::class.java))
        }

        binding.btnback.setOnClickListener {
            startActivity(Intent(baseContext, HomeScreenActivity::class.java))
        }

        setUpEvents()
    }

    private fun setUpEvents() {
        addressDao = AddressDao(baseContext)
        addresses = addressDao.getAddress()

        binding.rvaddresses.layoutManager = LinearLayoutManager(baseContext)
        addressAdapter = AddressAdapter(addresses)

        binding.rvaddresses.adapter = addressAdapter

        setUpEditAddress()
        setUpDeleteAddress()

    }


    private fun setUpEditAddress() {
        addressAdapter.setOnEditAddressListener { it, position ->
            this.position = position

            val updateAddressIntent = Intent(baseContext, UpdateAddressActivity::class.java)
            updateAddressIntent.putExtra("address", it)

            startActivityForResult(updateAddressIntent, REQUEST_CODE_EDIT_ADDRESS)

        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_CODE_EDIT_ADDRESS){
            if(resultCode == RESULT_OK){
                val updatedAddress = data?.extras?.getSerializable("address") as ShippingAddress
                addresses?.set(position, updatedAddress)
                addressAdapter.notifyItemChanged(position)
            }
        }
    }

    private fun setUpDeleteAddress() {
        addressAdapter.setOndeleteAddressListener { it, position ->
            deleteAddress(it, position)

        }
    }

    private fun deleteAddress(it: ShippingAddress, position: Int) {

        val dialog = AlertDialog.Builder(this)
            .setTitle("Confirm Action")
            .setMessage("Are you sure you want to delete this Address?")
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->

                val deletedAdress = addressDao.deleteAddress(it.addressID)

                if(deletedAdress){
                    addresses?.removeAt(position)
                    addressAdapter.notifyDataSetChanged()
                    Toast.makeText(baseContext, "Address has been deleted successfully", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(baseContext, "Failed to delete Address", Toast.LENGTH_LONG).show()
                }

                dialog.dismiss()
            }).setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            }).create()

        dialog.show()

    }

}