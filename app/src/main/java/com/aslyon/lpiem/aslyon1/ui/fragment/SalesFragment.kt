package com.aslyon.lpiem.aslyon1.ui.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.aslyon.lpiem.aslyon1.R

import kotlinx.android.synthetic.main.fragment_sales.*
import android.view.MotionEvent



class SalesFragment : BaseFragment(){

    companion object {
        const val TAG = "SALESFRAGMENT"
        fun newInstance(): SalesFragment = SalesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sales, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        wv_sales_fragment.webViewClient=MyWebViewClient()
        wv_sales_fragment.loadUrl("https://as.univ-lyon1.fr/boutique/")


    }

    class MyWebViewClient : WebViewClient(){

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            view?.loadUrl("https://as.univ-lyon1.fr/boutique/")
            return true
        }



    }
}