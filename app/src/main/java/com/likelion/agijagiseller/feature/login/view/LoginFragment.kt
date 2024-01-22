package com.likelion.agijagiseller.feature.login.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.likelion.agijagiseller.BuildConfig
import com.likelion.agijagiseller.R
import com.likelion.agijagiseller.databinding.FragmentLoginBinding
import com.likelion.agijagiseller.feature.login.model.LoginType
import com.likelion.agijagiseller.feature.login.model.User
import com.likelion.agijagiseller.feature.login.viewmodel.UserLocalViewModel
import com.likelion.agijagiseller.feature.login.viewmodel.UserRemoteViewModel
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val userRemoteViewModel: UserRemoteViewModel by viewModels()
    private val userLocalViewModel: UserLocalViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNaverLoginButton()
        initKakaoLoginButton()
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

                        UserApiClient.instance.loginWithKakaoAccount(
                            requireContext(),
                            callback = callback
                        )
                    } else if (token != null) {
                        Log.d("카카오톡 로그인", "카카오톡으로 로그인 성공 ${token.accessToken}")
                        UserApiClient.instance.me { user, error ->
                            if (error != null) {
                                Log.e("사용자 정보 요청 실패", "사용자 정보 요청 실패", error)
                            } else if (user != null) {
                                val email = user.kakaoAccount?.email!!
                                val password = user.id.toString()
                                val nickname = user.kakaoAccount?.profile?.nickname.toString()
                                Snackbar.make(requireView(), "로그인 중 입니다...", Snackbar.LENGTH_SHORT)
                                    .show()
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
        userRemoteViewModel.signUp(email, password)
        userRemoteViewModel.signUpStatus.observe(viewLifecycleOwner) { response ->
            if (response == true) {
                saveKakaoUserInfo(email, nickname)
                Log.d("회원가입 성공", "카카오 회원가입 성공")
            } else {
                userRemoteViewModel.getCurrentUser()
                userRemoteViewModel.currentUser.observe(viewLifecycleOwner) { user ->
                    getUserInfo(user.uid)
                    Log.d("이미 존재하는 유저", "로그인 완료")
                }
            }
        }
    }

    private fun saveKakaoUserInfo(
        email: String,
        nickname: String,
    ) {
        userRemoteViewModel.getCurrentUser()
        userRemoteViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            val kakaoUser = User(
                email,
                LoginType.KAKAO.toString(),
                nickname,
            )
            val uid = user.uid
            userRemoteViewModel.saveUser(uid, kakaoUser)
            userRemoteViewModel.userSaved.observe(viewLifecycleOwner) { response ->
                if (response == true) {
                    getUserInfo(uid)
                } else {
                    Snackbar.make(requireView(), "카카오 로그인 실패", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getUserInfo(
        uid: String,
    ) {
        userRemoteViewModel.getUser(uid)
        userRemoteViewModel.userGetStatus.observe(viewLifecycleOwner) { user ->
            userLocalViewModel.apply {
                updateUid(uid)
                updateEmail(user?.email!!)
                updateLoginType(user.loginType!!)
                updateName(user.name!!)
            }
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
