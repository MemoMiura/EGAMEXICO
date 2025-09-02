package com.cursosant.insurance.mainModule.view

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.cursosant.insurance.BR
import com.cursosant.insurance.R
import com.cursosant.insurance.common.entities.User
import com.cursosant.insurance.databinding.ActivityMainBinding
import com.cursosant.insurance.databinding.NavHeaderMainBinding
import com.cursosant.insurance.mainModule.viewModel.MainViewModel
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.badge.ExperimentalBadgeUtils
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

/****
 * Project: Insurance
 * From: com.cursosant.insurance.mainModule.view
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
open class MainActivity : AppCompatActivity() {

    protected lateinit var appBarConfiguration: AppBarConfiguration
    protected lateinit var binding: ActivityMainBinding
    private lateinit var headerBinding: NavHeaderMainBinding

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            Toast.makeText(this,
                getString(R.string.main_notifications_permissions_denied), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //setupInit()
        super.onCreate(savedInstanceState)
        // FIXME: enable to implement notifications by default
        //askNotificationPermission()
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
                MaterialAlertDialogBuilder(this)
                    .setMessage(R.string.main_notifications_permissions_explaining)
                    .setPositiveButton(R.string.main_notifications_permissions_dialog_accept){ _, _ ->
                        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                    .setNegativeButton(R.string.main_notifications_permissions_dialog_cancel, null)
                    .show()
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun setupInit() {
        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        //navController.navigate(R.id.action_global_to_login)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_about, R.id.nav_policies, R.id.nav_quote, R.id.nav_insurers,
                R.id.nav_docSubramos, R.id.nav_contact, R.id.nav_sign_out, R.id.nav_delete_account,
                R.id.nav_call_emergency, R.id.nav_sinister, R.id.nav_privacy
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    protected fun setupViewModel() {
        val vm: MainViewModel by viewModels()
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, vm)
    }

    protected fun setupObservers() {
        binding.viewModel?.let { vm ->
            vm.isLogin.observe(this) { result ->
                if (result) {
                    User.instance?.let { user ->
                        setUserHeader(user.first_name, user.last_name, user.email)
                    }
                }
            }
        }
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }*/
    private fun inflateHeader() {
        if (!::headerBinding.isInitialized) {
            headerBinding = NavHeaderMainBinding.bind(binding.navView.getHeaderView(0))
        }
    }

    // TODO: move method to interface?
    protected fun setImgCover(imgRes: Int) {
        inflateHeader()
        //val headerBinding = NavHeaderMainBinding.bind(binding.navView.getHeaderView(0))
        headerBinding.imgCover.setImageResource(imgRes)
    }

    private fun setUserHeader(firstName: String, lastName: String, email: String) {
        inflateHeader()
        headerBinding.tvName.visibility = View.VISIBLE
        headerBinding.tvEmail.visibility = View.VISIBLE
        headerBinding.tvName.text = getString(R.string.main_drawer_header_full_name, firstName, lastName)
        headerBinding.tvEmail.text = email
    }
}