package com.cursosant.insurance.notificationDetailModule.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cursosant.insurance.BR
import com.cursosant.insurance.R
import com.cursosant.insurance.common.entities.FileImage
import com.cursosant.insurance.common.entities.Notification
import com.cursosant.insurance.common.utils.Constants
import com.cursosant.insurance.common.utils.UiUtils
import com.cursosant.insurance.databinding.FragmentNotificationDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.notificationDetailModule.view
 * Created by Alain Nicolás Tello on 07/12/23 at 13:44
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
class NotificationDetailFragment : Fragment() {
    @Inject
    lateinit var uiUtils: UiUtils

    private var _binding: FragmentNotificationDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var notification: Notification

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val fileImage = FileImage(0, "",
                it.getString(Constants.ARG_IMG_URL, ""))
            notification = Notification(0,
                it.getString(Constants.ARG_TITLE, ""),
                it.getString(Constants.ARG_DESCRIPTION, ""),
                it.getString(Constants.ARG_URL, ""),
                listOf(fileImage))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNotificationDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupButtons()
        setupObservers()
    }

    private fun setupViewModel() {
        /*val vm: NotificationsViewModel by viewModels()
        binding.lifecycleOwner = this*/
        binding.setVariable(BR.notification, notification)
    }

    private fun setupObservers() {
        /*binding.viewModel?.let { vm ->
            vm.snackbarMsg.observe(viewLifecycleOwner) { resMsg ->
                uiUtils.snackbarLong(binding.root, resMsg)
            }
        }*/
    }

    private fun setupButtons() {
        binding.btnUrl.setOnClickListener {
            val site = notification.site?.lowercase() ?: ""
            if (site.contains("http")) {
                val webSite = Intent(Intent.ACTION_VIEW, Uri.parse(site))
                requireActivity().startActivity(webSite)
            } else {
                uiUtils.snackbarLong(binding.root, R.string.notification_detail_url_invalid)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //(requireActivity() as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        _binding = null
    }
}