package org.sussanacode.groceryappapi.activity

import android.content.Intent
import android.location.Address
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.sussanacode.groceryappapi.databinding.ActivityOrderSummeryBinding
import org.sussanacode.groceryappapi.model.Payment
import org.sussanacode.groceryappapi.model.ShippingAddress
import org.sussanacode.groceryappapi.sql.AddressDao
import org.sussanacode.groceryappapi.sql.PaymentDao

class OrderSummeryActivity : AppCompatActivity() {
    lateinit var binding: ActivityOrderSummeryBinding
    lateinit var addressDao: AddressDao
    lateinit var paymentDao: PaymentDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderSummeryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addressDao = AddressDao(baseContext)
        paymentDao = PaymentDao(baseContext)

        setupevents()
    }

    private fun setupevents() {
        val intent = intent;
        val extras = intent.extras

        val shipping_handling = extras?.getDouble("shipping")
        Log.d("Shipping", "$shipping_handling")


        val estimated_tax = extras?.getDouble("estimatedtax")
        Log.d("Estimated tax", "$estimated_tax")
        val total_be4_tax = extras?.getDouble("totalbe4tax")
        Log.d("Total be4 tax", "$total_be4_tax")
        val savings = extras?.getDouble("savings")
        Log.d("Savings", "$savings")
        val order_total = extras?.getDouble("ordertotal")
        Log.d("order total", "$order_total")



        //read user address from db
        val deliveryadd:ArrayList<ShippingAddress>? = addressDao.getAddress()
        //var deliveryaddress = binding.tvdeliveryAddress.setText(addressDao.getAddress().toString())

        //addpayment
        val payments:ArrayList<Payment>? = paymentDao.getPayment()
       // binding.tvpaymentsummary.text = paymentDao.getPayment().toString()




    }
}