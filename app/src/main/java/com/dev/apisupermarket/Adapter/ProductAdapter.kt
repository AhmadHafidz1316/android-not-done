package com.dev.apisupermarket.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.apisupermarket.R
import com.dev.apisupermarket.Request.ProductRequest

class ProductAdapter(private val productList: List<ProductRequest>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val namaBarangTextView: TextView = itemView.findViewById(R.id.namaBarang)
        private val hargaBarangTextView: TextView = itemView.findViewById(R.id.hargaBarang)
        private val imageBarang : ImageView = itemView.findViewById(R.id.image)

        fun bind(product: ProductRequest) {
            namaBarangTextView.text = product.name
            hargaBarangTextView.text = "Price: $${product.price}"
            Glide.with(itemView)
                .load(product.image)
                .into(imageBarang)
        }
    }

}
