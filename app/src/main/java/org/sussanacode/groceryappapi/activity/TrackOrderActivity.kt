package org.sussanacode.groceryappapi.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import org.sussanacode.groceryappapi.R
import org.sussanacode.groceryappapi.adapters.OrderAdapter
import org.sussanacode.groceryappapi.databinding.ActivityTrackOrderBinding
import org.sussanacode.groceryappapi.model.Order
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TrackOrderActivity : AppCompatActivity() {
    lateinit var binding: ActivityTrackOrderBinding
    lateinit var orderAdapter: OrderAdapter

    val orderList :ArrayList<Order> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvorderreviewList.layoutManager = LinearLayoutManager(baseContext)

        binding.btnback.setOnClickListener {
            startActivity(Intent(baseContext, OrderSummaryActivity::class.java))
            finish()
        }

        setupevents()
    }

    private fun setupevents() {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")


        val intent = intent;
        val extras = intent.extras

        val orderId = extras?.getString("orderId")
        val totalPrice = extras?.getDouble("order_total")
        val orderDt = extras?.getString("order_date")


        val calendar = Calendar.getInstance()
        calendar.time = sdf.parse(orderDt)
        calendar.add(Calendar.DATE, 5)

        val deliveryDT = sdf.format(calendar.time)
        Log.d("Delivery Date", deliveryDT)


        binding.ivpackaging.setColorFilter(Color.argb(50,102, 0, 26))


        orderList.add(Order(orderId, deliveryDT, totalPrice))

        orderAdapter = OrderAdapter(orderList)
        binding.rvorderreviewList.adapter = orderAdapter

    }


}