package com.cursosant.insurance.sinisterModule.view

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.cursosant.insurance.BR
import com.cursosant.insurance.R
import com.cursosant.insurance.common.entities.User
import com.cursosant.insurance.common.utils.UiUtils
import com.cursosant.insurance.common.utils.Utils
import com.cursosant.insurance.databinding.FragmentDialogSinisterBinding
import com.cursosant.insurance.sinisterModule.viewModel.SinisterViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.sinisterModule.view
 * Created by Alain Nicolás Tello on 12/06/23 at 19:02
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
open class SinisterFragmentDialog : DialogFragment(), DialogInterface.OnShowListener {
    @Inject
    lateinit var utils: Utils
    @Inject
    lateinit var uiUtils: UiUtils

    private var _binding: FragmentDialogSinisterBinding? = null
    private val binding get() = _binding!!

    private var isAncora = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        activity?.let {
            _binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_dialog_sinister, null, false)
            val builder = MaterialAlertDialogBuilder(requireActivity())
                .setView(binding.root)

            val dialog = builder.create()
            dialog.setOnShowListener(this)

            return dialog
        }
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onShow(dialogInterface: DialogInterface?) {
        (dialog as? AlertDialog)?.let {
            lifecycleScope.launch() {
                delay(1_000)
                User.instance?.let { binding.viewModel?.getPoliciesInUser(it.token.token, it.username) }
            }
        }

        setupViewModel()
        setupObservers()
    }

    private fun setupViewModel() {
        val vm: SinisterViewModel by viewModels()
        binding.lifecycleOwner = requireActivity()
        binding.setVariable(BR.viewModel, vm)
    }

    private fun setupObservers() {
        with(requireActivity()) {
            binding.viewModel?.let { vm ->
                vm.snackbarMsg.observe(this) { resMsg ->
                    uiUtils.snackbarLong(binding.root, resMsg)
                }
                vm.toastMsg.observe(this) { resMsg ->
                    uiUtils.toastLong(resMsg)
                }
                vm.policies.observe(this) { result ->
                    val dialog =  MaterialAlertDialogBuilder(requireActivity())
                        .setTitle(R.string.sinister_dialog_title_chooser)
                        .setItems(result.map { it.getDescription() }.toTypedArray()){dialog, i ->
                            val policy = vm.policies.value?.get(i)
                            policy?.id?.let {id ->
                                User.instance?.let { binding.viewModel?.getPolicy(it.token.token, id) }
                            }
                        }
                        .setOnCancelListener { this@SinisterFragmentDialog.dismiss() }
                        .create()
                    dialog.show()
                }
                vm.policy.observe(this) { result ->
                    sendReport(result.id)
                    //checkPhone(result.aseguradora?.phone)

                    val phone = if (isAncora) result.getPhone() else result.aseguradora?.phone
                    val doctorName = if (isAncora) result.getDoctorName() else null
                    utils.checkPhone(binding.root, phone, doctorName, this@SinisterFragmentDialog)
                    //checkPhone(result.getPhone(), result.getDoctorName())
                }
                vm.isSent.observe(this) {
                    uiUtils.toastLong(R.string.sinister_report_sent)
                }
            }
        }
    }

    private fun sendReport(policyId: Long) {
        User.instance?.let { binding.viewModel?.sendReport(it.token.token, policyId) }
    }

    /*private fun checkPhone(phone: String?, name: String? = null) {
        if (!phone.isNullOrBlank()) {
            uiUtils.snackbarAction(binding.root,
                if (name != null) "$name: $phone" else getString(R.string.sinister_call_msg, phone),
                R.string.sinister_call){ call(phone) }
        } else {
            uiUtils.snackbarAction(binding.root, getString(R.string.sinister_no_phone),
                R.string.snackbar_ok){ dismiss() }
        }
    }

    private fun call(phone: String) {
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:$phone")
        if (callIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(callIntent)
            dismiss()
        } else uiUtils.snackbarLong(binding.root, R.string.call_no_app_resolve)
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun enableAncora() {
        isAncora = true
    }
}