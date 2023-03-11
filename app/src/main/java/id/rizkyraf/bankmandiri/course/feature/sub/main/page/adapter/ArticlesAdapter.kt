package id.rizkyraf.bankmandiri.course.feature.sub.main.page.adapter

import com.bumptech.glide.Glide
import id.rizkyraf.bankmandiri.course.databinding.ItemArticleBinding
import id.rizkyraf.bankmandiri.course.service.data.response.NewsEntity
import id.rizkyraf.bankmandiri.course.util.BaseAdapter

class ArticlesAdapter : BaseAdapter<NewsEntity.Article, ItemArticleBinding>(
    ItemArticleBinding::inflate
) {

    override fun onBindViewHolder(holder: ViewHolder<ItemArticleBinding>, position: Int) {
        val item = list[position]
        val binding = holder.binding

        binding.apply {
            Glide.with(ivArticleImage)
                .load(item.urlToImage)
                .into(ivArticleImage)
            tvDescription.text = item.description
            tvTitle.text = item.title
            tvPublishedAt.text = item.publishedAt
            tvSource.text = item.source?.name
        }

        holder.itemView.setOnClickListener {
            onClickListener?.onItemClick(item, position)
        }
    }
}