package com.cursosant.insurance.documentsModule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cursosant.insurance.BR
import com.cursosant.insurance.R
import com.cursosant.insurance.common.entities.Document
import com.cursosant.insurance.common.entities.User
import com.cursosant.insurance.common.utils.Constants
import com.cursosant.insurance.common.utils.NavUtils
import com.cursosant.insurance.common.utils.UiUtils
import com.cursosant.insurance.databinding.FragmentDocumentsBinding
import com.cursosant.insurance.documentsModule.view.adapters.DocumentAdapter
import com.cursosant.insurance.documentsModule.view.adapters.OnClickListener
import com.cursosant.insurance.documentsModule.viewModel.DocumentsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.documentsModule.view
 * Created by Alain Nicolás Tello on 07/06/23 at 15:11
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
open class DocumentsFragment : Fragment(), OnClickListener{
    @Inject
    lateinit var utils: UiUtils
    @Inject
    lateinit var navUtils: NavUtils
    @Inject
    lateinit var adapter: DocumentAdapter

    private var _binding: FragmentDocumentsBinding? = null
    private val binding get() = _binding!!

    private var isAncora = false
    private var insuranceId = -1L
    private var subramoCode = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*isAncora = requireArguments().getBoolean(Constants.ARG_IS_ANCORA, false)
        insuranceId = requireArguments().getLong(Constants.ARG_ID, -1)*/
        arguments?.let {
            isAncora = it.getBoolean(Constants.ARG_IS_ANCORA, false)
            insuranceId = it.getLong(Constants.ARG_ID, -1)
            subramoCode = it.getInt(Constants.ARG_CODE, -1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDocumentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupRecyclerViews()
        setupObservers()
    }

    private fun setupViewModel() {
        val vm: DocumentsViewModel by viewModels()
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, vm)
    }

    private fun setupObservers() {
        binding.viewModel?.let { vm ->
            vm.snackbarMsg.observe(viewLifecycleOwner) { resMsg ->
                utils.snackbarLong(binding.root, resMsg)
            }
            vm.documentResponse.observe(viewLifecycleOwner) { result ->
                adapter.submitList(result.results)
            }
        }
    }

    private fun setupRecyclerViews() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = this@DocumentsFragment.adapter
        }.also { adapter.setOnClickListener(this) }
    }

    override fun onResume() {
        super.onResume()
        if (isAncora) {
            User.instance?.let { binding.viewModel?.getDocumentsByInsuranceId(insuranceId, subramoCode) }
        } else {
            User.instance?.let { binding.viewModel?.getDocuments(it.token.token) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    /*
    * OnClickListener
    * */
    override fun onClick(document: Document) {
        /*val navController = requireActivity().findNavController(R.id.nav_host_fragment_content_main)
        val action = DocumentsFragmentDirections.actionDocumentsToPdf()
        action.name = document.nombre
        action.url = document.arch
        navController.navigate(action)*/
        navUtils.run {
            val args = Bundle()
            args.putString(Constants.ARG_NAME, document.nombre)
            args.putString(Constants.ARG_URL, document.arch)
            navController.navigate(actionDocumentsToPdf, args)
        }
    }
}