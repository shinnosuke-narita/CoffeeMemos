package com.withapp.coffeememo.menu.presentation.view

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.withapp.coffeememo.R
import com.withapp.coffeememo.databinding.FragmentMenuBinding
import com.withapp.coffeememo.menu.presentation.adapter.MenuAdapter

class MenuFragment : Fragment() {
    // viewBinding
    private  var _binding: FragmentMenuBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: MenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val titleList: Array<out String> =
            requireContext()
                .resources
                .getStringArray(R.array.title_list)

        val descList: Array<out String> =
            requireContext()
                .resources
                .getStringArray(R.array.desc_list)

        // ビルド番号取得
        val versionName: String =
            getVersionName(requireActivity()) ?: ""
        // viewModel 初期化
        viewModel.initialize(titleList, descList, versionName)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMenuBinding
            .inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setUpView(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpView(context: Context) {
        setUpAdapter(context)
        setUpHeader()
    }

    private fun setUpAdapter(context: Context) {
        binding.menuList.layoutManager =
            LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
        }

        binding.menuList.adapter =
            MenuAdapter(
                viewModel.menuListItem
            ) { position ->
                when(position) {
                    0 -> {
                        // ライセンス
                        startActivity(
                            Intent(
                                requireContext(),
                                OssLicensesMenuActivity::class.java
                            )
                        )
                    }
                    1 -> {
                        // 著作権
                        findNavController()
                            .navigate(R.id.copyRightFragment)

                    }
                    2 -> {
                        // プライバシーポリシー
                        findNavController()
                            .navigate(R.id.privacyPolicyFragment)
                    }
                    else -> {} // 何もしない
                }
            }
    }

    private fun setUpHeader() {
        // header セッティング
        binding.header.headerTitle.text =
            getString(R.string.menu_header_title)
        binding.header.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getVersionName(activity: FragmentActivity): String? {
        try {
            val packageName: String = activity.packageName
            val info: PackageInfo =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    activity.packageManager.getPackageInfo(
                        packageName,
                        PackageManager.PackageInfoFlags.of(0)
                    )
                } else {
                    @Suppress("DEPRECATION")
                    activity.packageManager.getPackageInfo(packageName, 0)
                }

            return info.versionName
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }
}