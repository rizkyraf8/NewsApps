package id.rizkyraf.bankmandiri.course.feature.sub.main.page

import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import id.rizkyraf.bankmandiri.course.databinding.FragmentCategoryBinding
import id.rizkyraf.bankmandiri.course.feature.sub.main.page.adapter.CategoryAdapter
import id.rizkyraf.bankmandiri.course.util.BaseAdapter
import id.rizkyraf.bankmandiri.course.util.BaseFragment

@AndroidEntryPoint
class CategoryPage : BaseFragment<FragmentCategoryBinding>(
    FragmentCategoryBinding::inflate
) {

    private val mAdapter by lazy {
        CategoryAdapter()
    }

    override fun didMount() {
        initView()
    }

    override fun onClick() {
        mAdapter.setListener(object : BaseAdapter.OnItemClickListener<String> {
            override fun onItemClick(item: String, position: Int) {
                toSourceList(item)
            }
        })
    }

    private fun toSourceList(item: String) {
        val intent = Intent(activity, SourcesActivity::class.java)
        intent.putExtra(SourcesActivity.SOURCE, item)
        startActivity(intent)
    }

    private fun initView() {
        binding.apply {
            rvCategoryNews.apply {
                adapter = mAdapter
                setHasFixedSize(true)
            }
        }

        setData()
    }

    private fun setData() {
        mAdapter.addItem(
            listOf(
                "business", "entertainment", "general", "health", "science", "sports", "technology"
            )
        )
    }
}