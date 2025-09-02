package com.cursosant.insurance.policyDetailModule.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cursosant.insurance.BR
import com.cursosant.insurance.R
import com.cursosant.insurance.common.entities.FilePDF
import com.cursosant.insurance.common.entities.User
import com.cursosant.insurance.common.utils.Constants
import com.cursosant.insurance.common.utils.DateUtils
import com.cursosant.insurance.common.utils.NavUtils
import com.cursosant.insurance.common.utils.UiUtils
import com.cursosant.insurance.common.utils.Utils
import com.cursosant.insurance.databinding.FragmentPolicyDetailBinding
import com.cursosant.insurance.policyDetailModule.view.adapters.BeneficiaryAdapter
import com.cursosant.insurance.policyDetailModule.view.adapters.CoverageAdapter
import com.cursosant.insurance.policyDetailModule.view.adapters.FilePDFAdapter
import com.cursosant.insurance.policyDetailModule.view.adapters.OnClickListener
import com.cursosant.insurance.policyDetailModule.view.adapters.ReceiptAdapter
import com.cursosant.insurance.policyDetailModule.viewModel.PolicyDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.policyDetailModule.view
 * Created by Alain Nicolás Tello on 03/06/23 at 12:54
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
open class PolicyDetailFragment : Fragment(), OnClickListener {

    @Inject
    lateinit var utils: Utils
    @Inject
    lateinit var uiUtils: UiUtils
    @Inject
    lateinit var dateUtils: DateUtils
    @Inject
    lateinit var navUtils: NavUtils
    @Inject
    lateinit var fileAdapter: FilePDFAdapter
    @Inject
    lateinit var coverageAdapter: CoverageAdapter
    @Inject
    lateinit var receiptAdapter: ReceiptAdapter
    @Inject
    lateinit var beneficiaryAdapter: BeneficiaryAdapter

    private var _binding: FragmentPolicyDetailBinding? = null
    private val binding get() = _binding!!

    //private val args: PolicyDetailFragmentArgs by navArgs()
    private var policyId: Long = 0
    protected var subramo: String = ""

