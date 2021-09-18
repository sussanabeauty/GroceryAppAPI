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
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import org.sussanacode.groceryappapi.adapters.CategoryAdapter
import org.sussanacode.groceryappapi.databinding.FragmentCategoryBinding
import org.sussanacode.groceryappapi.model.Category

class CategoryFragment: Fragment() {
    lateinit var binding: FragmentCategoryBinding
    lateinit var requestQueue: RequestQueue
    lateinit var imageLoader: ImageLoader
    lateinit var catAdapter: CategoryAdapter
    var categoryList : ArrayList<Category> = ArrayList<Category>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)

        requestQueue = Volley.newRequestQueue(context)
        imageLoader = ImageLoader(requestQueue, cache)
        binding.rvCategory.layoutManager = GridLayoutManager(context, 2)

        getCatData()
        return binding.root
    }



    private fun getCatData() {
        val arrrequest = JsonObjectRequest(
            Request.Method.GET, CATEGORY_URL, null,

            Response.Listener<JSONObject>{ response ->

                if(response.getBoolean("error")){
                    Toast.makeText(context, "Failed to get category response", Toast.LENGTH_LONG).show()
                }else {

                    try{
                        Log.d(":Error::", "${response.getBoolean("error")}")
                        val categorydata = response.getJSONArray("data")

                        for(i in 0 until categorydata.length()) {
                            val catObj = categorydata.getJSONObject(i)

                            val catname = catObj.getString("catName");
                            val catImgUrl = catObj.getString("catImage");
                            val imgUrl = "https://rjtmobile.com/grocery/images/$catImgUrl"
                            val categoryID = catObj.getInt("catId")
                            val category = Category(categoryID, catname, imgUrl)
                            categoryList.add(category)
                        }
                        catAdapter = CategoryAdapter(categoryList, imageLoader)
                        binding.rvCategory.adapter = catAdapter

                        //send category id to subcategory fragment
                        catAdapter.setOnCategoryClickListener{ category, position ->
                            if(this::onCategoryClickListener.isInitialized){
                                onCategoryClickListener(category)
                            }

                        }

                    }catch (e: JSONException){
                        e.printStackTrace()
                        Toast.makeText(context, "failed to retrieve category object", Toast.LENGTH_LONG).show()
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

    companion object{
        const val CATEGORY_URL = "https://grocery-second-app.herokuapp.com/api/category"
    }



    fun setOnClickCategory(listener: (Category) -> Unit){
        onCategoryClickListener = listener
    }
    lateinit var  onCategoryClickListener: (Category) -> Unit
}