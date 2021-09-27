package org.sussanacode.groceryappapi.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import org.sussanacode.groceryappapi.R
import org.sussanacode.groceryappapi.databinding.ActivityOrderSummeryBinding
import org.sussanacode.groceryappapi.model.Payment
import org.sussanacode.groceryappapi.model.ShippingAddress
import org.sussanacode.groceryappapi.sql.AddressDao
import org.sussanacode.groceryappapi.sql.PaymentDao
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class OrderSummaryActivity : AppCompatActivity() {
    lateinit var binding: ActivityOrderSummeryBinding
    lateinit var addressDao: AddressDao
    lateinit var paymentDao: PaymentDao

    lateinit var extrass: Bundle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderSummeryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        addressDao = AddressDao(baseContext)
        paymentDao = PaymentDao(baseContext)
        extrass = Bundle()

        binding.btnback.setOnClickListener {
            startActivity(Intent(baseContext, CheckoutActivity::class.java))
            finish()
        }

        setupevents()
    }

    private fun setupevents() {
        val intent = intent;
        val extras = intent.extras

        val orderID = extras?.getString("orderID")
        Log.d("Order Id", "$orderID")
        binding.tvorderId.text = "OrderID: $orderID"


        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        binding.tvorderdate.text = "Place on: ${currentDate}"


            var pincode = ""
            var streetname = ""
            var city = ""
            var houseno = ""
            var type = ""

        //read user address from db
        val deliveryadd:ArrayList<ShippingAddress>? = addressDao.getAddress()
        deliveryadd?.let {
            for(i in it){
                if(i.isPrimary == 1){
                    pincode = i.zip
                    streetname = i.address
                    city = i.city
                    houseno = i.houseNo.toString()
                    type = i.state
                }
                else if(i.isPrimary == 0) {
                    pincode = i.zip
                    streetname = i.address
                    city = i.city
                    houseno = i.houseNo.toString()
                    type = i.state
                }
            }
        }

        binding.tvdeliveryAddress.text = "\t\t\t\t\tDelivery Address: \n" +
                "\t\t\t\t\t\t $houseno $streetname \n" +
                "\t\t\t\t\t\t\t $city, $type $pincode"



        //addpayment
        val order_total = extras?.getDouble("ordertotal")
        val payments:ArrayList<Payment>? = paymentDao.getPayment()
        binding.tvpaymentsummary.text = "\t\t\t Payment Summary\n" +
                "Total Price: \t\t\t\t\t\t\t\t\t ${order_total.toString()}\n" +
                "You Paid: \t\t\t\t\t\t\t\t\t\t\t ${order_total.toString()}\n" +
                "Payment Type: \t\t\t Cash"



        extrass.putString("orderId", orderID)
        extrass.putString("order_date", currentDate)
        extrass.putDouble("order_total", order_total!!)


        binding.btntrackorder.setOnClickListener {
            val trackorderIntent = Intent(baseContext, TrackOrderActivity::class.java)
            trackorderIntent.putExtras(extrass)
            startActivity(trackorderIntent)
        }

    }
}