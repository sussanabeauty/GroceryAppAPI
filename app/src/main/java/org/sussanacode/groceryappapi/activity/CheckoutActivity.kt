package org.sussanacode.groceryappapi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.sussanacode.groceryappapi.R
import org.sussanacode.groceryappapi.databinding.ActivityCheckoutBinding
import org.sussanacode.groceryappapi.fragments.CartViewFragment
import org.sussanacode.groceryappapi.sql.AddressDao
import org.sussanacode.groceryappapi.sql.CartDAO

class CheckoutActivity : AppCompatActivity() {
    lateinit var binding: ActivityCheckoutBinding
    lateinit var cartFragment: CartViewFragment
    lateinit var addressDao: AddressDao
    lateinit var cartDAO: CartDAO

    var itemcount: Int? = 0
    var subtotal: Double? = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addressDao = AddressDao(baseContext)

      //  cartFragment = CartViewFragment()

        binding.btnback.setOnClickListener {

            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, CartViewFragment()).commit()
//                .addToBackStack("ProductFragment").commit()
        }

        setUpevents()

    }


    private fun setUpevents() {
        itemcount = intent.extras?.getInt("itemcount")
        subtotal = intent.extras?.getDouble("subtotal")

        binding.tvitems.text = "Items (${itemcount.toString()}): \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t $${subtotal.toString()}"
        val shippingNhandling = binding.tvshiphandling.text.toString()
        val couponsavings = binding.tvsavings.text.toString()
        binding.tvtotalbe4tax.text = "Total before tax: \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t $${subtotal.toString()}"
        binding.tvestimatedtax.text = "Estimated tax to be collected \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t ${0.0}"
        val ordertotal = binding.tvorderrtotal.text.toString()



        //read user address from db
        binding.tvuseraddress.text = addressDao.getAddress().toString()




    }
}