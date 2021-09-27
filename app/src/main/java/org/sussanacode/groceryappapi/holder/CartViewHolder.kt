package org.sussanacode.groceryappapi.holder

import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import org.sussanacode.groceryappapi.R
import org.sussanacode.groceryappapi.databinding.HolderCartBinding
import org.sussanacode.groceryappapi.model.Cart
import org.sussanacode.groceryappapi.sql.CartDAO


class CartViewHolder (val binding: HolderCartBinding) : RecyclerView.ViewHolder(binding.root)
{


    fun bind(cart: Cart, imageLoader: ImageLoader){
        binding.tvProdname.text = cart.productname
        binding.qtyvalue.text = cart.quantity.toString()
        val subtotal = cart.productprice * cart.quantity
        binding.tvprice.text = "$${subtotal.toString()}"


        binding.btnincrementItem.setOnClickListener{
            cart.quantity += 1
            val productsubtotal = cart.quantity * cart.productprice

            binding.qtyvalue.text = cart.quantity.toString()
            binding.tvprice.text = "$${productsubtotal.toString()}"
        }

        binding.btndecrementItem.setOnClickListener{
            cart.quantity -= 1
            val productsubtotal = cart.quantity * cart.productprice
            binding.qtyvalue.text = cart.quantity.toString()
            binding.tvprice.text = "$${productsubtotal.toString()}"
        }

        imageLoader.get(cart.productImage, ImageLoader.getImageListener(binding.ivprodImg, R.drawable.ic_default_image, R.drawable.ic_error))
        binding.ivprodImg.setImageUrl(cart.productImage, imageLoader)

    }



//    fun updateDetails(cartItems: ArrayList<Cart>, cart: Cart)
//    {
//        cart.subtotal = cart.productprice * cart.quantity
//        binding.tvprice.text = "$${cart.subtotal.toString()}"
//        binding.qtyvalue.text = cart.quantity.toString()
//        val newTotal:Double = cartItems.sumOf { it.subtotal }
//        val newQty:Int = cartItems.sumOf {it.quantity}
//        println(newTotal)
//        println(newQty)
//
//    }

}