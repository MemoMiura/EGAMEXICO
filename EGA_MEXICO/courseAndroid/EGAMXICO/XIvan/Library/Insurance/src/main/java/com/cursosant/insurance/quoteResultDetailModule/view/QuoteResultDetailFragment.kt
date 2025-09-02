package com.cursosant.insurance.quoteResultDetailModule.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cursosant.insurance.BR
import com.cursosant.insurance.common.entities.ServiceResponse
import com.cursosant.insurance.common.utils.Constants
import com.cursosant.insurance.common.utils.DateUtils
import com.cursosant.insurance.common.utils.UiUtils
import com.cursosant.insurance.databinding.FragmentQuoteResultDetailBinding
import com.cursosant.insurance.policyDetailModule.view.adapters.CoverageAdapter
import com.cursosant.insurance.quoteResultDetailModule.view.adapter.PayFrequencyAdapter
import com.cursosant.insurance.quoteResultDetailModule.viewModel.QuoteResultDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.quoteResultDetailModule.view.adapter
 * Created by Alain Nicolás Tello on 29/07/23 at 10:18
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
class QuoteResultDetailFragment : Fragment() {

    @Inject
    lateinit var utils: UiUtils
    @Inject
    lateinit var dateUtils: DateUtils
    @Inject
    lateinit var coverageAdapter: CoverageAdapter
    @Inject
    lateinit var payFrequencyAdapter: PayFrequencyAdapter

    private var _binding: FragmentQuoteResultDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentQuoteResultDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupArguments()
        setupRecyclerViews()
        setupObservers()
    }

    private fun setupViewModel() {
        val vm: QuoteResultDetailViewModel by viewModels()
        binding.apply {
            lifecycleOwner = this@QuoteResultDetailFragment
            setVariable(BR.viewModel, vm)
            setVariable(BR.utils, dateUtils)
        }
    }

    private fun setupArguments() {
        with(requireArguments()) {
            val serviceResponse = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                getParcelable(Constants.ARG_SERVICE_RESPONSE, ServiceResponse::class.java)
            } else {
                @Suppress("DEPRECATION")
                getParcelable(Constants.ARG_SERVICE_RESPONSE)
            }
            serviceResponse?.let {
                binding.viewModel?.setServiceResponse(it)
            }
        }
    }

    private fun setupRecyclerViews() {
        binding.rvCoverage.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = coverageAdapter
        }
        binding.rvPayFrequencies.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = payFrequencyAdapter
        }
    }

    private fun setupObservers() {
        binding.viewModel?.let { vm ->
            vm.snackbarMsg.observe(viewLifecycleOwner) { resMsg ->
                utils.snackbarLong(binding.root, resMsg)
            }
            vm.serviceResponse.observe(viewLifecycleOwner) { result ->
                coverageAdapter.submitList(result.quote_coverages)
                vm.getPayFrequencies(result)
            }
            vm.payFrequencies.observe(viewLifecycleOwner) { result ->
                payFrequencyAdapter.submitList(result)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}