package com.likelion.agijagiseller.feature.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.likelion.agijagiseller.R
import com.likelion.agijagiseller.databinding.FragmentProductRegistrationBinding

class ProductUpdateFragment : Fragment() {
    private var _binding: FragmentProductRegistrationBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductRegistrationBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initUpdateButton()
    }

    private fun initUpdateButton() {
        binding.buttonProductAddOk.setOnClickListener {
            // 업데이트 프리뷰로 넘어가야됨
        }
    }

    private fun initToolbar() {
        binding.run {
            toolbarProductregistration.run {
                title = "판매상품 수정"
            }

            toolbarProductregistration.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}