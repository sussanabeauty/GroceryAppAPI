package org.sussanacode.groceryappapi.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import org.sussanacode.groceryappapi.R
import org.sussanacode.groceryappapi.databinding.ActivityCheckoutBinding
import org.sussanacode.groceryappapi.fragments.CartViewFragment
import org.sussanacode.groceryappapi.model.Cart
import org.sussanacode.groceryappapi.model.Payment
import org.sussanacode.groceryappapi.model.ShippingAddress
import org.sussanacode.groceryappapi.sql.AddressDao
import org.sussanacode.groceryappapi.sql.CartDAO
import org.sussanacode.groceryappapi.sql.PaymentDao

class CheckoutActivity : AppCompatActivity() {
    lateinit var binding: ActivityCheckoutBinding
    lateinit var requestQueue: RequestQueue
    lateinit var addressDao: AddressDao
    lateinit var paymentDao: PaymentDao
    lateinit var cartDAO: CartDAO
    lateinit var extras: Bundle

    var itemcount: Int? = 0
    var subtotal: Double? = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)


        cartDAO = CartDAO(baseContext)

        addressDao = AddressDao(baseContext)
        paymentDao = PaymentDao(baseContext)
        requestQueue = Volley.newRequestQueue(baseContext)

        //  cartFragment = CartViewFragment()

        binding.btnback.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CartViewFragment()).commit()

            //.add(R.id.fragment_container, CartViewFragment()).re.commit()
//                .addToBackStack("ProductFragment").commit()
        }

        setUpevents()
    }


    private fun checkout(shipping: Double, ordertotal: Double, savings: Double, totalbe4tax: Double)
    {

        //get userID
        val sharedPref = baseContext.getSharedPreferences("user_checked", Context.MODE_PRIVATE)
        val userID = sharedPref.getString("USER_ID", "")

        //product details from cartview page
        val productId = intent.extras?.getString("productID")
        val productName = intent.extras?.getString("productName")
        val productPrice = intent.extras?.getDouble("productPrice")


        //get product list from cart
        val prodJsonArray = JSONArray();
        val cartList:ArrayList<Cart>? = cartDAO.getItemsInCart()
         cartList?.let {
               for(i in it) {
                   val item = JSONObject()
                   //item.put("id", cartItem.id")
                   item.put("id", i.productID)
                   item.put("quantity", i.quantity)
                   item.put("price" , i.productprice)
                   item.put("productName", i.productname)

                   prodJsonArray.put(item)
               }
        }


        val paymentJson = JSONObject()
        paymentJson.put("paymentMode", "cash")


        val paymentList: ArrayList<Payment>? = paymentDao.getPayment()

        paymentList?.let {
            for(i in it){
                if(i.isPrimary == 0) {
                    paymentJson.put("paymentMode", "cash")
                }
            }
        }



        //shipping detail
        val addressDaoList: ArrayList<ShippingAddress>? = addressDao.getAddress()
        val shippingJson = JSONObject()
        addressDaoList?.let {
            for(i in it){
                if(i.isPrimary == 1){
                // val shippingJson = JSONObject()
                    shippingJson.put("pincode", i.zip)
                    shippingJson.put("streetName", i.address)
                    shippingJson.put("city", i.city)
                    shippingJson.put("houseNo", i.houseNo)
                    shippingJson.put("type", i.state)
                }
            }
        }


        //order summary
        val orderSummaryJson = JSONObject()
        orderSummaryJson.put("deliveryCharges",shipping)
        orderSummaryJson.put("totalAmount", ordertotal)
        orderSummaryJson.put("discount", savings)
        orderSummaryJson.put("ourPrice", totalbe4tax)

        //gather all data for order
        val orderData = JSONObject()
        orderData.put("shipingAddress", shippingJson)
        orderData.put("payment", paymentJson)
        orderData.put("userId", userID)
        orderData.put("products", prodJsonArray)
        orderData.put("orderSummary", orderSummaryJson)

        val ORDER_URL = "https://grocery-second-app.herokuapp.com/api/orders"

        val jsonRequest = JsonObjectRequest(
            Request.Method.POST, ORDER_URL, orderData,
            Response.Listener<JSONObject>{ response ->

                val errorVal: Boolean = response.getBoolean("error")
                if (errorVal == false) {

                    val OrderResponseData = response.getJSONObject("data")

                    var orderID = OrderResponseData.getString("_id")
                    extras.putString("orderID", orderID)


                    val orderDate = OrderResponseData.getString("date")
                    Log.d("orderdate", orderDate)

                } else if (errorVal == true) {
                    print("error")
                }
            }, { error -> error.printStackTrace() })
        jsonRequest.retryPolicy = DefaultRetryPolicy(10 * 1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        requestQueue.add(jsonRequest)

    }



    private fun setUpevents() {
       // val extras = Bundle()

        extras = Bundle()
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


        checkout(shipping, ordertotal, savings, totalbe4tax)

        extras.putDouble("shipping", shipping)
        extras.putDouble("savings", savings)
        extras.putDouble("totalbe4tax", totalbe4tax)
        extras.putDouble("estimatedtax", estimatedtax)
        extras.putDouble("ordertotal", ordertotal)



        //include other payment gateways
        binding.btnProceedwithorder.setOnClickListener {
            val orderIntent = Intent(baseContext, OrderSummaryActivity::class.java)
            orderIntent.putExtras(extras)
            startActivity(orderIntent)
        }



    }
}