    private var isAncora = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPolicyDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupArguments()
        setupViewModel()
        setupRecyclerViews()
        setupButtons()
        setupObservers()
    }

    private fun setupArguments() {
        policyId = requireArguments().getLong(Constants.ARG_ID, 0)
        subramo = requireArguments().getString(Constants.ARG_POLICY_SUBRAMO, "")
    }

    private fun setupViewModel() {
        val vm: PolicyDetailViewModel by viewModels()
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, vm)
        binding.setVariable(BR.utils, dateUtils)
    }

    private fun setupObservers() {
        binding.viewModel?.let { vm ->
            vm.snackbarMsg.observe(viewLifecycleOwner) { resMsg ->
                uiUtils.snackbarLong(binding.root, resMsg)
            }
            vm.toastMsg.observe(viewLifecycleOwner) { resMsg ->
                uiUtils.toastLong(resMsg)
            }
            vm.policy.observe(viewLifecycleOwner) { result ->
                fileAdapter.submitList(result.files)
                coverageAdapter.submitList(result.coverageInPolicy_policy)
                receiptAdapter.submitList(result.recibos_poliza)
                beneficiaryAdapter.submitList(result.getBeneficiaries())
            }
            vm.isSent.observe(viewLifecycleOwner) {
                uiUtils.toastLong(R.string.sinister_report_sent)
            }
        }
    }

    private fun setupRecyclerViews() {
        binding.rvFilesPDF.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = fileAdapter
        }.also { fileAdapter.setOnClickListener(this) }
        binding.rvCoverage.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = coverageAdapter
        }
        binding.rvReceipt.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = receiptAdapter
        }
        binding.rvBeneficiary.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = beneficiaryAdapter
        }
    }

    private fun setupButtons(){
        with(binding){
            retryContainer.btnRetry.setOnClickListener { getPolicy() }
            btnShare.setOnClickListener { sharePolicy() }
            btnEdit.setOnClickListener { editPolicy() }
            btnReport.setOnClickListener { sendReport() }
        }
    }

    private fun sharePolicy() {
        binding.viewModel?.policy?.value?.let {
            val sendIntent = Intent().also {  intent ->
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT, // FIXME: use constants instead
                    "Poliza: ${it.poliza_number} Aseguradora: ${it.aseguradora?.alias} " +
                            "Vigencia: ${dateUtils.getTotalValidity(it.start_of_validity, it.end_of_validity)} " +
                            "Subramo: ${it.subramo}")
                intent.type = "text/plain"
            }
            startActivity(sendIntent)
        }
    }

    private fun editPolicy() {
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:soporte@miurabox.com")
        startActivity(emailIntent)
    }

    private fun sendReport() {
        binding.viewModel?.let { vm ->
            User.instance?.let { vm.sendReport(it.token.token) }
            //checkPhone(vm.policy.value?.aseguradora?.phone)
            val phone = if (isAncora) vm.policy.value?.getPhone() else vm.policy.value?.aseguradora?.phone
            val doctorName = if (isAncora) vm.policy.value?.getDoctorName() else null
            utils.checkPhone(binding.root, phone, doctorName)
        }
    }
    // TODO: can this make fusion with sinisterModule?
    /*private fun checkPhone(phone: String?) {
        if (!phone.isNullOrBlank()) {
            utils.snackbarAction(binding.root, getString(R.string.sinister_call_msg, phone),
                R.string.sinister_call){ call(phone) }
        } else {
            utils.snackbarAction(binding.root, getString(R.string.sinister_no_phone),
                R.string.snackbar_ok){}
        }
    }

    private fun call(phone: String) {
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:$phone")
        if (callIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(callIntent)
        } else utils.snackbarLong(binding.root, R.string.call_no_app_resolve)
    }*/

    override fun onResume() {
        super.onResume()
        getPolicy()
    }

    private fun getPolicy() {
        //val idPolicy = args.idPolicy
        User.instance?.let { binding.viewModel?.getPolicy(it.token.token, policyId) }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    /*
    * OnClickListener
    * */
    override fun onClick(file: FilePDF) {
        /*val navController = requireActivity().findNavController(R.id.nav_host_fragment_content_main)
        val action = PolicyDetailFragmentDirections.actionPolicyDetailToPdf()
        action.name = file.nombre
        action.url = file.arch
        navController.navigate(action)*/
        navUtils.run {
            val args = Bundle()
            args.putString(Constants.ARG_NAME, file.nombre)
            args.putString(Constants.ARG_URL, file.arch)
            navController.navigate(actionPolicyDetailToPdf, args)
        }
    }

    /*
    * For Ancora
    * */
    protected fun showAndSetGoTicketsButton(subramo: String) {
    //protected fun showAndSetGoTicketsButton(block: () -> Unit) {
        with(binding) {
            btnGoTickets.visibility = View.VISIBLE
            btnGoTickets.setOnClickListener { navUtils.run { navController.navigate(actionToTickets) } }

            when (subramo) {
                Constants.SUBRAMO_LIFE, Constants.SUBRAMO_DAMAGES -> {
                    btnGoTickets.setText(R.string.policy_detail_btn_go_tickets_alt)
                }
                Constants.SUBRAMO_MEDIC_EXPENSES -> {
                    btnGoTickets.setText(R.string.policy_detail_btn_go_tickets_alt)
                    btnReport.setText(R.string.policy_detail_btn_go_report_alt)
                }
            }
        }

        //binding.btnGoTickets.setOnClickListener { block() }
    }

    protected fun enableAncora() {
        isAncora = true
    }

    protected fun setButtonsColor(colorTicketRes: Int, onColorTicketRes: Int, colorReportRes: Int, onColorReportRes: Int) {
        with(binding) {
            val colorTicket = ContextCompat.getColor(requireActivity(), colorTicketRes)
            btnGoTickets.setBackgroundColor(colorTicket)
            val onColorTicket = ContextCompat.getColor(requireActivity(), onColorTicketRes)
            btnGoTickets.setTextColor(onColorTicket)
            btnGoTickets.setIconTintResource(onColorTicketRes)

            val colorReport = ContextCompat.getColor(requireActivity(), colorReportRes)
            btnReport.setBackgroundColor(colorReport)
            val onColorReport = ContextCompat.getColor(requireActivity(), onColorReportRes)
            btnReport.setTextColor(onColorReport)
            btnReport.setIconTintResource(onColorReportRes)
        }
    }
}