package com.miurabox.ega.mexico

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.cursosant.insurance.R
import com.cursosant.insurance.common.utils.NavUtils
import com.cursosant.insurance.databinding.ActivityMainBinding
import com.cursosant.insurance.mainModule.view.MainActivity
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/****
 * Project: EGAMexico
 * From: com.miurabox.egamexico
 * Created by Iván Nicolás Tello on 31/08/23 at 08:15
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
class MainActivity : MainActivity() {
    @Inject lateinit var navUtils: NavUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        setupInit()
    }

    private fun setupInit() {
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        val graph = navController.navInflater.inflate(com.miurabox.ega.mexico.R.navigation.mobile_navigation_custom)

        with(navUtils) {
            this.navController = navController

            actionDeleteAccountToLogin = com.miurabox.ega.mexico.R.id.action_delete_account_to_login
            actionDocumentsToPdf =  com.miurabox.ega.mexico.R.id.action_documents_to_pdf
            actionPolicyDetailToPdf = com.miurabox.ega.mexico.R.id.action_policy_detail_to_pdf
            actionPoliciesToPolicyDetail = com.miurabox.ega.mexico.R.id.action_policies_to_policy_detail
            actionPolicyDetailToPdf = com.miurabox.ega.mexico.R.id.action_policy_detail_to_pdf
            actionSignoutToLogin = com.miurabox.ega.mexico.R.id.action_sign_out_to_login
            actionAboutToPrivacy = com.miurabox.ega.mexico.R.id.action_about_to_privacy
            actionLoginToRegister = com.miurabox.ega.mexico.R.id.action_login_to_register
            actionLoginToHome = com.miurabox.ega.mexico.R.id.action_login_to_home
            actionRegisterToLogin = com.miurabox.ega.mexico.R.id.action_register_to_login
            actionQuoteToQuoteResult = com.miurabox.ega.mexico.R.id.action_quote_to_quote_result
            actionQuoteResultToQuoteResultDetail = com.miurabox.ega.mexico.R.id.action_quote_result_to_quote_result_detail

            actionToAbout = com.miurabox.ega.mexico.R.id.action_to_about
            actionToPolicies= com.miurabox.ega.mexico.R.id.action_to_policies
            actionToInsurers = com.miurabox.ega.mexico.R.id.action_to_insurers
            actionToQuote = com.miurabox.ega.mexico.R.id.action_to_quote
            actionToCallEmergency = com.miurabox.ega.mexico.R.id.action_to_call_emergency
            actionToSinister = com.miurabox.ega.mexico.R.id.action_to_sinister
            actionToDocuments = com.miurabox.ega.mexico.R.id.action_to_documents
        }

        navView.inflateMenu(com.miurabox.ega.mexico.R.menu.activity_main_drawer_custom)
        graph.setStartDestination(R.id.nav_home)
        navController.setGraph(graph, null)
        navController.navigate(com.miurabox.ega.mexico.R.id.action_global_to_login)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_about, R.id.nav_policies, R.id.nav_quote, R.id.nav_insurers,
                com.miurabox.ega.mexico.R.id.nav_documents, R.id.nav_contact, R.id.nav_sign_out, R.id.nav_delete_account,
                R.id.nav_call_emergency, R.id.nav_sinister, R.id.nav_privacy
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        setImgCover(com.miurabox.ega.mexico.R.drawable.logo_p_hw)
    }
}