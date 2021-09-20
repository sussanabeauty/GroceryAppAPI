package org.sussanacode.groceryappapi.holder

import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import org.sussanacode.groceryappapi.R
import org.sussanacode.groceryappapi.databinding.HolderCartBinding
import org.sussanacode.groceryappapi.model.Cart


class CartViewHolder (val binding: HolderCartBinding) : RecyclerView.ViewHolder(binding.root)
{
    fun bind(cart: Cart, imageLoader: ImageLoader){
        binding.tvProdname.text = cart.productname
        binding.qtyvalue.text = cart.quantity.toString()
        binding.tvprice.text = "$${cart.productprice.toString()}"

        imageLoader.get(cart.productImage, ImageLoader.getImageListener(binding.ivprodImg, R.drawable.ic_default_image, R.drawable.ic_error))
        binding.ivprodImg.setImageUrl(cart.productImage, imageLoader)

    }

}