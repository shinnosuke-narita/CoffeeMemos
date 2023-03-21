package com.withapp.coffeememo.privacy_policy.presentation.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import com.withapp.coffeememo.R
import com.withapp.coffeememo.databinding.FragmentPrivacyPolicyBinding

class PrivacyPolicyFragment : Fragment() {
    // viewBinding
    private  var _binding: FragmentPrivacyPolicyBinding? = null
    private val binding
        get() = _binding!!

    private val privacyPolicyUrl: String =
        "https://with-app.herokuapp.com/"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPrivacyPolicyBinding
            .inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        // header セッティング
        binding.header.headerTitle.text =
            getString(R.string.privacy_policy_header)
        binding.header.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        // webView セッティング
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                if (request == null) return true

                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            request.url
                        )
                    )
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                    return true
                }

                return true
            }
        }
        binding.webView.loadUrl(privacyPolicyUrl)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}