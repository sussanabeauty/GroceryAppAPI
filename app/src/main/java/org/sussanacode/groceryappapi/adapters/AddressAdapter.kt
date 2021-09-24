package org.sussanacode.groceryappapi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import org.sussanacode.groceryappapi.databinding.HolderAddressBinding
import org.sussanacode.groceryappapi.databinding.HolderCartBinding
import org.sussanacode.groceryappapi.holder.AddressHolder
import org.sussanacode.groceryappapi.holder.CartViewHolder
import org.sussanacode.groceryappapi.model.Cart
import org.sussanacode.groceryappapi.model.ShippingAddress

class AddressAdapter (val addresses: ArrayList<ShippingAddress>?) : RecyclerView.Adapter<AddressHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HolderAddressBinding.inflate(layoutInflater, parent, false)
        return AddressHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressHolder, position: Int) {
        addresses?.let {
            holder.bind(it[position])


            if (this::editaddressListener.isInitialized) {
                holder.binding.btnedit.setOnClickListener { btneditaddress ->
                    editaddressListener(it[position], position)
                }
            }

            if (this::deleteaddressListener.isInitialized) {
                holder.binding.btndelete.setOnClickListener { btndeleteaddress ->
                    deleteaddressListener(it[position], position)
                }
            }
        }
    }

    override fun getItemCount() = addresses?.size?: 0


    lateinit var editaddressListener: (ShippingAddress, Int) -> Unit
    lateinit var deleteaddressListener : (ShippingAddress, Int) -> Unit


    fun setOndeleteAddressListener(listener: (ShippingAddress, Int) -> Unit){
        deleteaddressListener = listener
    }

    fun setOnEditAddressListener(listener: (ShippingAddress, Int) -> Unit){
        editaddressListener = listener
    }
}