package com.likelion.agijagiseller.feature.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.likelion.agijagiseller.R
import com.likelion.agijagiseller.databinding.FragmentProductRegistrationBinding

class ProductRegistrationFragment : Fragment() {

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

        initProductRegisterButton()
        initToolbar()
    }

    private fun initProductRegisterButton() {
        binding.buttonProductAddOk.setOnClickListener {
            findNavController().navigate(R.id.action_productRegistrationFragment_to_productDetailPreviewFragment)
        }
    }

    private fun initToolbar() {
        binding.toolbarProductregistration.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}