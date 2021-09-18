package org.sussanacode.groceryappapi.holder

import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import org.sussanacode.groceryappapi.R
import org.sussanacode.groceryappapi.databinding.HolderCategoryBinding
import org.sussanacode.groceryappapi.model.Category

class CategoryHolder(val binding: HolderCategoryBinding) : RecyclerView.ViewHolder(binding.root)
{
    fun bind(category: Category, imageLoader: ImageLoader){
        binding.tvImgtxt.text = category.catName

        imageLoader.get(category.catImage, ImageLoader.getImageListener(binding.ivcatImg, R.drawable.ic_default_image, R.drawable.ic_error))
        binding.ivcatImg.setImageUrl(category.catImage, imageLoader)
    }
}