package com.cursosant.insurance.loginModule.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.cursosant.insurance.BR
import com.cursosant.insurance.R
import com.cursosant.insurance.common.entities.User
import com.cursosant.insurance.common.entities.UserData
import com.cursosant.insurance.common.utils.NavUtils
import com.cursosant.insurance.common.utils.UiUtils
import com.cursosant.insurance.common.utils.Utils
import com.cursosant.insurance.databinding.FragmentLoginBinding
import com.cursosant.insurance.loginModule.viewModel.LoginViewModel
import com.cursosant.insurance.mainModule.viewModel.MainViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/****
 * Project: Insurance
 * From: com.cursosant.insurance.loginModule
 * Created by Alain Nicolás Tello on 27/05/23 at 10:08
 * All rights reserved 2023.
 *
 * All my Udemy Courses:
 * https://www.udemy.com/user/alain-nicolas-tello/
 * And Frogames formación:
 * https://cursos.frogamesformacion.com/pages/instructor-alain-nicolas
 *
 * Coupons on my Website:
 * www.alainnicolastello.com
 ***/
@AndroidEntryPoint
open class LoginFragment : Fragment() {

    @Inject lateinit var uiUtils: UiUtils
    @Inject lateinit var utils: Utils
    @Inject lateinit var navUtils: NavUtils

    private var gson: Gson? = null
    var gsonBuilder = GsonBuilder()
    var token: String? = ""

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gson = gsonBuilder.create()
        UserData.loadData(requireContext())

        setupViewModel()
        setupButtons()
        setupObservers()
    }

    private fun setupViewModel() {
        val vm: LoginViewModel by viewModels()
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, vm)
    }

    private fun setupObservers() {
        binding.viewModel?.let { vm ->
            vm.initialSetupEvent.observe(viewLifecycleOwner) { event ->
                if (event == null){
                    binding.containerForm.visibility = View.VISIBLE
                } else {
                    login(event.email, event.password)
                }
            }
            vm.inProgress.observe(viewLifecycleOwner) { result ->
                binding.containerForm.visibility = if (result) View.GONE else View.VISIBLE
            }
            vm.snackbarMsg.observe(viewLifecycleOwner) { resMsg ->
                uiUtils.snackbarLong(binding.root, resMsg)
            }
            vm.snackbarWarning.observe(viewLifecycleOwner) { resMsg ->
                uiUtils.snackbarWarning(binding.root, resMsg)
            }
            vm.isLogin.observe(viewLifecycleOwner) { result ->
                if (result) {
                    goToHome()
                    User.instance?.let { vm.setupTopics(it.username) }
                    val mainVM: MainViewModel by activityViewModels()
                    mainVM.loginFinished()
                }
            }
            vm.isHideKeyboard.observe(viewLifecycleOwner) { isHide ->
                if (isHide) uiUtils.hideKeyboard(binding.root)
            }
        }
    }

    // FIXME: if auto-login fails, show retry option or fill form
    private fun login(email: String, password: String) {
        val msg = getString(R.string.login_progress_msg_login)
        binding.viewModel?.login(email, password, msg)
    }

    private fun goToHome() {
        navUtils.run { navController.navigate(actionLoginToHome) }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if (isEnabled && User.instance != null) {
                    isEnabled = false
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                } else requireActivity().finish()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
    
    private fun setupButtons() {
        with(binding){
            btnLogin.setOnClickListener {
                if (validateFields(isLogin = true)) {
                    login(binding.etUsername.text.toString().trim(), binding.etPassword.text.toString().trim())
                }
            }
            btnCreateAccount.setOnClickListener {
                navUtils.navController.navigate(navUtils.actionLoginToRegister)
            }
            btnForgot.setOnClickListener {
                val msg = getString(R.string.login_progress_msg_general)
                if (validateFields()) viewModel?.forgotPassword(etUsername.text.toString().trim(), msg)
            }
            btnSendCode.setOnClickListener {
                val msg = getString(R.string.login_progress_msg_general)
                if (validateFields()) viewModel?.resendActivation(etUsername.text.toString().trim(), msg)
            }
        }
    }

    private fun validateFields(isLogin: Boolean = false): Boolean{
        var isValid = true

        with(binding) {
            if (etUsername.text.toString().isBlank()) {
                tilUsername.error = if (isLogin) getString(R.string.login_empty_username)
                                    else getString(R.string.login_recovery_empty_email)
                isValid = false
            } else if (!utils.validateEmail(etUsername.text.toString().trim())) {
                tilUsername.error = getString(R.string.login_invalid_email)
                isValid = false
            }

            if (isLogin && etPassword.text.toString().isBlank()) {
                tilPassword.error = getString(R.string.login_empty_password)
                isValid = false
            }
        }

        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        _binding = null
    }

    protected fun setImgCover(imgRes: Int) {
        binding.imgCover.setImageResource(imgRes)
    }

    protected fun setImgLogo(imgRes: Int) {
        binding.imgLogo.setImageResource(imgRes)
    }

    protected fun setBackgroundColor(colorRes: Int, onColorRes: Int) {
        with(binding) {
            containerMain.setBackgroundResource(colorRes)
            val onColor = ContextCompat.getColor(requireActivity(), onColorRes)
            btnCreateAccount.setTextColor(onColor)
            btnForgot.setTextColor(onColor)
            btnSendCode.setTextColor(onColor)
        }
    }

    protected fun setHintStrokeColor(colorRes: Int) {
        val color = ContextCompat.getColor(requireActivity(), colorRes)
        val colorState = ContextCompat.getColorStateList(requireActivity(), colorRes)
        with(binding) {
            tilUsername.hintTextColor = colorState
            tilUsername.boxStrokeColor = color
            tilPassword.hintTextColor = colorState
            tilPassword.boxStrokeColor = color
        }
    }
}