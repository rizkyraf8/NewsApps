package id.rizkyraf.bankmandiri.course.feature.sub.main.page.adapter

import id.rizkyraf.bankmandiri.course.databinding.ItemCategoryBinding
import id.rizkyraf.bankmandiri.course.util.BaseAdapter

class CategoryAdapter : BaseAdapter<String, ItemCategoryBinding>(
    ItemCategoryBinding::inflate
) {

    override fun onBindViewHolder(holder: ViewHolder<ItemCategoryBinding>, position: Int) {
        val item = list[position]
        val binding = holder.binding

        binding.apply {
            tvCategory.text = item
        }

        holder.itemView.setOnClickListener {
            onClickListener?.onItemClick(item, position)
        }
    }
}