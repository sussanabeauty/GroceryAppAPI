package org.sussanacode.groceryappapi.fragments

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
import org.sussanacode.groceryappapi.adapters.CartViewAdapter
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
//    lateinit var cartDao: CartDAO


    //val cartList: ArrayList<Cart> = ArrayList()
    var cartList: ArrayList<Cart>? = null
    var productname: String? = null;


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCartViewBinding.inflate(layoutInflater, container, false)

        requestQueue = Volley.newRequestQueue(context)
        imageLoader = ImageLoader(requestQueue, cache)

        binding.rvcartitem.layoutManager = LinearLayoutManager(context)

        //addProducts()
        getProductDetails()

        binding.btnback.setOnClickListener { activity?.supportFragmentManager?.popBackStack() }

        return binding.root
    }

    private fun setUpMinusProduct() {
        cartAdapter.setOnMinusProductListener { cart, position ->
            cart.quantity--
        }
    }

    private fun setUpPlusProduct() {
        cartAdapter.setOnAddOnProductListener { cart, position ->
            cart.quantity++

        }
    }


    fun getproductByName(product: Product){
        productname = product.productName
    }


    private fun getProductDetails() {
        if(productname != null){
            val productUrl = "https://grocery-second-app.herokuapp.com/api/products/search/${productname}"
            Log.d("Cart url", "$productUrl")



            //read data
            val arrrequest = JsonObjectRequest(
                Request.Method.GET, productUrl, null,

                Response.Listener<JSONObject>{ response ->


                    if(response.getBoolean("error")){

                        Toast.makeText(context, " product data was no retrieve $response", Toast.LENGTH_LONG).show()
                    }else {
                        var cartitems: ArrayList<Cart> = ArrayList<Cart>()
                        try{
                            val productdetails = response.getJSONArray("data")

                            for(i in 0 until productdetails.length()) {
                                val productObj = productdetails.getJSONObject(i)

                                val productname = productObj.getString("productName");
                                val productprice = productObj.getDouble("price")
                                val productImg = productObj. getString("image");
                                val productimgUrl = "https://rjtmobile.com/grocery/images/$productImg"

                                var cart: Cart? = null
                                //refactor code
                                var quantity = (cart?.quantity ?: + 1 )
                                cart = Cart(0, productname, quantity, productimgUrl, productprice)
                                cartList?.add(cart)

                                //getquantity(cartList)

                                val saveProducttoCartDB = CartDAO(requireContext()).addToCart(cart)

                                //read data from cartDB into recyclerview
                                cartList = CartDAO(requireContext()).getItemsInCart()!!
                                Log.d(" Cart Data from DB", "Cart item retrieved successfully" )


                            }


                            var subtotal = 0.0
                            for (i in cartList!!){
                                 subtotal += i.productprice
                            }

                            cartList?.let {
                                cartAdapter = CartViewAdapter(it, imageLoader)
                                binding.rvcartitem.adapter = cartAdapter
                                binding.tvsubtotal.text = "Subtotal: $${subtotal.toString()}"
                            }




//                            //addproduct to cart
//                            cartAdapter.setOnAddOnProductListener { cart, position ->
//                                if(this::onPlusClickListener.isInitialized){
//                                    Log.d("Plus Sign", "$onPlusClickListener")
//                                    cart.quantity++
//                                }
//
//                            }
//
//                            cartAdapter.setOnMinusProductListener { cart, position ->
//                                if(this::onMinusClickListener.isInitialized){
//                                    cart.quantity--
//                                    Log.d("Minus Sign", "$onMinusClickListener")
//                                }
//
//                            }

                            setUpPlusProduct()
                            setUpMinusProduct()


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

    }

    private fun getquantity(cartList: ArrayList<Cart>?) {
        var quantity = 0
        var productname = ""

        val cartMap: MutableMap<Cart, Int> = java.util.LinkedHashMap()

        if (cartList != null) {
            for(cart in cartList){
                if(cartMap.containsKey(cart)){
                    cartMap[cart] = cartMap[cart]!! + 1
                }else{
                    cartMap[cart] = 1
                }
            }
        }


        for((k, v) in cartMap){
            productname = k.toString()
            quantity = v
        }

    }


    lateinit var  onPlusClickListener: (Product, Int) -> Unit
    lateinit var  onMinusClickListener: (Product) -> Unit


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