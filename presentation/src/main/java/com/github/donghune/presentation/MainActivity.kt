package com.github.donghune.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.donghune.presentation.base.BaseActivity
import com.github.donghune.presentation.databinding.ActivityMainBinding
import com.github.donghune.presentation.latest.LatestFragment
import com.github.donghune.presentation.playlist.PlayListFragment
import com.github.donghune.presentation.popularity.PopularityFragment
import com.github.donghune.presentation.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.page_latest -> {
                    changeFragment(LatestFragment.newInstance())
                    true
                }
                R.id.page_playlist -> {
                    changeFragment(PlayListFragment.newInstance())
                    true
                }
                R.id.page_popularity -> {
                    changeFragment(PopularityFragment.newInstance())
                    true
                }
                R.id.page_search -> {
                    changeFragment(SearchFragment.newInstance())
                    true
                }
                else -> false
            }
        }
        changeFragment(SearchFragment.newInstance())
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view_tag, fragment)
            .commit()
    }
}
