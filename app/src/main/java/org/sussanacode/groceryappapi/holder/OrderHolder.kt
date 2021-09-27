package org.sussanacode.groceryappapi.holder

import androidx.recyclerview.widget.RecyclerView
import org.sussanacode.groceryappapi.databinding.HolderTrackOrderBinding
import org.sussanacode.groceryappapi.model.Order

class OrderHolder (val binding: HolderTrackOrderBinding): RecyclerView.ViewHolder(binding.root){


    fun bind(order: Order){
        binding.tvorderid.text = "Order ID: ${order.orderID}"
        binding.tvexpecteddelivery.text = "Delivery Date: ${order.deliverydate.toString()}"
        binding.tvtotalprice.text = "Order Total: $${order.ordertotal.toString()}"

    }
}