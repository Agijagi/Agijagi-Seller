package com.likelion.agijagiseller.feature.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.likelion.agijagiseller.R
import com.likelion.agijagiseller.databinding.FragmentProductListBinding

class ProductListFragment : Fragment() {
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
    }

    private fun initToolbar() {
        binding.run {
            toolbarProductList.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            toolbarProductList.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_product_list_add -> findNavController().navigate(R.id.action_productListFragment_to_productRegistrationFragment)
                }
                false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}