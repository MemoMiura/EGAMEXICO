package com.cursosant.insurance.deleteAccountModule.view

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cursosant.insurance.BR
import com.cursosant.insurance.R
import com.cursosant.insurance.common.entities.User
import com.cursosant.insurance.common.utils.UiUtils
import com.cursosant.insurance.databinding.FragmentDialogDeleteAccountBinding
import com.cursosant.insurance.deleteAccountModule.viewModel.DeleteAccountViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DeleteAccountFragment : DialogFragment(), DialogInterface.OnShowListener {

    @Inject lateinit var utils: UiUtils

    private var _binding: FragmentDialogDeleteAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        activity?.let {
            _binding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_dialog_delete_account,
                null,
                false
            )

            val builder = MaterialAlertDialogBuilder(requireActivity())
                .setPositiveButton(R.string.dialog_delete_account_ok, null)
                .setNegativeButton(R.string.dialog_cancel, null)
                .setView(binding.root)

            return builder.create().also { it.setOnShowListener(this) }
        }
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onShow(dialogInterface: DialogInterface?) {
        (dialog as? AlertDialog)?.let { dlg ->
            // Botón Aceptar
            dlg.getButton(Dialog.BUTTON_POSITIVE)?.setOnClickListener {
                MaterialAlertDialogBuilder(requireActivity())
                    .setMessage(R.string.dialog_delete_account_confirm_msg)
                    .setPositiveButton(R.string.dialog_delete_account_ok) { _, _ ->
                        User.instance?.let { user ->
                            binding.viewModel?.disableAccount(
                                user.username,
                                user.passwordUser
                            ) { response ->
                                Toast.makeText(
                                    requireContext(),
                                    response.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                                // Cierra el diálogo y regresa al login
                                this@DeleteAccountFragment.dismiss()
                                findNavController().navigate(R.id.nav_login)

                            }
                        }
                    }
                    .setNegativeButton(R.string.dialog_cancel, null)
                    .show()
            }

            // Botón Cancelar
            dlg.getButton(Dialog.BUTTON_NEGATIVE)?.setOnClickListener {
                this@DeleteAccountFragment.dismiss()

            }
        }

        setupViewModel()
        setupObservers()
    }

    private fun setupViewModel() {
        val vm: DeleteAccountViewModel by viewModels()
        binding.lifecycleOwner = requireActivity()
        binding.setVariable(BR.viewModel, vm)
    }

    private fun setupObservers() {
        val activity = requireActivity()

        binding.viewModel?.let { vm ->
            vm.snackbarMsg.observe(activity) { resMsg: Int ->
                utils.snackbarLong(binding.root, resMsg)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
