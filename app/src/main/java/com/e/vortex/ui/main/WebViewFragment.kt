package com.e.vortex.ui.main

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context.MODE_PRIVATE
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.e.vortex.R


class WebViewFragment : Fragment() {

    companion object {
        fun newInstance() = WebViewFragment()
    }

    private lateinit var webView: WebView
    private lateinit var viewModel: ViewModel

    private class MyWebViewClient : WebViewClient() {
        @TargetApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            view.loadUrl(request.url.toString())
            return true
        }

        // For old devices
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val inflate = inflater.inflate(R.layout.fr_web_view, container, false)
        webView = inflate.findViewById(R.id.webView)
        webView.webViewClient = MyWebViewClient()
        webView.settings.javaScriptEnabled = true
        return inflate
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = activity?.let { ViewModelProvider(it).get(ViewModel::class.java) }!!

        viewModel.isErrorVisible.observe(viewLifecycleOwner, {
            if (it) {
                Toast.makeText(
                    activity,
                    "Probably, internet isn't available, turn it on and restart app",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        viewModel.response.observe(viewLifecycleOwner, {
            if (it != null) {
                if (isFirstRun()) {
                    webView.loadUrl(it.link)
                } else {
                    webView.loadUrl(it.home)
                }
            }
        })
    }

    private fun isFirstRun(): Boolean {
        val sharedPreferences = activity!!.getSharedPreferences("com.e.Vortex", MODE_PRIVATE)
        val isFirstRun = sharedPreferences.getBoolean("first_run", true)

        if (isFirstRun) {
            sharedPreferences.edit().putBoolean("first_run", false).apply()
        }

        return isFirstRun
    }
}