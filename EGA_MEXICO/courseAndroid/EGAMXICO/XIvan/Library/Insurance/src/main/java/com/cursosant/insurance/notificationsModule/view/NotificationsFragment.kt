package com.cursosant.insurance.notificationsModule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cursosant.insurance.BR
import com.cursosant.insurance.common.entities.Notification
import com.cursosant.insurance.common.entities.User
import com.cursosant.insurance.common.utils.Constants
import com.cursosant.insurance.common.utils.NavUtils
import com.cursosant.insurance.common.utils.UiUtils
import com.cursosant.insurance.databinding.FragmentNotificationsBinding
import com.cursosant.insurance.notificationsModule.view.adapter.NotificationAdapter
import com.cursosant.insurance.notificationsModule.view.adapter.OnClickListener
import com.cursosant.insurance.notificationsModule.viewModel.NotificationsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.notificationsModule.view.adapter
 * Created by Alain Nicolás Tello on 05/09/23 at 11:20
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
class NotificationsFragment : Fragment(), OnClickListener{

    @Inject
    lateinit var uiUtils: UiUtils
    @Inject
    lateinit var navUtils: NavUtils
    @Inject
    lateinit var adapter: NotificationAdapter

            private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
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
        val vm: NotificationsViewModel by viewModels()
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, vm)
    }

    private fun setupObservers() {
        binding.viewModel?.let { vm ->
            vm.snackbarMsg.observe(viewLifecycleOwner) { resMsg ->
                uiUtils.snackbarLong(binding.root, resMsg)
            }
            vm.snackbarWarning.observe(viewLifecycleOwner) { resMsg ->
                uiUtils.snackbarWarning(binding.root, resMsg)
            }
            vm.notifications.observe(viewLifecycleOwner) { result ->
                binding.viewModel?.updatePreferences(false)
                adapter.submitList(result)
            }
        }
    }

    private fun setupRecyclerViews() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = this@NotificationsFragment.adapter
        }.also { adapter.setOnClickListener(this) }
    }

    private fun setupButtons() {
        binding.retryContainer.btnRetry.setOnClickListener { getNotifications() }
    }

    private fun getNotifications() {
        User.instance?.let { binding.viewModel?.getNotificationsByUser(it.token.token, it.username) }
    }

    override fun onResume() {
        super.onResume()
        getNotifications()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        _binding = null
    }

    override fun onClick(notification: Notification) {
        navUtils.run {
            val args = Bundle()
            args.putString(Constants.ARG_TITLE, notification.title)
            args.putString(Constants.ARG_DESCRIPTION, notification.description)
            args.putString(Constants.ARG_URL, notification.site)
            args.putString(Constants.ARG_IMG_URL, notification.getImgUrl())
            navController.navigate(actionNotificationsToNotificationDetail, args)
        }
    }
}