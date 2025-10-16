package com.cursosant.insurance.policiesModule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cursosant.insurance.R
import com.cursosant.insurance.common.entities.Policy
import com.cursosant.insurance.common.entities.User
import com.cursosant.insurance.common.utils.Constants
import com.cursosant.insurance.common.utils.NavUtils
import com.cursosant.insurance.common.utils.UiUtils
import com.cursosant.insurance.databinding.FragmentPoliciesBinding
import com.cursosant.insurance.policiesModule.view.adapters.OnClickListener
import com.cursosant.insurance.policiesModule.view.adapters.PolicyAdapter
import com.cursosant.insurance.policiesModule.viewModel.PoliciesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PoliciesFragment : Fragment(), OnClickListener {

    private var _binding: FragmentPoliciesBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var adapter: PolicyAdapter
    @Inject lateinit var utils: UiUtils
    @Inject lateinit var navUtils: NavUtils

    private val viewModel: PoliciesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPoliciesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRecyclerView()
        setupButtons()
        binding.retryContainer.msg = getString(R.string.policies_empty_msg)
        setupObservers()
    }

    private fun setupViewModel() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = this@PoliciesFragment.adapter
        }.also { adapter.setOnClickListener(this) }
    }

    private fun setupObservers() {
        viewModel.snackbarMsg.observe(viewLifecycleOwner) { resMsg ->
            utils.snackbarLong(binding.root, resMsg)
        }
        viewModel.policies.observe(viewLifecycleOwner) { result ->
            adapter.submitData(viewLifecycleOwner.lifecycle, result)
        }
        viewModel.emptyStateMessage.observe(viewLifecycleOwner) { resId ->
            binding.retryContainer.msg = getString(resId)
        }
    }

    private fun setupButtons() {
        binding.retryContainer.btnRetry.setOnClickListener {
            viewModel.refreshCurrentPage()
        }
    }

    override fun onResume() {
        super.onResume()
        getPolicies()
    }

    private fun getPolicies() {
        val user = User.instance
        viewModel.onSessionAvailable(user?.token?.token, user?.username)
        viewModel.refreshCurrentPage()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*
    * OnClickListener
    * */
    override fun onClick(policy: Policy) {
        /*val navController = requireActivity().findNavController(R.id.nav_host_fragment_content_main)
        val action = PoliciesFragmentDirections.actionPoliciesToPolicyDetail()
        action.idPolicy = policy.id
        navController.navigate(action)*/
        navUtils.run {
            val args = Bundle()
            args.putLong(Constants.ARG_ID, policy.id)
            args.putString(Constants.ARG_POLICY_SUBRAMO, policy.subramo)
            navController.navigate(actionPoliciesToPolicyDetail, args)
        }
    }
}