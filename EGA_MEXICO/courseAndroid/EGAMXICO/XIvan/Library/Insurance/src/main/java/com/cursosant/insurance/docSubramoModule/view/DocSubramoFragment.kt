package com.cursosant.insurance.docSubramoModule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cursosant.insurance.BR
import com.cursosant.insurance.common.entities.Subramo
import com.cursosant.insurance.common.entities.User
import com.cursosant.insurance.common.utils.Constants
import com.cursosant.insurance.common.utils.NavUtils
import com.cursosant.insurance.common.utils.UiUtils
import com.cursosant.insurance.databinding.FragmentDocSubramoBinding
import com.cursosant.insurance.databinding.FragmentDocumentsBinding
import com.cursosant.insurance.docSubramoModule.view.adapters.OnClickListener
import com.cursosant.insurance.docSubramoModule.view.adapters.SubramosAdapter
import com.cursosant.insurance.docSubramoModule.viewModel.DocSubramoViewModel
import com.cursosant.insurance.documentsModule.viewModel.DocumentsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.docSubramoModule.view
 * Created by Alain Nicolás Tello on 23/11/23 at 14
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
class DocSubramoFragment : Fragment(), OnClickListener {
    @Inject
    lateinit var utils: UiUtils
    @Inject
    lateinit var navUtils: NavUtils
    @Inject
    lateinit var adapter: SubramosAdapter

    private var _binding: FragmentDocSubramoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDocSubramoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupRecyclerViews()
        setupButtons()
        setupObservers()
    }

    private fun setupViewModel() {
        val vm: DocSubramoViewModel by viewModels()
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, vm)
    }

    private fun setupObservers() {
        binding.viewModel?.let { vm ->
            vm.snackbarMsg.observe(viewLifecycleOwner) { resMsg ->
                utils.snackbarLong(binding.root, resMsg)
            }
            vm.subramos.observe(viewLifecycleOwner) { result ->
                adapter.submitList(result)
            }
        }
    }

    private fun setupRecyclerViews() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = this@DocSubramoFragment.adapter
        }.also { adapter.setOnClickListener(this) }
    }

    private fun setupButtons() {
        binding.retryContainer.btnRetry.setOnClickListener { getSubramos() }
    }

    override fun onResume() {
        super.onResume()
        getSubramos()
    }

    private fun getSubramos() {
        User.instance?.let { binding.viewModel?.getSubramosByUsername(it.token.token, it.username) }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    /*
    * OnClickListener
    * */
    override fun onClick(subramo: Subramo) {
        navUtils.run {
            val args = Bundle()
            args.putBoolean(Constants.ARG_IS_ANCORA, true)
            args.putLong(Constants.ARG_ID, subramo.id)
            args.putInt(Constants.ARG_CODE, subramo.subramo_code)
            navController.navigate(actionDocSubramosToDocInsurers, args)
        }
    }
}