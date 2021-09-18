package org.sussanacode.groceryappapi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import org.sussanacode.groceryappapi.databinding.HolderCategoryBinding
import org.sussanacode.groceryappapi.holder.CategoryHolder
import org.sussanacode.groceryappapi.model.Category

class CategoryAdapter(val categories: ArrayList<Category>, val imageLoader: ImageLoader) :RecyclerView.Adapter<CategoryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HolderCategoryBinding.inflate(layoutInflater, parent, false)
        return CategoryHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
       holder.bind(categories[position], imageLoader)

        if(this::categoryClickListener.isInitialized) {
            holder.itemView.setOnClickListener {
                categoryClickListener(categories[position], position)
            }
        }
    }

    override fun getItemCount() = categories.size


    lateinit var categoryClickListener: (Category, Int) -> Unit


    fun setOnCategoryClickListener(listener: (Category, Int) -> Unit) {
        categoryClickListener = listener
    }


}