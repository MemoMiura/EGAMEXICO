package com.cursosant.insurance.policiesModule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.cursosant.insurance.BR
import com.cursosant.insurance.R
import com.cursosant.insurance.common.entities.Policy
import com.cursosant.insurance.common.entities.User
import com.cursosant.insurance.common.utils.Constants
import com.cursosant.insurance.common.utils.NavUtils
import com.cursosant.insurance.common.utils.UiUtils
import com.cursosant.insurance.databinding.FragmentPoliciesBinding
import com.cursosant.insurance.policiesModule.model.PoliciesPagingSource
import com.cursosant.insurance.policiesModule.view.adapters.OnClickListener
import com.cursosant.insurance.policiesModule.view.adapters.PolicyAdapter
import com.cursosant.insurance.policiesModule.viewModel.PoliciesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PoliciesFragment : Fragment(), OnClickListener {

    private var _binding: FragmentPoliciesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PoliciesViewModel by viewModels()

    @Inject lateinit var adapter: PolicyAdapter
    @Inject lateinit var utils: UiUtils
    @Inject lateinit var navUtils: NavUtils

    private var lastKnownPage = PoliciesPagingSource.FIRST_PAGE

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
        binding.setVariable(BR.viewModel, viewModel)
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = this@PoliciesFragment.adapter
        }.also { adapter.setOnClickListener(this) }
    }

    private fun setupObservers() {
        viewModel.run {
            snackbarMsg.observe(viewLifecycleOwner) { resMsg ->
                utils.snackbarLong(binding.root, resMsg)
            }
            policies.observe(viewLifecycleOwner) { result ->
                adapter.submitData(viewLifecycleOwner.lifecycle, result)
            }
            paginationState.observe(viewLifecycleOwner) { state ->
                binding.btnPrevious.isEnabled = state.hasPrevious
                binding.btnNext.isEnabled = state.hasNext
                binding.paginationSummary.text = getString(
                    R.string.policies_pagination_summary,
                    state.currentPage,
                    state.totalPages,
                    state.totalCount
                )

                if (lastKnownPage != state.currentPage) {
                    lastKnownPage = state.currentPage
                    binding.recyclerView.scrollToPosition(0)
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    adapter.loadStateFlow.collectLatest { loadState ->
                        val refreshState = loadState.refresh
                        updateLoading(refreshState is LoadState.Loading)

                        if (refreshState is LoadState.Loading) {
                            setPoliciesEmpty(false)
                        }

                        val errorState = refreshState as? LoadState.Error
                            ?: loadState.append as? LoadState.Error
                            ?: loadState.prepend as? LoadState.Error

                        if (errorState != null) {
                            if (adapter.itemCount == 0) {
                                setPoliciesEmpty(true)
                                binding.retryContainer.msg = getString(R.string.policies_error)
                            }
                            notifyPoliciesError()
                        } else {
                            val isListEmpty = refreshState is LoadState.NotLoading && adapter.itemCount == 0
                            setPoliciesEmpty(isListEmpty)
                            if (isListEmpty) {
                                binding.retryContainer.msg = getString(R.string.policies_empty_msg)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupButtons() {
        binding.retryContainer.btnRetry.setOnClickListener { adapter.retry() }
        binding.btnPrevious.setOnClickListener { viewModel.goToPreviousPage() }
        binding.btnNext.setOnClickListener { viewModel.goToNextPage() }
    }

    override fun onResume() {
        super.onResume()
        getPolicies()
    }

    private fun getPolicies() {
        User.instance?.let { viewModel.getPolicies(it.token.token) }
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