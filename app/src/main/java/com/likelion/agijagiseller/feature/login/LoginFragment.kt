package com.likelion.agijagiseller.feature.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.likelion.agijagiseller.R
import com.likelion.agijagiseller.databinding.FragmentLoginBinding
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        initNaverLoginButton()
        initKakaoLoginButton()
    }

    private fun initNaverLoginButton() {

        NaverIdLoginSDK.initialize(
            requireContext(),
            getString(R.string.naver_client_id),
            getString(R.string.naver_client_secret),
            getString(R.string.naver_client_name)
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

    private fun initKakaoLoginButton() {
        binding.buttonLoginKakao.setOnClickListener {
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e("카카오 로그인", "카카오계정으로 로그인 실패", error)
                } else if (token != null) {
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    Log.d("카카오 로그인", "카카오계정으로 로그인 성공 ${token.accessToken}")
                }
            }

            if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
                UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
                    if (error != null) {
                        Log.e("카카오톡 로그인", "카카오톡으로 로그인 실패", error)

                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
                    } else if (token != null) {
                        Log.d("카카오톡 로그인", "카카오톡으로 로그인 성공 ${token.accessToken}")
                        UserApiClient.instance.me { user, error ->
                            if (error != null) {
                                Log.e("사용자 정보 요청 실패", "사용자 정보 요청 실패", error)
                            } else if (user != null) {
                                val email = user.kakaoAccount?.email!!
                                val password = user.id.toString()
                                val nickname = user.kakaoAccount?.profile?.nickname.toString()
                                signUpKakaoAccount(email, password, nickname)
                            }
                        }
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
            }
        }
    }

    private fun signUpKakaoAccount(
        email: String,
        password: String,
        nickname: String,
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    saveKakaoUserInfo(email, nickname)
                    Log.d("회원가입 성공", "카카오 회원가입 성공")
                } else {
                    Log.w("회원가입 실패", "카카오 회원가입 실패", task.exception)
                }
            }
    }

    private fun saveKakaoUserInfo(
        email: String,
        nickname: String,
    ) {
        val currentUser = auth.currentUser
        currentUser?.let { user ->
            val kakaoUser = hashMapOf(
                "email" to email,
                "loginType" to "KAKAO",
                "name" to nickname
            )
            db.collection("seller").document(user.uid).set(kakaoUser).addOnSuccessListener {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}