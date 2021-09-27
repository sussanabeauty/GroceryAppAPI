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
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.sussanacode.groceryappapi.activity.CheckoutActivity
import org.sussanacode.groceryappapi.adapters.CartViewAdapter
import org.sussanacode.groceryappapi.databinding.FragmentCartViewBinding
import org.sussanacode.groceryappapi.holder.CartViewHolder
import org.sussanacode.groceryappapi.model.Cart
import org.sussanacode.groceryappapi.model.Product
import org.sussanacode.groceryappapi.sql.CartDAO


class CartViewFragment: Fragment() {
    lateinit var binding: FragmentCartViewBinding

    lateinit var requestQueue: RequestQueue
    lateinit var imageLoader: ImageLoader
    lateinit var cartAdapter: CartViewAdapter
    lateinit var cartDao : CartDAO

    var subtotal: Double = 0.0
    var itemcount = 0
    var productquantity = 0;
    var productprice = 0.0
    var cartList: ArrayList<Cart>? = null

    lateinit var productname: String
    lateinit var productID: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCartViewBinding.inflate(layoutInflater, container, false)

        requestQueue = Volley.newRequestQueue(context)
        imageLoader = ImageLoader(requestQueue, cache)
        cartDao =  CartDAO(requireActivity())

        // cartAdapter = CartViewAdapter(cartList, imageLoader)


        binding.rvcartitem.layoutManager = LinearLayoutManager(context)

        //addProducts()
        getProductDetails()

        //read date from the database
        binding.btncheckout.setOnClickListener {
            itemcount
            val checkoutIntent = Intent(context, CheckoutActivity::class.java)
            checkoutIntent.putExtra("productID", productID)
            checkoutIntent.putExtra("productName", productname)
            checkoutIntent.putExtra("productPrice", productprice)
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
                Request.Method.GET, productUrl, null, { response ->

                    if(response.getBoolean("error")){
                        Toast.makeText(context, " product data was no retrieve $response", Toast.LENGTH_LONG).show()
                    }else {
                        try{
                            val productdetails = response.getJSONArray("data")

                            for(i in 0 until productdetails.length()) {
                                val productObj = productdetails.getJSONObject(i)

                                productname = productObj.getString("productName");
                                productprice = productObj.getDouble("price")
                                val productImg = productObj. getString("image");
                                val productimgUrl = "https://rjtmobile.com/grocery/images/$productImg"
                                productID = productObj.getString("_id")


                                val quantity = 1

                                val cart = Cart(0, productID, productname, quantity, productimgUrl, productprice)

                                //save to database
                                val saveProducttoCartDB = cartDao.addToCart(cart)

                                if(saveProducttoCartDB){
                                    Toast.makeText(context, "Cart Items were saved successfully", Toast.LENGTH_LONG).show()
                                }else Toast.makeText(context, "Failed to save cart items", Toast.LENGTH_LONG).show()
                            }

                            displaycartItem()

                        }catch (e: JSONException){
                            e.printStackTrace()
                            Toast.makeText(context, "failed to retrieve Product object", Toast.LENGTH_LONG).show()
                        }
                    }
                }, { error ->
                    error.printStackTrace()
                    Toast.makeText(context, "Error $error", Toast.LENGTH_LONG).show()
                })
            requestQueue.add(arrrequest)

        }else{ return }

//        displaycartItem()

    }

    private fun displaycartItem() {

        cartList = cartDao.getItemsInCart()

        cartAdapter = CartViewAdapter(cartList, imageLoader)
        binding.rvcartitem.adapter = cartAdapter

        subtotal = 0.0
        itemcount = 0

        for (i in cartList!!){
            var productSubtotal: Double  = i.productprice * i.quantity

            subtotal += productSubtotal
            itemcount += i.quantity
            binding.btncheckout.text = "Proceed with Checkout (${itemcount})"
            binding.tvsubtotal.text = "Subtotal: $${subtotal}"

        }

        //setupAddListener()
//        setUpMinusListener()

    }

    //  private fun setUpMinusListener() {

//        cartAdapter.setOnIncrementProductListener { cart ->
//            val numquanty = cart.quantity + 1
//            val productsubtotal = cart.quantity * cart.productprice
//
//            cart.quantity = numquanty
//            cart.productprice = productprice
//
//            Toast.makeText(context, "You click minus button", Toast.LENGTH_LONG).show()
//
////            binding.qtyvalue.text = cart.quantity.toString()
////            binding.tvprice.text = "$${productsubtotal.toString()}"
//        }

    // }

//    private fun setupAddListener() {
//
//        cartAdapter.setOnAddProductListener { cart, position ->
//
//            cart.quantity += 1
//            val productsubtotal = cart.quantity * cart.productprice
//
//
//           // cartAdapter.totalqty.text = cart.quantity.toString()
//          //  cartAdapter.totalprice.text = "$${productsubtotal.toString()}"
//
//        //    holder.binding.qtyvalue.text = it[position].quantity.toString()
////            holder.binding.tvprice.text = "$${productsubtotal.toString()}"
//
//
//            Toast.makeText(context, "You click add button", Toast.LENGTH_LONG).show()
//
////            binding.qtyvalue.text = cart.quantity.toString()
////            binding.tvprice.text = "$${productsubtotal.toString()}"
//        }
//
//    }



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