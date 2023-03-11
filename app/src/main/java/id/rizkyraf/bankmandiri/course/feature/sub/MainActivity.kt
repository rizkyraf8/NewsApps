package id.rizkyraf.bankmandiri.course.feature.sub

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.core.view.MenuItemCompat
import dagger.hilt.android.AndroidEntryPoint
import id.rizkyraf.bankmandiri.course.R
import id.rizkyraf.bankmandiri.course.databinding.ActivityMainBinding
import id.rizkyraf.bankmandiri.course.feature.sub.main.page.CategoryPage
import id.rizkyraf.bankmandiri.course.feature.sub.main.page.NewsPage
import id.rizkyraf.bankmandiri.course.feature.sub.main.page.SearchResultActivity
import id.rizkyraf.bankmandiri.course.util.BaseActivity

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {

    private val fragment = listOf(
        NewsPage(),
        CategoryPage()
    )

    override fun didMount() {
        displayFragment(0)
    }

    override fun onClick() {
        binding.apply {
            bottomNavigationView.apply {
                itemIconTintList = null
                setOnItemSelectedListener {
                    displayFragment(it.itemId)

                    return@setOnItemSelectedListener true
                }
            }
        }
    }

    private fun displayFragment(itemId: Int) {
        val position = when (itemId) {
            R.id.categoryFragment -> 1
            else -> 0
        }
        supportFragmentManager.beginTransaction().apply {
            if (fragment[position].isAdded) {
                show(fragment[position])
            } else {
                add(R.id.frameFragment, fragment[position], fragment[position].tag)
            }

            supportFragmentManager.fragments.forEach {
                if (it != fragment[position] && it.isAdded) {
                    hide(it)
                }
            }
        }.commit()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        val searchViewItem: MenuItem = menu.findItem(R.id.menu_search)
        val searchView = MenuItemCompat.getActionView(searchViewItem) as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                handleIntent(query)
                searchView.setQuery("", false)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun handleIntent(query: String) {
        val intentSearch = Intent(this, SearchResultActivity::class.java)
        intentSearch.putExtra(SearchResultActivity.SEARCH_KEY, query)
        startActivity(intentSearch)
    }
}