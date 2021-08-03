package com.angelorobson.product.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.angelorobson.product.databinding.ProductItemBinding
import com.angelorobson.product.presentation.model.ProductPresentation

class ProductsAdapter(private val onclick: (product: ProductPresentation) -> Unit) :
    ListAdapter<ProductPresentation, ProductsAdapter.ProductsViewHolder>(
        DIFF_CALLBACK
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsViewHolder {
        return ProductsViewHolder.create(parent, onclick)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductPresentation>() {
            override fun areItemsTheSame(
                oldItem: ProductPresentation,
                newItem: ProductPresentation
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ProductPresentation,
                newItem: ProductPresentation
            ): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

    class ProductsViewHolder(
        private val itemBinding: ProductItemBinding,
        private val onclick: (product: ProductPresentation) -> Unit
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(product: ProductPresentation) {
            itemBinding.run {
                item = product
                itemView.setOnClickListener {
                    onclick.invoke(product)
                }
            }
        }

        companion object {
            fun create(
                parent: ViewGroup,
                onclick: (product: ProductPresentation) -> Unit
            ): ProductsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ProductItemBinding.inflate(layoutInflater, parent, false)
                return ProductsViewHolder(binding, onclick)
            }
        }
    }
}