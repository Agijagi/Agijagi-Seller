package com.likelion.agijagiseller.feature.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.likelion.agijagiseller.R
import com.likelion.agijagiseller.databinding.FragmentProductDetailBinding

class ProductDetailFragment : Fragment() {
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

        initButton()
        initToolbar()
    }

    private fun initButton() {
        binding.buttonProductdetailComplete.visibility = View.GONE
    }

    private fun initToolbar() {
        binding.run {
            toolbarProductdetail.inflateMenu(R.menu.menu_product_detail)
            toolbarProductdetail.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_product_detail_edit -> {
                        findNavController().navigate(R.id.action_productDetailFragment_to_productUpdateFragment)
                    }

                    R.id.menu_product_detail_delete -> {
                        // Todo 삭제하는 다이얼로그
                    }
                }
                false
            }

            toolbarProductdetail.setOnMenuItemClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
