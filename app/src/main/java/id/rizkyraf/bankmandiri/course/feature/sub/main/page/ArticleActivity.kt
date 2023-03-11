package id.rizkyraf.bankmandiri.course.feature.sub.main.page

import android.webkit.WebViewClient
import dagger.hilt.android.AndroidEntryPoint
import id.rizkyraf.bankmandiri.course.databinding.ActivityArticleBinding
import id.rizkyraf.bankmandiri.course.service.data.response.NewsEntity
import id.rizkyraf.bankmandiri.course.util.BaseActivity

@AndroidEntryPoint
class ArticleActivity : BaseActivity<ActivityArticleBinding>(
    ActivityArticleBinding::inflate
) {

    override fun didMount() {
        initView()
    }

    override fun onClick() {

    }

    private fun initView() {
        val article = intent.getParcelableExtra<NewsEntity.Article>(ARTICLE)
        article?.let {
            binding.apply {
                webView.apply {
                    webViewClient = WebViewClient()
                    article.url?.let { it1 -> loadUrl(it1) }
                }

            }
        }
    }

    companion object {
        const val ARTICLE = "article"
    }
}