package org.sussanacode.groceryappapi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sussanacode.groceryappapi.databinding.HolderTrackOrderBinding
import org.sussanacode.groceryappapi.holder.OrderHolder
import org.sussanacode.groceryappapi.model.Order


class OrderAdapter (val orders: ArrayList<Order> ): RecyclerView.Adapter<OrderHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HolderTrackOrderBinding.inflate(layoutInflater, parent, false)
        return OrderHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderHolder, position: Int) {
        holder.bind(orders[position])
    }

    override fun getItemCount() = orders.size
}