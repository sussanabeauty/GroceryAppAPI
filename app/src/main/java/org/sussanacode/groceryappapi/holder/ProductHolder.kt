package org.sussanacode.groceryappapi.holder

import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import org.sussanacode.groceryappapi.R
import org.sussanacode.groceryappapi.databinding.HolderProductBinding
import org.sussanacode.groceryappapi.databinding.HolderSubcategoryBinding
import org.sussanacode.groceryappapi.model.Product
import org.sussanacode.groceryappapi.model.Subcategory

class ProductHolder (val binding: HolderProductBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(product: Product, imageLoader: ImageLoader){

        binding.tvproductImgtxt.text = product.productName
        binding.tvpricetxt.text = "$${product.price.toString()}"

        imageLoader.get(product.productimage, ImageLoader.getImageListener(binding.ivproductImg, R.drawable.ic_default_image, R.drawable.ic_error))
        binding.ivproductImg.setImageUrl(product.productimage, imageLoader)

    }
}