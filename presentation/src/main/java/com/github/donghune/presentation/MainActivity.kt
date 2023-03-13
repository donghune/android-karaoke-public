package com.github.donghune.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.donghune.R
import com.github.donghune.databinding.ActivityMainBinding
import com.github.donghune.presentation.playlist.PlayListFragment
import com.github.donghune.presentation.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.navigationBarColor = getColor(R.color.md_theme_light_surfaceTint)
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.page_home -> {
                    changeFragment(SearchFragment.newInstance())
                    true
                }
                R.id.page_playlist -> {
                    changeFragment(PlayListFragment.newInstance())
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
            .replace(binding.fragmentContainerViewTag.id, fragment)
            .commit()
    }
}
