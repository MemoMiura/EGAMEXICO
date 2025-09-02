package com.cursosant.insurance.signoutModule.view

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.cursosant.insurance.BR
import com.cursosant.insurance.R
import com.cursosant.insurance.common.utils.NavUtils
import com.cursosant.insurance.common.utils.UiUtils
import com.cursosant.insurance.databinding.FragmentDialogSignoutBinding
import com.cursosant.insurance.signoutModule.viewModel.SignoutViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/****
 * Project: Contant
 * From: com.cursosandroidant.contant.logoutModule
 * Created by Alain Nicolás Tello on 02/03/23 at 17:16
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
class SignoutDialogFragment : DialogFragment(), DialogInterface.OnShowListener {
    @Inject
    lateinit var utils: UiUtils
    @Inject
    lateinit var navUtils: NavUtils

    private var _binding: FragmentDialogSignoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        activity?.let {
            _binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_dialog_signout, null, false)

            val builder = MaterialAlertDialogBuilder(requireActivity())
                .setPositiveButton(R.string.menu_sign_out, null)
                .setNegativeButton(R.string.dialog_cancel, null)
                .setView(binding.root)

            val dialog = builder.create()
            dialog.setOnShowListener(this)

            return dialog
        }
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onShow(dialogInterface: DialogInterface?) {
        (dialog as? AlertDialog)?.let {
            it.getButton(Dialog.BUTTON_POSITIVE)?.setOnClickListener {
                binding.viewModel?.signout()
            }
            it.getButton(Dialog.BUTTON_NEGATIVE)?.setOnClickListener { dismiss() }
        }

        setupViewModel()
        setupObservers()
    }

    private fun setupViewModel() {
        val vm: SignoutViewModel by viewModels()
        binding.lifecycleOwner = requireActivity()
        binding.setVariable(BR.viewModel, vm)
    }

    private fun setupObservers() {
        with(requireActivity()) {
            binding.viewModel?.let { vm ->
                vm.snackbarMsg.observe(this) { resMsg ->
                    utils.snackbarLong(binding.root, resMsg)
                }
                vm.dismiss.observe(this) { dismiss ->
                    if (dismiss) {
                        dismiss()
                        /*val navController = findNavController(R.id.nav_host_fragment_content_main)
                        navController.navigate(R.id.action_sign_out_to_login)*/
                        navUtils.run { navController.navigate(actionSignoutToLogin) }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}