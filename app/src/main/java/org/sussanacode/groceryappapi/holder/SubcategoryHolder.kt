package org.sussanacode.groceryappapi.holder

import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import org.sussanacode.groceryappapi.R
import org.sussanacode.groceryappapi.databinding.HolderSubcategoryBinding
import org.sussanacode.groceryappapi.model.Subcategory


class SubcategoryHolder (val binding: HolderSubcategoryBinding) : RecyclerView.ViewHolder(binding.root) {


    fun bind(subcat: Subcategory, imageLoader: ImageLoader){

        binding.tvsubImgtxt.text = subcat.subName

        imageLoader.get(subcat.subImage, ImageLoader.getImageListener(binding.ivsubcatImg, R.drawable.ic_default_image, R.drawable.ic_error))
        binding.ivsubcatImg.setImageUrl(subcat.subImage, imageLoader)

    }
}