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

class SalesLyonFragment : BaseFragment(){

    companion object {
        const val TAG = "SALESLYONFRAGMENT"
        fun newInstance(): SalesLyonFragment = SalesLyonFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sales, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        wv_sales_fragment.loadUrl("https://asudl.universite-lyon.fr/boutique-66434.kjsp?RH=1749694146834577&RF=1543530668610")

    }
}
