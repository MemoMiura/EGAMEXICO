package com.cursosant.insurance.aboutModule.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cursosant.insurance.BR
import com.cursosant.insurance.common.utils.NavUtils
import com.cursosant.insurance.databinding.FragmentAboutBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class AboutFragment : Fragment() {
    @Inject lateinit var navUtils: NavUtils

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    private var webSite = ""//"http://miurabox.com/"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
    }

    private fun setupButtons() {
        with(binding) {
            btnVisit.setOnClickListener {
                val webSite = Intent(Intent.ACTION_VIEW, Uri.parse(website))
                requireActivity().startActivity(webSite)
            }
            btnPrivacy.setOnClickListener {
                goToPrivacy()
            }
        }
    }

    private fun goToPrivacy() {
        /*val navController = requireActivity().findNavController(R.id.nav_host_fragment_content_main)
        navController.navigate(R.id.action_about_to_privacy)*/
        navUtils.run { navController.navigate(actionAboutToPrivacy) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun setImgCover(imgRes: Int) {
        binding.imgCover.setImageResource(imgRes)
    }

    protected fun setWebsite(url: String) {
        webSite = url
        binding.setVariable(BR.website, webSite)
    }

    protected fun setAboutDescription(descriptionRes: Int) {
        binding.tvAbout.setText(descriptionRes)
    }
}