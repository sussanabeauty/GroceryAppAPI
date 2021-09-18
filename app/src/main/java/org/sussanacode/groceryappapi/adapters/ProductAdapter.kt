package org.sussanacode.groceryappapi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import org.sussanacode.groceryappapi.databinding.HolderProductBinding
import org.sussanacode.groceryappapi.databinding.HolderSubcategoryBinding
import org.sussanacode.groceryappapi.holder.ProductHolder
import org.sussanacode.groceryappapi.holder.SubcategoryHolder
import org.sussanacode.groceryappapi.model.Product
import org.sussanacode.groceryappapi.model.Subcategory

class ProductAdapter (val productList: ArrayList<Product>, val imageLoader: ImageLoader) : RecyclerView.Adapter<ProductHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HolderProductBinding.inflate(layoutInflater, parent, false)
        return ProductHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        holder.bind(productList[position], imageLoader)

        if(this::productClickListener.isInitialized) {
            holder.itemView.setOnClickListener {
                productClickListener(productList[position], position)
            }
        }
    }

    override fun getItemCount() = productList.size

    lateinit var productClickListener: (Product, Int) -> Unit

    fun setOnProductClickListener(listener: (Product, Int) -> Unit) {
        productClickListener = listener
    }
}