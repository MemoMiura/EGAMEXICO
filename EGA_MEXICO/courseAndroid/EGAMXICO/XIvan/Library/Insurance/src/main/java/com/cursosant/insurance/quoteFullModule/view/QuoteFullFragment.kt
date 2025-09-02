package com.cursosant.insurance.quoteFullModule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import com.cursosant.insurance.BR
import com.cursosant.insurance.R
import com.cursosant.insurance.common.entities.ServiceResponse
import com.cursosant.insurance.common.entities.User
import com.cursosant.insurance.common.utils.Constants
import com.cursosant.insurance.common.utils.DateUtils
import com.cursosant.insurance.common.utils.NavUtils
import com.cursosant.insurance.common.utils.UiUtils
import com.cursosant.insurance.databinding.FragmentQuoteFullBinding
import com.cursosant.insurance.quoteResultModule.view.adapters.OnClickListener
import com.cursosant.insurance.quoteFullModule.view.adapters.ServiceFullAdapter
import com.cursosant.insurance.quoteFullModule.viewModel.QuoteFullViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class QuoteFullFragment : Fragment(), OnClickListener {

    private var _binding: FragmentQuoteFullBinding? = null
    private val binding get() = _binding!!

    // ✅ ViewModel como propiedad del Fragment
    private val vm: QuoteFullViewModel by viewModels()

    @Inject lateinit var ui: UiUtils
    @Inject lateinit var date: DateUtils
    @Inject lateinit var navUtils: NavUtils
    @Inject lateinit var adapter: ServiceFullAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuoteFullBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRecyclerView()
        setupSpinners()
        setupBirthdate()
        setupCP()
        setupButtons()
        setupObservers()
    }

    private fun setupViewModel() {
        // ✅ MUY IMPORTANTE para LiveData + DataBinding
        binding.lifecycleOwner = viewLifecycleOwner

        // ✅ Asigna el VM a las variables de los layouts
        binding.setVariable(BR.viewModel, vm)
        binding.cForm.setVariable(BR.viewModel, vm)
        User.instance?.let { binding.cResult.setVariable(BR.email, it.email) }
        binding.cResult.setVariable(BR.viewModel, vm)
    }

    private fun setupRecyclerView() {
        binding.cResult.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = this@QuoteFullFragment.adapter
        }.also {
            adapter.setOnClickListener(this)
            adapter.setType(Constants.FRQ_ANNUAL_VALUE)
        }
    }

    private fun setupObservers() {
        with(binding) {
            viewModel?.let { vm ->
                vm.snackbarMsg.observe(viewLifecycleOwner) { resMsg ->
                    ui.snackbarLong(root, resMsg)
                }
                vm.isHideKeyboard.observe(viewLifecycleOwner) { isHide ->
                    if (isHide) ui.hideKeyboard(root)
                }
                vm.token.observe(viewLifecycleOwner) { result ->
                    if (result != null) {
                        binding.viewModel?.getTypes()
                        User.instance?.let {
                            it.tokenMultiQuoteUser = result
                            binding.viewModel?.getStates(it.getTokenMultiQuote())
                        }
                    } else {
                        ui.snackbarAction(
                            root,
                            getString(R.string.quote_no_permission),
                            R.string.quote_exit
                        ) {
                            // findNavController().navigate(R.id.action_to_home)
                        }
                    }
                }

                // Quote View
                with(cForm) {
                    vm.genders.observe(viewLifecycleOwner) { result ->
                        spGender.setAdapter(
                            ArrayAdapter(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                result
                            )
                        )
                    }
                    vm.types.observe(viewLifecycleOwner) { result ->
                        spType.setAdapter(
                            ArrayAdapter(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                result.values.toList()
                            )
                        )
                    }
                    vm.carBrands.observe(viewLifecycleOwner) { result ->
                        spBrand.setAdapter(
                            ArrayAdapter(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                result
                            )
                        )
                    }
                    vm.carYears.observe(viewLifecycleOwner) { result ->
                        spYear.setAdapter(
                            ArrayAdapter(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                result
                            )
                        )
                    }
                    vm.carModels.observe(viewLifecycleOwner) { result ->
                        spModel.setAdapter(
                            ArrayAdapter(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                result.map { it.name }
                            )
                        )
                    }
                    vm.mxStates.observe(viewLifecycleOwner) { result ->
                        spState.setAdapter(
                            ArrayAdapter(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                result.map { it.name }
                            )
                        )
                    }
                    vm.mxCities.observe(viewLifecycleOwner) { result ->
                        spCity.setAdapter(
                            ArrayAdapter(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                result
                            )
                        )
                    }
                    vm.suburbsList.observe(viewLifecycleOwner) { result ->
                        spSuburb.setAdapter(
                            ArrayAdapter(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                result
                            )
                        )
                    }
                }

                // QuoteResult View
                vm.coverages.observe(viewLifecycleOwner) { result ->
                    if (result.isNotEmpty()) {
                        cResult.spCoverage.setAdapter(
                            ArrayAdapter(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                result
                            )
                        )
                        cResult.spCoverage.setText(result[0], false)
                    }
                }
                vm.frequencies.observe(viewLifecycleOwner) { result ->
                    if (result.isNotEmpty()) {
                        cResult.spFrequency.setAdapter(
                            ArrayAdapter(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                result
                            )
                        )
                        cResult.spFrequency.setText(result[result.size - 1], false)
                    }
                }
                vm.quote.observe(viewLifecycleOwner) { result ->
                    result?.let { goToResult() }
                }
            }
        }
    }

    private fun setupSpinners() {
        with(binding.cForm) {
            viewModel?.getAllGenders()
            spType.setOnItemClickListener { _, _, _, _ -> viewModel?.getBrands() }
            spBrand.setOnItemClickListener { _, _, _, _ -> viewModel?.getYears() }
            spYear.setOnItemClickListener { _, _, _, _ -> viewModel?.getModels() }

            spState.setOnItemClickListener { _, _, _, _ ->
                User.instance?.let { viewModel?.getCities(it.getTokenMultiQuote()) }
                spCity.setText("")
            }
            spCity.setOnItemClickListener { _, _, _, _ ->
                User.instance?.let { viewModel?.getSuburbs(it.getTokenMultiQuote()) }
                spSuburb.setText("")
            }
            spSuburb.setOnItemClickListener { _, _, _, _ ->
                if (cbCP.isChecked) User.instance?.let { viewModel?.getCP(it.getTokenMultiQuote()) }
            }
        }
        // QuoteResult View
        with(binding.cResult) {
            spFrequency.setOnItemClickListener { _, _, position, _ ->
                val type = resources.getIntArray(R.array.frequencies_key)[position]
                adapter.setType(type.toString())
            }
            spCoverage.setOnItemClickListener { _, _, position, _ ->
                val coverage = resources.getStringArray(R.array.coverages_key)[position]
                viewModel?.setCoverage(coverage)
            }
        }
    }

    private fun setupBirthdate() {
        with(binding.cForm) {
            etBirthdate.setOnClickListener {
                val picker = MaterialDatePicker.Builder.datePicker().build()
                picker.addOnPositiveButtonClickListener { timeInMilliseconds ->
                    etBirthdate.setText(
                        date.formatDate(
                            Constants.DATE_PATTERN_BIRTHDATE_UI,
                            timeInMilliseconds
                        )
                    )
                    binding.viewModel?.setBirthdate(
                        date.formatDate(
                            Constants.DATE_PATTERN_BIRTHDATE_PARAM,
                            timeInMilliseconds
                        ),
                        etBirthdate.text.toString()
                    )
                }
                picker.show(childFragmentManager, picker.toString())
            }
        }
    }

    private fun setupCP() {
        with(binding.cForm) {
            etCp.addTextChangedListener {
                if (!cbCP.isChecked) User.instance?.let {
                    viewModel?.getSuburbsByCP(it.getTokenMultiQuote())
                }
            }
            cbCP.addOnCheckedStateChangedListener { checkBox, _ ->
                if (checkBox.isChecked) {
                    etCp.setText("")
                    tvState.text = ""
                    tvCity.text = ""
                    spSuburb.run {
                        setText("")
                        viewModel?.suburbsList?.value?.let { suburbs ->
                            setAdapter(
                                ArrayAdapter(
                                    requireContext(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    suburbs
                                )
                            )
                        }
                        clearFocus()
                    }
                    viewModel?.mxStates?.value?.let { states ->
                        spState.setAdapter(
                            ArrayAdapter(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                states.map { it.name }
                            )
                        )
                    }
                    viewModel?.mxCities?.value?.let { cities ->
                        spCity.setAdapter(
                            ArrayAdapter(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                cities
                            )
                        )
                    }
                }
            }
        }
    }

    private fun setupButtons() {
        binding.cForm.btnQuote.setOnClickListener {
            adapter.clear(); refreshAdapterSize()
            sendQuote()
        }
        binding.cResult.btnBack.setOnClickListener { goToQuote() }
        binding.cResult.retryContainer.btnRetry.setOnClickListener { sendQuote() }
    }

    private fun refreshAdapterSize() {
        binding.viewModel?.servicesSize?.value = adapter.itemCount
    }

    private fun goToResult() {
        val moveLeft = MaterialSharedAxis(MaterialSharedAxis.X, true)
        TransitionManager.beginDelayedTransition(binding.cMain, moveLeft)
        binding.cForm.cMain.visibility = View.GONE
        binding.cResult.cMain.visibility = View.VISIBLE
    }

    private fun goToQuote() {
        val moveRight = MaterialSharedAxis(MaterialSharedAxis.X, false)
        TransitionManager.beginDelayedTransition(binding.cMain, moveRight)
        binding.cResult.cMain.visibility = View.GONE
        binding.cForm.cMain.visibility = View.VISIBLE
    }

    private fun sendQuote() {
        with(binding.cForm) {
            val isValid = validateFields(
                tilGender, tilBirthdate, tilType, tilBrand, tilYear, tilModel, tilCP, tilSuburb,
                if (cbCP.isChecked) tilState else null,
                if (cbCP.isChecked) tilCity else null
            )
            if (isValid) {
                User.instance?.let { user ->
                    viewModel?.let { vm ->
                        val typeId = vm.types.value?.entries?.firstOrNull { it.value == vm.type.value }?.key ?: 0
                        val modelId = vm.carModels.value?.firstOrNull { it.name == vm.model.value }?.value
                        val stateId = vm.mxStates.value?.firstOrNull { it.name == vm.state.value }?.value
                        vm.getQuote(
                            user.getTokenMultiQuote(),
                            vm.gender.value,
                            vm.birthdate.value,
                            typeId,
                            vm.brand.value,
                            vm.year.value,
                            modelId,
                            vm.model.value,
                            vm.cp.value,
                            stateId,
                            vm.state.value,
                            vm.city.value,
                            vm.suburb.value,
                            user.email
                        )
                    }
                }
            }
        }
    }

    private fun validateFields(vararg textFields: TextInputLayout?): Boolean {
        var isValid = true
        textFields.forEach { tf ->
            tf?.editText?.text?.let { text ->
                if (text.toString().isBlank()) {
                    tf.isErrorEnabled = true
                    tf.error = getString(R.string.error_field_required)
                    isValid = false
                }
            }
        }
        return isValid
    }

    override fun onStart() {
        super.onStart()
        User.instance?.let { binding.viewModel?.loginMultiQuoter(it.username, it.getPassword()) }
    }

    override fun onPause() {
        super.onPause()
        binding.viewModel?.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ui.clearSnackbar()
        _binding = null
    }

    override fun onClick(serviceResponse: ServiceResponse) {
        navUtils.run {
            serviceResponse.carDescription = binding.cResult.tvCarDescription.text.toString()
            val args = Bundle()
            args.putParcelable(Constants.ARG_SERVICE_RESPONSE, serviceResponse)
            navController.navigate(actionQuoteResultToQuoteResultDetail, args)
        }
    }
}
