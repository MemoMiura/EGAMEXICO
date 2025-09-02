package com.cursosant.insurance.homeModule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cursosant.insurance.common.utils.NavUtils
import com.cursosant.insurance.databinding.FragmentHomeBinding
import com.cursosant.insurance.homeModule.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.homeModule.view
 * Created by Alain Nicolás Tello on 05/07/23 at 19:13
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
open class HomeFragment : Fragment() {
    /*@Inject
    lateinit var utils: UiUtils*/
    @Inject
    lateinit var navUtils: NavUtils

    private var _binding: FragmentHomeBinding? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtons()
    }

    private fun setupButtons() {
        with(binding){
            btnAbout.setOnClickListener { navUtils.run { navController.navigate(actionToAbout) } }
            btnPolicies.setOnClickListener { navUtils.run { navController.navigate(actionToPolicies) } }
            btnInsurance.setOnClickListener { navUtils.run { navController.navigate(actionToInsurers) } }
            btnQuote.setOnClickListener { navUtils.run { navController.navigate(actionToQuote) } }
            btnCallEmergency.setOnClickListener { navUtils.run { navController.navigate(actionToCallEmergency) } }
            btnSinister.setOnClickListener { navUtils.run { navController.navigate(actionToSinister) } }
        }
    }

    protected fun setImgCover(imgRes: Int) {
        binding.imgCover.setImageResource(imgRes)
    }

    protected fun setBackgroundColor(colorRes: Int) {
        binding.containerMain.setBackgroundResource(colorRes)
    }

    protected fun showQuoter() {
        binding.btnQuote.visibility = View.VISIBLE
    }
}