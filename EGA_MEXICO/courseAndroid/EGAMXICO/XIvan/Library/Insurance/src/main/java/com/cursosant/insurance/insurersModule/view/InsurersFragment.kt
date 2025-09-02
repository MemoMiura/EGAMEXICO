package com.cursosant.insurance.insurersModule.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cursosant.insurance.BR
import com.cursosant.insurance.R
import com.cursosant.insurance.common.entities.Insurance
import com.cursosant.insurance.common.entities.User
import com.cursosant.insurance.common.utils.Constants
import com.cursosant.insurance.common.utils.UiUtils
import com.cursosant.insurance.databinding.FragmentInsurersBinding
import com.cursosant.insurance.insurersModule.view.adapters.InsuranceAdapter
import com.cursosant.insurance.insurersModule.view.adapters.OnClickListener
import com.cursosant.insurance.insurersModule.viewModel.InsurersViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.insurersModule.view
 * Created by Alain Nicolás Tello on 02/06/23 at 18:54
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
open class InsurersFragment : Fragment(), OnClickListener {
    private var _binding: FragmentInsurersBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var adapter: InsuranceAdapter
    @Inject
    lateinit var utils: UiUtils

    private var isAncora = false
    //private var subramoId = -1L
    protected var subramoCode = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            isAncora = requireArguments().getBoolean(Constants.ARG_IS_ANCORA, false)
            subramoCode = requireArguments().getInt(Constants.ARG_CODE, -1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInsurersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupViewModel() {
        val vm: InsurersViewModel by viewModels()
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, vm)
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = this@InsurersFragment.adapter
        }.also { adapter.setOnClickListener(this) }
    }

    private fun setupObservers() {
        binding.viewModel?.let { vm ->
            vm.snackbarMsg.observe(viewLifecycleOwner) { resMsg ->
                utils.snackbarLong(binding.root, resMsg)
            }
            vm.insurers.observe(viewLifecycleOwner) { result ->
                adapter.submitList(result)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isAncora) {
            User.instance?.let { binding.viewModel?.getInsurersBySubramoCode(subramoCode) }
        } else {
            User.instance?.let { binding.viewModel?.getInsurers(it.token.token, it.username) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*
    * OnClickListener
    * */
    override fun onClick(insurance: Insurance) {
        if (insurance.website.isNullOrBlank()) {
            utils.snackbarAction(binding.root, getString(R.string.insurers_empty_website),
                R.string.snackbar_ok){}
        } else {
            if (insurance.website.lowercase().contains(Constants.PREFIX_WEBSITE)){
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(insurance.website))
                startActivity(browserIntent)
            } else {
                utils.snackbarAction(binding.root, getString(R.string.insurers_invalid_website),
                    R.string.snackbar_ok) {}
            }
        }
    }
}