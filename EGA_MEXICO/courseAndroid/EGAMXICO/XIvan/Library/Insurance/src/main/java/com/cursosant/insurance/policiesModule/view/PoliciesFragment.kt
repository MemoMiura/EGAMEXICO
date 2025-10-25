package com.cursosant.insurance.policiesModule.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cursosant.insurance.BR
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
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PoliciesFragment : Fragment(), OnClickListener {

    private var _binding: FragmentPoliciesBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var adapter: PolicyAdapter
    @Inject lateinit var utils: UiUtils
    @Inject lateinit var navUtils: NavUtils

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
        setupAdsIfPresent()
        setupObservers()
    }

    private fun setupAdsIfPresent() {
        val adView = binding.root.findViewById<View?>(R.id.adView) ?: return
        // Access the Google Mobile Ads SDK via reflection so the library can run
        // even when the dependency is missing from the hosting application.
        runCatching {
            initializeAdView(requireContext(), adView)
        }.onFailure { error ->
            hideAdView(adView)
            Log.w(TAG, "Unable to initialize ads for policies screen", error)
        }
    }

    private fun initializeAdView(context: Context, adView: View) {
        val adViewClass = Class.forName("com.google.android.gms.ads.AdView")
        if (!adViewClass.isInstance(adView)) {
            throw IllegalStateException(
                "View bound to @id/adView is not an AdView: ${adView.javaClass.name}"
            )
        }

        val mobileAdsClass = Class.forName("com.google.android.gms.ads.MobileAds")
        runCatching {
            mobileAdsClass.getMethod("initialize", Context::class.java).invoke(null, context)
        }.recoverCatching {
            val listenerClass = Class.forName(
                "com.google.android.gms.ads.initialization.OnInitializationCompleteListener"
            )
            mobileAdsClass
                .getMethod("initialize", Context::class.java, listenerClass)
                .invoke(null, context, null)
        }.getOrThrow()

        val adRequestBuilderClass = Class.forName("com.google.android.gms.ads.AdRequest\$Builder")
        val builder = adRequestBuilderClass.getDeclaredConstructor().newInstance()
        val adRequest = adRequestBuilderClass.getMethod("build").invoke(builder)

        val adRequestClass = Class.forName("com.google.android.gms.ads.AdRequest")
        adViewClass.getMethod("loadAd", adRequestClass).invoke(adView, adRequest)
    }

    private fun hideAdView(adView: View) {
        adView.visibility = View.GONE
    }

    private fun setupViewModel() {
        val vm: PoliciesViewModel by viewModels()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.setVariable(BR.viewModel, vm)
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = this@PoliciesFragment.adapter
        }.also { adapter.setOnClickListener(this) }
    }

    private fun setupObservers() {
        binding.viewModel?.let { vm ->
            vm.snackbarMsg.observe(viewLifecycleOwner) { resMsg ->
                utils.snackbarLong(binding.root, resMsg)
            }
            vm.policies.observe(viewLifecycleOwner) { result ->
                adapter.submitList(result)
            }
        }
    }

    private fun setupButtons() {
        binding.retryContainer.btnRetry.setOnClickListener { getPolicies() }
        binding.btnNext.setOnClickListener { binding.viewModel?.loadNextPage() }
        binding.btnPrevious.setOnClickListener { binding.viewModel?.loadPreviousPage() }
    }

    override fun onResume() {
        super.onResume()
        getPolicies()
    }

    private fun getPolicies() {
        User.instance?.let { binding.viewModel?.loadFirstPage(it.token.token) }
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

    private companion object {
        private const val TAG = "PoliciesFragment"
    }
}