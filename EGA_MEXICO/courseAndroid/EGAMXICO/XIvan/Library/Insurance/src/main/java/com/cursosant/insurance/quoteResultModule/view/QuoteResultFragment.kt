package com.cursosant.insurance.quoteResultModule.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cursosant.insurance.BR
import com.cursosant.insurance.R
import com.cursosant.insurance.common.entities.QuoteRequest
import com.cursosant.insurance.common.entities.QuoteResponse
import com.cursosant.insurance.common.entities.ServiceResponse
import com.cursosant.insurance.common.entities.User
import com.cursosant.insurance.common.utils.Constants
import com.cursosant.insurance.common.utils.NavUtils
import com.cursosant.insurance.common.utils.UiUtils
import com.cursosant.insurance.databinding.FragmentQuoteResultBinding
import com.cursosant.insurance.quoteResultModule.view.adapters.OnClickListener
import com.cursosant.insurance.quoteResultModule.view.adapters.ServiceAdapter
import com.cursosant.insurance.quoteResultModule.viewModel.QuoteResultViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.quoteResultModule.view
 * Created by Alain Nicolás Tello on 17/06/23 at 10:26
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
class QuoteResultFragment : Fragment(), OnClickListener {

    @Inject
    lateinit var ui: UiUtils
    @Inject
    lateinit var navUtils: NavUtils
    @Inject
    lateinit var adapter: ServiceAdapter

    private var _binding: FragmentQuoteResultBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var utils: UiUtils

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuoteResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRecyclerView()
        setupSpinners()
        setupButtons()
        setupObservers()
    }

    private fun setupViewModel() {
        val vm: QuoteResultViewModel by viewModels()
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, vm)
        User.instance?.let { binding.setVariable(BR.email, it.email) }
    }

    @Suppress("DEPRECATION")
    private fun setupArguments() {
        with(requireArguments()) {
            val quoteResponse: QuoteResponse?
            val quoteRequest: QuoteRequest?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                quoteResponse = getParcelable(Constants.ARG_QUOTE_RESPONSE, QuoteResponse::class.java)
                quoteRequest = getParcelable(Constants.ARG_QUOTE_REQUEST, QuoteRequest::class.java)
            } else {
                quoteResponse = getParcelable(Constants.ARG_QUOTE_RESPONSE)
                quoteRequest = getParcelable(Constants.ARG_QUOTE_REQUEST)
            }
            quoteResponse?.let {
                binding.viewModel?.setQuoteResponse(it)
            }
            quoteRequest?.let {
                binding.viewModel?.setQuoteRequest(it)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = this@QuoteResultFragment.adapter
        }.also {
            adapter.setOnClickListener(this)
            adapter.setType(Constants.FRQ_ANNUAL_VALUE)
        }
    }

    private fun setupObservers() {
        with(binding){
            viewModel?.let { vm ->
                vm.getAllCoverages()
                vm.snackbarMsg.observe(viewLifecycleOwner) { resMsg ->
                    ui.snackbarLong(root, resMsg)
                }
                //QuoteResult View
                vm.quoteRequest.observe(viewLifecycleOwner) { result ->
                    result?.model?.let { getServices() }
                }
                vm.coverages.observe(viewLifecycleOwner) { result ->
                    if (result.isNotEmpty()) {
                        spCoverage.setAdapter(
                            ArrayAdapter(requireContext(),
                            android.R.layout.simple_spinner_dropdown_item, result) )
                        spCoverage.setText(result[0], false)
                    }
                }
                vm.frequencies.observe(viewLifecycleOwner) { result ->
                    if (result.isNotEmpty()) {
                        spFrequency.setAdapter(
                            ArrayAdapter(requireContext(),
                            android.R.layout.simple_spinner_dropdown_item, result) )
                        spFrequency.setText(result[result.size - 1], false)

                        setupArguments()
                    }
                }
                vm.serviceResponse.observe(viewLifecycleOwner) { result ->
                    result?.let {
                        adapter.add(it)
                        refreshAdapterSize()
                    }
                }
            }
        }
    }

    private fun setupSpinners() {
        with(binding) {
            spFrequency.setOnItemClickListener { _, _, position, _ ->
                val type = resources.getIntArray(R.array.frequencies_key)[position]
                adapter.setType(type.toString())
                getServices()
            }
            spCoverage.setOnItemClickListener { _, _, position, _ ->
                val coverage = resources.getStringArray(R.array.coverages_key)[position]
                viewModel?.setCoverage(coverage)
                getServices()
            }
        }
    }

    private fun setupButtons() {
        binding.retryContainer.btnRetry.setOnClickListener { getServices() }
    }

    private fun refreshAdapterSize() {
        binding.viewModel?.servicesSize?.value = adapter.itemCount
    }

    private fun getServices() {
        User.instance?.let { binding.viewModel?.getServices(it.getTokenMultiQuote(), it.email) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*
    * OnClickListener
    * */
    override fun onClick(serviceResponse: ServiceResponse) {
        navUtils.run {
            serviceResponse.carDescription = binding.tvCarDescription.text.toString()
            val args = Bundle()
            args.putParcelable(Constants.ARG_SERVICE_RESPONSE, serviceResponse)
            navController.navigate(actionQuoteResultToQuoteResultDetail, args)
        }
    }
}