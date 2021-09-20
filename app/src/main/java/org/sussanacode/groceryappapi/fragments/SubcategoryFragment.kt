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
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import org.sussanacode.groceryappapi.adapters.SubcategoryAdapter
import org.sussanacode.groceryappapi.databinding.FragmentCategoryBinding
import org.sussanacode.groceryappapi.databinding.FragmentSubcategoryBinding
import org.sussanacode.groceryappapi.model.Category
import org.sussanacode.groceryappapi.model.Subcategory

class SubcategoryFragment : Fragment() {

    lateinit var binding: FragmentSubcategoryBinding
    lateinit var requestQueue: RequestQueue
    lateinit var imageLoader: ImageLoader
    lateinit var subcatAdapter: SubcategoryAdapter
    val subcategories: ArrayList<Subcategory> = ArrayList()
    var catID : Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubcategoryBinding.inflate(layoutInflater, container, false)

        requestQueue = Volley.newRequestQueue(context)
        imageLoader = ImageLoader(requestQueue, cache)
        binding.rvsubcat.layoutManager = GridLayoutManager(context, 2)

       getCatData()
        binding.btnback.setOnClickListener { activity?.supportFragmentManager?.popBackStack() }
        return binding.root
    }

    fun getCategoryID(category: Category){ catID = category.catID }

    private fun getCatData() {

        val subcategoryUrl = "https://grocery-second-app.herokuapp.com/api/subcategory/${catID}"
        val arrrequest = JsonObjectRequest(
            Request.Method.GET, subcategoryUrl, null,

            Response.Listener<JSONObject>{ response ->

                if(response.getBoolean("error")){
                    Toast.makeText(context, "Failed to get subcategory response", Toast.LENGTH_LONG).show()
                }else {

                    try{

                        val subcatdata = response.getJSONArray("data")

                        for(i in 0 until subcatdata.length()) {
                            val catObj = subcatdata.getJSONObject(i)

                            val subname = catObj.getString("subName");
                            val subImg = catObj.getString("subImage");
                            val subimgUrl = "https://rjtmobile.com/grocery/images/$subImg"
                            val subcatID = catObj.getInt("subId")
                            val subcategory = Subcategory(subcatID, subname, subimgUrl)
                            subcategories.add(subcategory)
                        }

                        subcatAdapter = SubcategoryAdapter(subcategories, imageLoader)
                        binding.rvsubcat.adapter = subcatAdapter


                        //send subcategory id to product fragment
                        subcatAdapter.setOnSubcategoryClickListener{ subcategory, position ->
                            if(this::onSubcategoryClickListener.isInitialized){
                                onSubcategoryClickListener(subcategory)
                            }

                        }

                    }catch (e: JSONException){
                        e.printStackTrace()
                        Toast.makeText(context, "failed to retrieve subcategory object", Toast.LENGTH_LONG).show()
                    }
                }
            }, Response.ErrorListener { error ->
                error.printStackTrace()
                Toast.makeText(context, "Error $error", Toast.LENGTH_LONG).show()
            }

        )
        requestQueue.add(arrrequest)


    }



    //load images using bitmap

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


    fun setOnClickSubcategory(listener: (Subcategory) -> Unit){
        onSubcategoryClickListener = listener
    }
    lateinit var  onSubcategoryClickListener: (Subcategory) -> Unit


}