package id.rizkyraf.bankmandiri.course.feature.sub.main.page

import android.content.Intent
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.rizkyraf.bankmandiri.course.databinding.FragmentNewsBinding
import id.rizkyraf.bankmandiri.course.feature.sub.main.page.adapter.ArticlesAdapter
import id.rizkyraf.bankmandiri.course.feature.sub.main.viewmodel.BreakingNewsViewModel
import id.rizkyraf.bankmandiri.course.service.data.response.NewsEntity
import id.rizkyraf.bankmandiri.course.util.BaseAdapter
import id.rizkyraf.bankmandiri.course.util.BaseFragment
import id.rizkyraf.bankmandiri.course.util.Constant
import id.rizkyraf.bankmandiri.course.util.Constant.QUERY_PAGE_SIZE
import id.rizkyraf.bankmandiri.course.util.Resource

@AndroidEntryPoint
class NewsPage : BaseFragment<FragmentNewsBinding>(
    FragmentNewsBinding::inflate
) {

    private val viewModel: BreakingNewsViewModel by viewModels()

    private val mAdapter by lazy {
        ArticlesAdapter()
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    override fun didMount() {
        initView()
        observeData()
    }

    override fun onClick() {
        mAdapter.setListener(object : BaseAdapter.OnItemClickListener<NewsEntity.Article> {
            override fun onItemClick(item: NewsEntity.Article, position: Int) {
                toDetail(item)
            }
        })
    }

    fun toDetail(item: NewsEntity.Article) {
        val intent = Intent(activity, ArticleActivity::class.java)
        intent.putExtra(ArticleActivity.ARTICLE, item)
        startActivity(intent)
    }

    private fun initView() {
        binding.apply {
            rvBreakingNews.apply {
                adapter = mAdapter
                setHasFixedSize(true)
                addOnScrollListener(this@NewsPage.scrollListener)
            }
        }

        requestData()
    }

    private fun requestData() {
        viewModel.getBreakingNews(countryCode = Constant.COUNTRY)
    }

    private fun observeData() {
        viewModel.breakingNews.observe(viewLifecycleOwner) {
            binding.apply {
                when (it) {
                    is Resource.Success -> {
                        paginationProgressBar.visibility = View.INVISIBLE
                        isLoading = false
                        it.data?.let { newsResponse ->
                            mAdapter.addItem(newsResponse.articles.toList())
                            val totalPages = newsResponse.totalResults / QUERY_PAGE_SIZE + 2
                            isLastPage = viewModel.breakingNewsPage == totalPages
                            if (isLastPage)
                                rvBreakingNews.setPadding(0, 0, 0, 0)
                        }
                    }
                    is Resource.Error -> {
                        paginationProgressBar.visibility = View.INVISIBLE
                        isLoading = true
                        it.message?.let { message ->
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        }
                    }
                    is Resource.Loading -> {
                        paginationProgressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) { //State is scrolling
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val totalVisibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + totalVisibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= 10
            val shouldPaginate =
                isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling

            if (shouldPaginate) {
                viewModel.getBreakingNews(countryCode = Constant.COUNTRY)
                isScrolling = false
            }
        }
    }
}