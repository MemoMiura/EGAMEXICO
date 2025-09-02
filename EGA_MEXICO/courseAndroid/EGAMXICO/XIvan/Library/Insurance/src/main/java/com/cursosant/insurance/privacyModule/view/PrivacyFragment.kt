package com.cursosant.insurance.privacyModule.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cursosant.insurance.common.utils.UiUtils
import com.cursosant.insurance.databinding.FragmentPrivacyBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.privacyModule.view
 * Created by Alain Nicolás Tello on 02/06/23 at 9:31
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
open class PrivacyFragment : Fragment() {

    @Inject
    lateinit var utils: UiUtils

    private var _binding: FragmentPrivacyBinding? = null
    private val binding get() = _binding!!

    private var website = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrivacyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtons()
    }

    private fun setupButtons() {
        binding.tvLink.setOnClickListener {
            if (website.isNotBlank()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(website))
                requireActivity().startActivity(intent)
                /*if (intent.resolveActivity(requireActivity().packageManager) != null) {
                    startActivity(intent)
                } else { // TODO: unificar launch intent con ContactFragment en utils
                    utils.snackbarLong(binding.root, R.string.privacy_website_error)
                }*/
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun setWebsite(website: String) {
        this.website = website
    }

    protected fun setPrivacyPolicies(policiesRes: Int) {
        binding.tvPrivacyPolicies.setText(policiesRes)
    }
}