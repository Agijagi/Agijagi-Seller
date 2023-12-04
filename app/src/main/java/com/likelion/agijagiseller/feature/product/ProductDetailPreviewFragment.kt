package com.likelion.agijagiseller.feature.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.likelion.agijagiseller.R
import com.likelion.agijagiseller.databinding.FragmentProductDetailBinding

class ProductDetailPreviewFragment : Fragment() {
    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initCompleteButton()
    }

    private fun initToolbar() {
        binding.toolbarProductdetail.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initCompleteButton() {
        binding.buttonProductdetailComplete.setOnClickListener {
            findNavController().navigate(R.id.action_productDetailPreviewFragment_to_productListFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}