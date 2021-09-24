package org.sussanacode.groceryappapi.holder

import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import org.sussanacode.groceryappapi.R
import org.sussanacode.groceryappapi.databinding.HolderAddressBinding
import org.sussanacode.groceryappapi.databinding.HolderCartBinding
import org.sussanacode.groceryappapi.model.Cart
import org.sussanacode.groceryappapi.model.ShippingAddress

class AddressHolder (val binding: HolderAddressBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(address: ShippingAddress){

        binding.tvusername.text = address.name
        binding.tvphone.text = "Phone Number: ${address.mobile}"
        binding.tvaddress.text = address.address
        binding.tvcitystate.text = "${address.city}, ${address.state}"
        binding.tvzip.text = address.zip


    }
}