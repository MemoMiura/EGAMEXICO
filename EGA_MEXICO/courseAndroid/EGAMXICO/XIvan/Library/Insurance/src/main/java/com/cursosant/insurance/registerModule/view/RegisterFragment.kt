package com.cursosant.insurance.registerModule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cursosant.insurance.BR
import com.cursosant.insurance.R
import com.cursosant.insurance.common.utils.NavUtils
import com.cursosant.insurance.common.utils.UiUtils
import com.cursosant.insurance.common.utils.Utils
import com.cursosant.insurance.databinding.FragmentRegisterBinding
import com.cursosant.insurance.registerModule.viewModel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.registerModule
 * Created by Alain Nicolás Tello on 01/06/23 at 8:13
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
open class RegisterFragment : Fragment() {

    @Inject
    lateinit var uiUtils: UiUtils
    @Inject
    lateinit var utils: Utils
    @Inject
    lateinit var navUtils: NavUtils

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupButtons()
        setupObservers()
    }

    private fun setupViewModel() {
        val vm: RegisterViewModel by viewModels()
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, vm)
    }

    private fun setupObservers() {
        binding.viewModel?.let { vm ->
            vm.snackbarMsg.observe(viewLifecycleOwner) { resMsg ->
                uiUtils.snackbarLong(binding.root, resMsg)
            }
            vm.snackbarWarning.observe(viewLifecycleOwner) { resMsg ->
                uiUtils.snackbarWarning(binding.root, resMsg)
            }
            vm.isHideKeyboard.observe(viewLifecycleOwner) { isHide ->
                if (isHide) uiUtils.hideKeyboard(binding.root)
            }
        }
    }

    private fun setupButtons() {
        with(binding){
            btnGoToLogin.setOnClickListener {
                navUtils.run { navController.navigate(actionRegisterToLogin) }
            }
            btnRegister.setOnClickListener {
                if (validateFields()) {
                    viewModel?.register(etFirstName.text.toString().trim(),
                        etLastName.text.toString().trim(),
                        etEmail.text.toString().trim(),
                        etPassword.text.toString().trim())
                }
            }
        }
    }

    private fun validateFields(): Boolean{
        var isValid = true

        with(binding) {
            if (etFirstName.text.toString().isBlank()) {
                tilFirstName.error = getString(R.string.register_empty_first_name)
                isValid = false
            }
            if (etLastName.text.toString().isBlank()) {
                tilLastName.error = getString(R.string.register_empty_last_name)
                isValid = false
            }

            if (etEmail.text.toString().isBlank()) {
                tilEmail.error = getString(R.string.register_empty_email)
                isValid = false
            } else if (!utils.validateEmail(etEmail.text.toString().trim())) {
                tilEmail.error = getString(R.string.login_invalid_email)
                isValid = false
            }

            val password = etPassword.text.toString()
            val passwordConfirm = etPasswordConfirm.text.toString()
            if (password.isBlank()) {
                tilPassword.error = getString(R.string.login_empty_password)
                isValid = false
            }
            if (passwordConfirm.isBlank()) {
                tilPasswordConfirm.error = getString(R.string.register_empty_password_confirm)
                isValid = false
            } else if (password.isNotBlank() && passwordConfirm.isNotBlank() && password != passwordConfirm){
                tilPasswordConfirm.error = getString(R.string.register_passwords_different)
                isValid = false
            }
        }

        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // TODO: unificar con LoginFragment y HomeFragment ???
    protected fun setImgCover(imgRes: Int) {
        binding.imgCover.setImageResource(imgRes)
    }

    protected fun setBackgroundColor(colorRes: Int, onColorRes: Int) {
        with(binding) {
            containerMain.setBackgroundResource(colorRes)
            val onColor = ContextCompat.getColor(requireActivity(), onColorRes)
            btnGoToLogin.setTextColor(onColor)
            tvQuestion.setTextColor(onColor)
        }
    }

    protected fun setHintStrokeColor(colorRes: Int) {
        val color = ContextCompat.getColor(requireActivity(), colorRes)
        val colorState = ContextCompat.getColorStateList(requireActivity(), colorRes)
        with(binding) {
            tilEmail.hintTextColor = colorState
            tilEmail.boxStrokeColor = color
            tilPassword.hintTextColor = colorState
            tilPassword.boxStrokeColor = color
            tilFirstName.hintTextColor = colorState
            tilFirstName.boxStrokeColor = color
            tilLastName.hintTextColor = colorState
            tilLastName.boxStrokeColor = color
            tilPasswordConfirm.hintTextColor = colorState
            tilPasswordConfirm.boxStrokeColor = color
        }
    }
}