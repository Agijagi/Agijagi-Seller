package com.likelion.agijagiseller.feature.storemanagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.likelion.agijagiseller.R
import com.likelion.agijagiseller.databinding.FragmentStoreManagementBinding


class StoreManagementFragment : Fragment() {

    private var _binding: FragmentStoreManagementBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStoreManagementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initStoreManagementEditButton()
        binding.toolbarStoreManagement.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initStoreManagementEditButton() {
        binding.buttonStoreManagementEdit.setOnClickListener {
            findNavController().navigate(R.id.action_storeManagementFragment_to_homeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}