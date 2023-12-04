package com.likelion.agijagiseller.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.likelion.agijagiseller.R
import com.likelion.agijagiseller.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMenuNavigate()
        initToolbar()
    }

    private fun initToolbar() {
        binding.toolbarHome.setOnMenuItemClickListener { menu ->
            when (menu.itemId) {
                R.id.menu_seller_my_page_chat -> {
                    findNavController().navigate(R.id.action_homeFragment_to_chattingFragment)
                }

                R.id.menu_seller_my_page_notification -> {
                    findNavController().navigate(R.id.action_homeFragment_to_notificationFragment)
                }
            }
            false
        }
    }

    private fun initMenuNavigate() {
        binding.run {
            textviewHomeManagementMyStore.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_storeManagementFragment)
            }

            textviewHomeProductAdd.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_productRegistrationFragment)
            }

            textviewHomeProductList.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_productListFragment)
            }

            textviewHomeManagementOrderShip.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_orderShippingManagementFragment)
            }

            textviewHomeSettingNotification.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_notificationSettingFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}