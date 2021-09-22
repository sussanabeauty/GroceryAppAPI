package org.sussanacode.groceryappapi.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.util.LruCache
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import org.sussanacode.groceryappapi.activity.CheckoutActivity
import org.sussanacode.groceryappapi.adapters.CartViewAdapter
import org.sussanacode.groceryappapi.adapters.ProductAdapter
import org.sussanacode.groceryappapi.databinding.FragmentCartViewBinding
import org.sussanacode.groceryappapi.model.Cart
import org.sussanacode.groceryappapi.model.Product
import org.sussanacode.groceryappapi.model.Subcategory
import org.sussanacode.groceryappapi.sql.CartDAO


class CartViewFragment: Fragment() {
    lateinit var binding: FragmentCartViewBinding

    lateinit var requestQueue: RequestQueue
    lateinit var imageLoader: ImageLoader
    lateinit var cartAdapter: CartViewAdapter



    var subtotal: Double = 0.0
    var itemcount = 0
    var productquantity = 0;
    val productprice = 0.0
    val cartList: ArrayList<Cart> = ArrayList()
    var productname: String? = null;


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCartViewBinding.inflate(layoutInflater, container, false)

        requestQueue = Volley.newRequestQueue(context)
        imageLoader = ImageLoader(requestQueue, cache)

        cartAdapter = CartViewAdapter(cartList, imageLoader)


        binding.rvcartitem.layoutManager = LinearLayoutManager(context)

        //addProducts()
        getProductDetails()

        //read date from the database
        binding.btncheckout.setOnClickListener {
            itemcount
            val checkoutIntent = Intent(context, CheckoutActivity::class.java)
            checkoutIntent.putExtra("itemcount", itemcount)
            checkoutIntent.putExtra("subtotal", subtotal)

            startActivity(checkoutIntent)
        }

        binding.btnback.setOnClickListener { activity?.supportFragmentManager?.popBackStack() }

        return binding.root
    }


    //getproductbyname
    fun getproductByName(product: Product){
        productname = product.productName
    }


    private fun getProductDetails() {

        if(productname != null){
            val productUrl = "https://grocery-second-app.herokuapp.com/api/products/search/${productname}"

            //read data
            val arrrequest = JsonObjectRequest(
                Request.Method.GET, productUrl, null,

                Response.Listener<JSONObject>{ response ->

                    if(response.getBoolean("error")){
                        Toast.makeText(context, " product data was no retrieve $response", Toast.LENGTH_LONG).show()
                    }else {
                        try{
                            val productdetails = response.getJSONArray("data")

                            for(i in 0 until productdetails.length()) {
                                val productObj = productdetails.getJSONObject(i)

                                val productname = productObj.getString("productName");
                                val productprice = productObj.getDouble("price")
                                val productImg = productObj. getString("image");
                                val productimgUrl = "https://rjtmobile.com/grocery/images/$productImg"


                                val c: Cart? = null
                                val quantity = c?.quantity ?: + 1
                                val cart = Cart(0, productname, quantity, productimgUrl, productprice)
                                cartList.add(cart)

                                //save to database
                                val saveProducttoCartDB = CartDAO(requireContext()).addToCart(cart)

                                for (i in cartList){
                                    subtotal += (i.productprice * i.quantity)
                                    itemcount = itemcount + 1

//                                    binding.btncheckout.text = "Proceed with Checkout (${itemcount})"
//                                    binding.tvsubtotal.text = "Subtotal: $${subtotal}"
                                }

                               // subtotal = productquantity

                                binding.btncheckout.text = "Proceed with Checkout (${itemcount})"
                                binding.tvsubtotal.text = "Subtotal: $${subtotal}"
//                                cartAdapter = CartViewAdapter(cartList, imageLoader)
                                binding.rvcartitem.adapter = cartAdapter


//                                cartAdapter.setOnIncrementProductListener { cart, position ->
//                                    cart.quantity += productquantity
//                                    cart.productprice *=  cart.productprice
//                                }
                            }

                        }catch (e: JSONException){
                            e.printStackTrace()
                            Toast.makeText(context, "failed to retrieve Product object", Toast.LENGTH_LONG).show()
                        }
                    }
                }, Response.ErrorListener { error ->
                    error.printStackTrace()
                    Toast.makeText(context, "Error $error", Toast.LENGTH_LONG).show()
                }

            )
            requestQueue.add(arrrequest)

        }else{
            return
        }

        setUpAddProductListener()
        setUpMinusProductLitener()

    }

    private fun setUpMinusProductLitener() {

    }

    private fun setUpAddProductListener() {

//        cartAdapter.setOnIncrementProductListener { cart, position ->
//           cart.quantity += productquantity
//            cart.productprice *=  cart.productprice
//        }
    }


    fun setOnClickaddProduct(listener: (Subcategory) -> Unit){
        onSubcategoryClickListener = listener
    }
    lateinit var  onSubcategoryClickListener: (Subcategory) -> Unit



    lateinit var onClickProductAddListener: (Cart) -> Unit
    lateinit var onClickProductMinusListener: (Product) -> Unit


    val cache = object : ImageLoader.ImageCache {

        val lruCache: LruCache<String, Bitmap> = LruCache(100)

        override fun getBitmap(url: String?): Bitmap? {
            return lruCache[url]
        }

        override fun putBitmap(url: String?, bitmap: Bitmap?) {
            url?.let{
                lruCache.put(it, bitmap)
            }
        }
    }
}