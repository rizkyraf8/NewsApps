package id.rizkyraf.bankmandiri.course.feature.sub.main.page.adapter

import com.bumptech.glide.Glide
import id.rizkyraf.bankmandiri.course.databinding.ItemSourcesBinding
import id.rizkyraf.bankmandiri.course.service.data.response.SourceEntity
import id.rizkyraf.bankmandiri.course.util.BaseAdapter

class SourcesAdapter : BaseAdapter<SourceEntity.Source, ItemSourcesBinding>(
    ItemSourcesBinding::inflate
) {

    override fun onBindViewHolder(holder: ViewHolder<ItemSourcesBinding>, position: Int) {
        val item = list[position]
        val binding = holder.binding

        binding.apply {
            tvDescription.text = item.description
            tvTitle.text = item.name
            tvCategory.text = item.category
        }

        holder.itemView.setOnClickListener {
            onClickListener?.onItemClick(item, position)
        }
    }
}