package br.igor.eletriccarapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import br.igor.eletriccarapp.R
import br.igor.eletriccarapp.presentation.adapter.TabAdapter
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    lateinit var tab_carros: TabLayout
    lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        load()
        setupTab()
    }

    private fun load() {
        tab_carros = findViewById(R.id.tab_carros)
        viewPager = findViewById(R.id.vp_view_pager)
    }

    private fun setupTab() {
        val tabsAdapter = TabAdapter(this)
        viewPager.adapter = tabsAdapter
        tab_carros.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    viewPager.currentItem = it.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tab_carros.getTabAt(position)?.select()
            }
        })

    }
}