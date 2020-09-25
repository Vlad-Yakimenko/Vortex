package com.e.vortex.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.e.vortex.App
import com.e.vortex.R
import com.e.vortex.ui.main.LoadingFragment
import com.e.vortex.ui.main.ViewModel
import com.e.vortex.ui.main.ViewModelFactory
import com.e.vortex.ui.main.WebViewFragment
import kotlinx.android.synthetic.main.fr_web_view.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        changeFragment(LoadingFragment.newInstance())

        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory((application as App).database.vortexDao())
        ).get(ViewModel::class.java)

        viewModel.isLoaded.observe(this, {
            if (it) {
                Thread.sleep(1000)
                changeFragment(WebViewFragment.newInstance())
            }
        })


    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}