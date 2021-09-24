package org.sussanacode.groceryappapi.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.sussanacode.groceryappapi.R
import org.sussanacode.groceryappapi.databinding.ActivityCheckoutBinding
import org.sussanacode.groceryappapi.fragments.CartViewFragment
import org.sussanacode.groceryappapi.sql.AddressDao
import org.sussanacode.groceryappapi.sql.CartDAO
import org.sussanacode.groceryappapi.sql.PaymentDao

class CheckoutActivity : AppCompatActivity() {
    lateinit var binding: ActivityCheckoutBinding
    lateinit var addressDao: AddressDao
    lateinit var paymentDao: PaymentDao
    lateinit var cartDAO: CartDAO


    var itemcount: Int? = 0
    var subtotal: Double? = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addressDao = AddressDao(baseContext)
        paymentDao = PaymentDao(baseContext)

      //  cartFragment = CartViewFragment()

        binding.btnback.setOnClickListener {

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CartViewFragment()).commit()

                //.add(R.id.fragment_container, CartViewFragment()).re.commit()
//                .addToBackStack("ProductFragment").commit()
        }

        setUpevents()

    }


    private fun setUpevents() {
        val extras = Bundle()

        itemcount = intent.extras?.getInt("itemcount")
        subtotal = intent.extras?.getDouble("subtotal")


        binding.tvitems.text = "Items (${itemcount.toString()}): \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t $${subtotal.toString()}"



        binding.tvshiphandling.text = 50.toString()
        val shipping:Double = binding.tvshiphandling.getText().toString().toDouble()
        binding.tvshiphandling.setText("Shipping and Handling: \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t $${shipping.toString()}")


        binding.tvsavings.text = 50.toString()
        val savings:Double = binding.tvsavings.getText().toString().toDouble()
        binding.tvsavings.text = "Coupon Savings: \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t $${savings.toString()}"



        binding.tvtotalbe4tax.text = subtotal.toString()
        val totalbe4tax = binding.tvtotalbe4tax.getText().toString().toDouble()
        binding.tvtotalbe4tax.setText("Total before tax: \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t $${totalbe4tax.toString()}")


        binding.tvestimatedtax.text = 0.0.toString()
        val estimatedtax = binding.tvestimatedtax.getText().toString().toDouble()
        binding.tvestimatedtax.setText("Estimated tax: \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t $${estimatedtax.toString()}")


        val grandtot = subtotal!! + shipping + totalbe4tax + estimatedtax - savings

        binding.tvorderrtotal.text = grandtot.toString()
        val ordertotal = binding.tvorderrtotal.getText().toString().toDouble()

        binding.tvorderrtotal.setText("Order Total: \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t $${ordertotal.toString()}")


        //read user address from db
        var deliveryaddress = binding.tvuseraddress.setText(addressDao.getAddress().toString())

        //addpayment
        binding.tvpayment.text = paymentDao.getPayment().toString()


        extras.putDouble("shipping", shipping)
        extras.putDouble("savings", savings)
        extras.putDouble("totalbe4tax", totalbe4tax)
        extras.putDouble("estimatedtax", estimatedtax)
        extras.putDouble("ordertotal", ordertotal)



        //include other payment gateways
        binding.btnProceedwithorder.setOnClickListener {
            val orderIntent = Intent(baseContext, OrderSummeryActivity::class.java)
            orderIntent.putExtras(extras)
            startActivity(orderIntent)
        }



    }
}