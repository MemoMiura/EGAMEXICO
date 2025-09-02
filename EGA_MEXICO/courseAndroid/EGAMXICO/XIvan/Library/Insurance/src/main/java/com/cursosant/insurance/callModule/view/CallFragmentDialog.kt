package com.cursosant.insurance.callModule.view

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.cursosant.insurance.R
import com.cursosant.insurance.common.utils.Constants
import com.cursosant.insurance.common.utils.UiUtils
import com.cursosant.insurance.databinding.FragmentDialogCallingBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.callModule
 * Created by Alain Nicolás Tello on 11/06/23 at 12:52
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
class CallFragmentDialog : DialogFragment(), DialogInterface.OnShowListener {
    @Inject
    lateinit var utils: UiUtils

    private var _binding: FragmentDialogCallingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        activity?.let {
            _binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_dialog_calling, null, false)

            val dialog =  MaterialAlertDialogBuilder(requireActivity())
                .setView(binding.root).create()
            dialog.setOnShowListener(this)

            return dialog
        }
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onShow(dialogInterface: DialogInterface?) {
        (dialog as? AlertDialog)?.let {
            lifecycleScope.launch() {
                delay(1_000)
                call911()
            }
        }
    }

    private fun call911() {
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:${Constants.PHONE_911}")
        if (callIntent.resolveActivity(requireActivity().packageManager) != null)
            startActivity(callIntent)
        else
            utils.snackbarLong(binding.root, R.string.call_no_app_resolve)
    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}