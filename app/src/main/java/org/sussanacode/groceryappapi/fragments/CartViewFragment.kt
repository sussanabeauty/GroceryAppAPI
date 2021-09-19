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

    lateinit var cart: Cart

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



        binding.rvcartitem.layoutManager = LinearLayoutManager(context)

        //addProducts()
        getProductDetails()
       // getcartdetails()

        binding.btnback.setOnClickListener { activity?.supportFragmentManager?.popBackStack() }


        return binding.root
    }

    //getproductbyname

    fun getproductByName(product: Product){
        productname = product.productName
    }


    private fun getProductDetails() {

//        https://grocery-second-app.herokuapp.com/api/products/search/Chicken%20Hariyali%20150%20grams
        if(productname != null){
            val productUrl = "https://grocery-second-app.herokuapp.com/api/products/search/${productname}"
            Log.d("Sub Cat url", "$productUrl")


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
                                Log.d("product Image Url", " $productimgUrl")

                                val quantity = cart.quantity + 1
                                val cart = Cart(0, productname, quantity, productimgUrl, productprice)
                                cartList.add(cart)

                                //save to database
                                val saveProducttoCartDB = CartDAO(requireContext()).addToCart(cart)
                                Log.d("CartDB", "${saveProducttoCartDB.toString()}")
                            }

                            cartAdapter = CartViewAdapter(cartList, imageLoader)
                            binding.rvcartitem .adapter = cartAdapter


//                            //addproduct to cart
//                            productAdapter.setOnAddProductListener { product, position ->
//                                if(this::onProductClickListener.isInitialized){
//                                    onProductClickListener(product)
//                                }
//                            }


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

    private fun addProducts() {



    }


    fun setOnClickaddProduct(listener: (Subcategory) -> Unit){
        onSubcategoryClickListener = listener
    }
    lateinit var  onSubcategoryClickListener: (Subcategory) -> Unit



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