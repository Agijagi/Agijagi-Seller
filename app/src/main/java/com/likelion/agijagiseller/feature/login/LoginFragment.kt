package com.likelion.agijagiseller.feature.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.likelion.agijagiseller.BuildConfig
import com.likelion.agijagiseller.R
import com.likelion.agijagiseller.databinding.FragmentLoginBinding
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNaverLoginButton()
    }

    private fun initNaverLoginButton() {

        NaverIdLoginSDK.initialize(
            requireContext(),
            BuildConfig.NAVER_ID,
            BuildConfig.NAVER_SECRET,
            BuildConfig.NAVER_NAME
        )

        binding.run {
            buttonLoginNaver.setOnClickListener {
                val oAuthLoginCallback = object : OAuthLoginCallback {
                    override fun onSuccess() {
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }

                    override fun onFailure(httpStatus: Int, message: String) {
                        val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                        val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                        Toast.makeText(
                            context,
                            "errorCode: $errorCode, errorDescription: $errorDescription",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onError(errorCode: Int, message: String) {
                        onFailure(errorCode, message)
                    }
                }
                NaverIdLoginSDK.authenticate(requireContext(), oAuthLoginCallback)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}