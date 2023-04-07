package com.example.storeapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.storeapp.data.model.Product
import com.example.storeapp.databinding.ItemProductBinding
import com.squareup.picasso.Picasso

class ProductsAdapter (private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var products = mutableListOf<Product>()

    fun addProductsToList(products: List<Product>) {
        this.products.addAll(products)
        notifyDataSetChanged()
    }

    fun setProduct(products: List<Product>){
        this.products = products.toMutableList()
        notifyDataSetChanged()
    }

    fun clearProducts(){
        products.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ProductsViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context),parent,false),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProductsViewHolder -> {
                holder.bind(products[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    class ProductsViewHolder(private val binding: ItemProductBinding, private val interaction: Interaction?) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: Product) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            Picasso.get().load(item.image)
                .into(binding.productImage)
            binding.productTitle.text = item.title
            binding.productPrice.text = "${item.price}$"
            binding.productDescription.text = item.description
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Product)
    }
}
