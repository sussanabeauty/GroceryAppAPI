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
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import org.sussanacode.groceryappapi.adapters.ProductAdapter
import org.sussanacode.groceryappapi.databinding.FragmentProductBinding
import org.sussanacode.groceryappapi.model.Product
import org.sussanacode.groceryappapi.model.Subcategory

class ProductFragment : Fragment() {

    lateinit var binding: FragmentProductBinding
    lateinit var requestQueue: RequestQueue
    lateinit var imageLoader: ImageLoader
    lateinit var productAdapter: ProductAdapter
    val products: ArrayList<Product> = ArrayList()
    var subcatID : Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductBinding.inflate(layoutInflater, container, false)
        requestQueue = Volley.newRequestQueue(context)
        imageLoader = ImageLoader(requestQueue, cache)

        binding.rvproduct.layoutManager = GridLayoutManager(context, 2)

        getProductdetails()

        binding.btnback.setOnClickListener { activity?.supportFragmentManager?.popBackStack() }


        return binding.root

    }

    fun getSubcategoryID(subcategory: Subcategory){ subcatID = subcategory.subId }



    private fun getProductdetails() {


        val productUrl = "https://grocery-second-app.herokuapp.com/api/products/sub/${subcatID}"
        Log.d("Sub Cat url", "$productUrl")
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
                            val subcatID = productObj.getInt("subId")

                            val product = Product(subcatID, productname, productimgUrl, productprice)
                            products.add(product)
                        }
                        productAdapter = ProductAdapter(products, imageLoader)
                        binding.rvproduct .adapter = productAdapter


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

    }


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