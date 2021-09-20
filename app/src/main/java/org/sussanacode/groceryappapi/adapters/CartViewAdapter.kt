package org.sussanacode.groceryappapi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import org.sussanacode.groceryappapi.databinding.HolderCartBinding
import org.sussanacode.groceryappapi.holder.CartViewHolder
import org.sussanacode.groceryappapi.model.Cart
import org.sussanacode.groceryappapi.model.Product

class CartViewAdapter (val cartItems: ArrayList<Cart>, val imageLoader: ImageLoader) :
    RecyclerView.Adapter<CartViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HolderCartBinding.inflate(layoutInflater, parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position], imageLoader)

//        if(this::productIncrementCartListener.isInitialized) {
//            holder.itemView.setOnClickListener {
//                productIncrementCartListener(cartItems[position], position)
//            }
//        }
    }

    override fun getItemCount() = cartItems.size

    lateinit var productIncrementCartListener: (Product, Int) -> Unit
    lateinit var productdecrementCartListener: (Product, Int) -> Unit


    fun setOnIncrementProductListener(listener: (Product, Int) -> Unit){
        productIncrementCartListener = listener
    }

    fun setOndecrementProductListener(listener: (Product, Int) -> Unit){
        productdecrementCartListener = listener
    }
}