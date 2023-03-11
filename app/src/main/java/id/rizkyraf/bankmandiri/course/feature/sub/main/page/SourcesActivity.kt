package id.rizkyraf.bankmandiri.course.feature.sub.main.page

import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.rizkyraf.bankmandiri.course.databinding.ActivitySourcesBinding
import id.rizkyraf.bankmandiri.course.feature.sub.main.page.adapter.SourcesAdapter
import id.rizkyraf.bankmandiri.course.feature.sub.main.viewmodel.SourcesViewModel
import id.rizkyraf.bankmandiri.course.service.data.response.SourceEntity
import id.rizkyraf.bankmandiri.course.util.BaseActivity
import id.rizkyraf.bankmandiri.course.util.BaseAdapter
import id.rizkyraf.bankmandiri.course.util.Resource

@AndroidEntryPoint
class SourcesActivity : BaseActivity<ActivitySourcesBinding>(
    ActivitySourcesBinding::inflate
) {

    private val viewModel: SourcesViewModel by viewModels()

    private val sourceText by lazy {
        intent.getStringExtra(SOURCE) ?: ""
    }

    private val mAdapter by lazy {
        SourcesAdapter()
    }

    override fun didMount() {
        initView()
        observeData()
    }

    override fun onClick() {
        mAdapter.setListener(object : BaseAdapter.OnItemClickListener<SourceEntity.Source> {
            override fun onItemClick(item: SourceEntity.Source, position: Int) {
                toDetail(item.id)
            }
        })
    }

    fun toDetail(item: String) {
        val intent = Intent(this, SourceDetailActivity::class.java)
        intent.putExtra(SourceDetailActivity.SOURCE_ID, item)
        startActivity(intent)
    }

    private fun initView() {
        binding.apply {
            tvTitle.text = sourceText

            rvSource.apply {
                adapter = mAdapter
                setHasFixedSize(true)
            }

        }

        requestData()
    }

    private fun requestData() {
        viewModel.getSourceNews(sourceText)
    }

    private fun observeData() {
        viewModel.sourceNews.observe(this) {
            binding.apply {
                when (it) {
                    is Resource.Success -> {
                        it.data?.let { newsResponse ->
                            mAdapter.addItem(newsResponse.sources.toList())
                        }
                    }
                    is Resource.Error -> {
                        it.message?.let { message ->
                            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                    is Resource.Loading -> {

                    }
                }
            }
        }
    }

    companion object {
        const val SOURCE = "source"
    }
}