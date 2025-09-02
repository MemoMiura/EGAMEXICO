package com.cursosant.insurance.quoteModule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cursosant.insurance.BR
import com.cursosant.insurance.R
import com.cursosant.insurance.common.entities.QuoteResponse
import com.cursosant.insurance.common.entities.User
import com.cursosant.insurance.common.utils.Constants
import com.cursosant.insurance.common.utils.DateUtils
import com.cursosant.insurance.common.utils.NavUtils
import com.cursosant.insurance.common.utils.UiUtils
import com.cursosant.insurance.databinding.FragmentQuoteBinding
import com.cursosant.insurance.quoteFullModule.view.adapters.ServiceFullAdapter
import com.cursosant.insurance.quoteModule.viewModel.QuoteViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.quoteModule.view.adapter
 * Created by Alain Nicolás Tello on 03/08/23 at 19:08
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
class QuoteFragment : Fragment() {

    private var _binding: FragmentQuoteBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var ui: UiUtils
    @Inject
    lateinit var date: DateUtils
    @Inject
    lateinit var navUtils: NavUtils
    @Inject
    lateinit var adapter: ServiceFullAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupSpinners()
        setupBirthdate()
        setupCP()
        setupButtons()
        setupObservers()
    }

    private fun setupViewModel() {
        val vm: QuoteViewModel by viewModels()
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, vm)
    }

    private fun setupObservers() {
        with(binding){
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
                        ui.snackbarAction(root, getString(R.string.quote_no_permission),
                            R.string.quote_exit){
                            // FIXME: uncomment after module will be finished
                            //findNavController().navigate(R.id.action_to_home)
                        }
                    }
                }
                vm.genders.observe(viewLifecycleOwner) { result ->
                    spGender.setAdapter(
                        ArrayAdapter(requireContext(),
                            android.R.layout.simple_spinner_dropdown_item, result) )
                }
                vm.carTypes.observe(viewLifecycleOwner) { result ->
                    spType.setAdapter(
                        ArrayAdapter(requireContext(),
                            android.R.layout.simple_spinner_dropdown_item, result.values.toList())
                    )
                }
                vm.carBrands.observe(viewLifecycleOwner) { result ->
                    spBrand.setAdapter(
                        ArrayAdapter(requireContext(),
                            android.R.layout.simple_spinner_dropdown_item, result)
                    )
                }
                vm.carYears.observe(viewLifecycleOwner) { result ->
                    spYear.setAdapter(
                        ArrayAdapter(requireContext(),
                            android.R.layout.simple_spinner_dropdown_item, result)
                    )
                }
                vm.carModels.observe(viewLifecycleOwner) { result ->
                    spModel.setAdapter(
                        ArrayAdapter(requireContext(),
                            android.R.layout.simple_spinner_dropdown_item, result.map { it.name })
                    )
                }
                vm.mxStates.observe(viewLifecycleOwner) { result ->
                    spState.setAdapter(
                        ArrayAdapter(requireContext(),
                            android.R.layout.simple_spinner_dropdown_item, result.map { it.name })
                    )
                }
                vm.mxCities.observe(viewLifecycleOwner) { result ->
                    spCity.setAdapter(
                        ArrayAdapter(requireContext(),
                            android.R.layout.simple_spinner_dropdown_item, result)
                    )
                }
                vm.suburbs.observe(viewLifecycleOwner) { result ->
                    spSuburb.setAdapter(
                        ArrayAdapter(requireContext(),
                            android.R.layout.simple_spinner_dropdown_item, result)
                    )
                }
                vm.quoteResponse.observe(viewLifecycleOwner) { result ->
                    result?.let {
                        goToResult(it)
                    }
                }
            }
        }
    }

    private fun setupSpinners() {
        with(binding){
            viewModel?.getAllGenders()
            spType.setOnItemClickListener { adapterView, view, position, id ->
                viewModel?.getBrands()
            }
            spBrand.setOnItemClickListener { _, _, _, _ ->
                viewModel?.getYears()
            }
            spYear.setOnItemClickListener { _, _, _, _ ->
                viewModel?.getModels()
            }

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
    }

    private fun setupBirthdate(){
        with(binding) {
            etBirthdate.setOnClickListener {
                val picker = MaterialDatePicker.Builder.datePicker().build()
                picker.addOnPositiveButtonClickListener { timeInMilliseconds ->
                    etBirthdate.setText(date.formatDate(Constants.DATE_PATTERN_BIRTHDATE_UI, timeInMilliseconds))
                    binding.viewModel?.setBirthdate(
                        date.formatDate(Constants.DATE_PATTERN_BIRTHDATE_PARAM, timeInMilliseconds),
                        etBirthdate.text.toString())
                }
                picker.show(childFragmentManager, picker.toString())
            }
        }
    }

    private fun setupCP(){
        with(binding){
            etCp.addTextChangedListener {
                if (!cbCP.isChecked) User.instance?.let {
                    viewModel?.getSuburbsByCP(it.getTokenMultiQuote())
                }
            }
            cbCP.addOnCheckedStateChangedListener { checkBox, _ ->// _ = state
                if (checkBox.isChecked) {
                    etCp.setText("")
                    tvState.text = ""
                    tvCity.text = ""
                    spSuburb.run {
                        setText("")
                        //setAdapter(null)
                        viewModel?.suburbs?.value?.let { suburbs ->
                            setAdapter(ArrayAdapter(requireContext(),
                                android.R.layout.simple_spinner_dropdown_item, suburbs))
                        }
                        clearFocus()
                    }
                    viewModel?.mxStates?.value?.let { states ->
                        spState.setAdapter(ArrayAdapter(requireContext(),
                            android.R.layout.simple_spinner_dropdown_item, states.map { it.name }))
                    }
                    // FIXME: if first etCP is success, and after cbCP is selected, mxCities is empty
                    viewModel?.mxCities?.value?.let { cities ->
                        spCity.setAdapter(ArrayAdapter(requireContext(),
                            android.R.layout.simple_spinner_dropdown_item, cities))
                    }
                }
            }
        }
    }

    private fun setupButtons() {
        binding.btnQuote.setOnClickListener {
            sendQuote() // FIXME: first query only gets 1 result(qualitas), but changing coverage it gets 2
        }
    }

    private fun goToResult(quoteResponse: QuoteResponse) {
        navUtils.run {
            val args = Bundle()
            args.putParcelable(Constants.ARG_QUOTE_RESPONSE, quoteResponse)
            args.putParcelable(Constants.ARG_QUOTE_REQUEST, binding.viewModel?.quoteRequest)
            navController.navigate(actionQuoteToQuoteResult, args)
        }
    }

    private fun sendQuote() {
        with(binding){
            val isValid = validateFields(tilGender, tilBirthdate, tilType, tilBrand, tilYear, tilModel, tilCP, tilSuburb,
                if (cbCP.isChecked) tilState else null, if (cbCP.isChecked) tilCity else null)
            if (isValid) User.instance?.let { viewModel?.getQuote(it.getTokenMultiQuote(), it.email) }
        }
    }

    private fun validateFields(vararg textFields: TextInputLayout?): Boolean{
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

    override fun onStop() {
        super.onStop()
        binding.viewModel?.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ui.clearSnackbar()
        _binding = null
    }
}