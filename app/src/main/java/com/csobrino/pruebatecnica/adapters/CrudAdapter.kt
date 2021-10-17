package com.csobrino.pruebatecnica.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.csobrino.pruebatecnica.data.Product
import com.csobrino.pruebatecnica.databinding.ItemCrudBinding

class CrudAdapter(private val onClickPlanet: OnClickPlanet) :
    ListAdapter<Product, CrudAdapter.ViewHolder>(ListAdapterCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, onClickPlanet)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ItemCrudBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product, onClickPlanet: OnClickPlanet) {
            binding.planet = item
            binding.clickInterface = onClickPlanet
            binding.adapterPos = adapterPosition

            Glide.with(binding.image.context).load(item.hdurl).into(binding.image)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCrudBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class ListAdapterCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem == newItem
        }
    }

    interface OnClickPlanet {
        fun zoomImage(url: String)
        fun item(product: Product)
    }
}