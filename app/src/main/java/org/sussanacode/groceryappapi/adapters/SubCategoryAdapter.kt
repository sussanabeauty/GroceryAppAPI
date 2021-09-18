package org.sussanacode.groceryappapi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import org.sussanacode.groceryappapi.databinding.HolderCategoryBinding
import org.sussanacode.groceryappapi.databinding.HolderSubcategoryBinding
import org.sussanacode.groceryappapi.holder.CategoryHolder
import org.sussanacode.groceryappapi.holder.SubcategoryHolder
import org.sussanacode.groceryappapi.model.Category
import org.sussanacode.groceryappapi.model.Subcategory

class SubcategoryAdapter (val subcatList: ArrayList<Subcategory>, val imageLoader: ImageLoader) :
    RecyclerView.Adapter<SubcategoryHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubcategoryHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HolderSubcategoryBinding.inflate(layoutInflater, parent, false)
        return SubcategoryHolder(binding)
    }

    override fun onBindViewHolder(holder: SubcategoryHolder, position: Int) {
        holder.bind(subcatList[position], imageLoader)

        if(this::subcategoryClickListener.isInitialized) {
            holder.itemView.setOnClickListener {
                subcategoryClickListener(subcatList[position], position)
            }
        }
    }

    override fun getItemCount() = subcatList.size


    lateinit var subcategoryClickListener: (Subcategory, Int) -> Unit

    fun setOnSubcategoryClickListener(listener: (Subcategory, Int) -> Unit) {
        subcategoryClickListener = listener
    }


}