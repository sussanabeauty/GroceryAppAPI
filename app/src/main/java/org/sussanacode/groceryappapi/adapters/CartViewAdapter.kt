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
        cartItems?.let {
            holder.bind(cartItems[position], imageLoader)


            if (this::productAddOnCartListener.isInitialized) {
                holder.itemView.setOnClickListener { btnIncreaseproduct ->
                    productAddOnCartListener(it[position], position)
                }
            }

            if (this::productMinusOnCartListener.isInitialized) {
                holder.itemView.setOnClickListener { btnIncreaseproduct ->
                    productMinusOnCartListener(it[position], position)
                }
            }
        }
    }

    override fun getItemCount() = cartItems.size

    lateinit var productAddOnCartListener: (Cart, Int) -> Unit
    lateinit var productMinusOnCartListener: (Cart, Int) -> Unit


    fun setOnIncrementProductListener(listener: (Cart, Int) -> Unit){
        productAddOnCartListener = listener
    }

    fun setOndecrementProductListener(listener: (Cart, Int) -> Unit){
        productMinusOnCartListener = listener
    }
}