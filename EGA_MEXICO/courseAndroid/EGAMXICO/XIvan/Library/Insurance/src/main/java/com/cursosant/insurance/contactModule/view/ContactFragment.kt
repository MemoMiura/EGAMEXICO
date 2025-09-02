package com.cursosant.insurance.contactModule.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cursosant.insurance.BR
import com.cursosant.insurance.R
import com.cursosant.insurance.common.entities.User
import com.cursosant.insurance.common.utils.NavUtils
import com.cursosant.insurance.common.utils.UiUtils
import com.cursosant.insurance.contactModule.viewModel.ContactViewModel
import com.cursosant.insurance.databinding.FragmentContactBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.contactModule.view
 * Created by Alain Nicolás Tello on 08/06/23 at 15:04
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
open class ContactFragment : Fragment(){//, OnMapReadyCallback {
    @Inject
    lateinit var utils: UiUtils
    @Inject
    lateinit var navUtils: NavUtils

    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!

    private var lat = 25.660076996672995
    private var long = -100.37460701349325

    private var isAncora = false

    /*private lateinit var map: GoogleMap

    private var mapFragment: SupportMapFragment? = null*/

    // TODO: Q: se necesita en algún momento saber la úbicación del usuario? O solo es ubicar las oficinas
    //todo: login_token() original: "Usuario y/o contraseña invalidos'NoneType' object has no attribute 'urlname'"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentContactBinding.inflate(inflater, container, false)

        /*mapFragment = this.childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)*/

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupUserData()
        setupButtons()
        setupMap()
        setupObservers()
    }

    private fun setupViewModel() {
        val vm: ContactViewModel by viewModels()
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, vm)
    }

    private fun setupObservers() {
        binding.viewModel?.let { vm ->
            vm.snackbarMsg.observe(viewLifecycleOwner) { resMsg ->
                utils.snackbarLong(binding.root, resMsg)
            }
        }
    }

    private fun setupUserData() {
        with(binding){
            User.instance?.let { user ->
                etName.setText(user.first_name)
                etEmail.setText(user.email)
            }
        }
    }

    private fun setupButtons(){
        with(binding){
            // TODO: origin project doesn't work in this action, success response no verified
            btnSend.setOnClickListener {
                if (isAncora) {
                    navUtils.run { navController.navigate(actionToTickets) }
                } else {
                    User.instance?.let { user ->
                        viewModel?.sendMessage(user.token.token, etName.text.toString().trim(),
                            etEmail.text.toString().trim(), etMsg.text.toString().trim())
                    }
                }

            }
        }
    }

    private fun setupMap() {
        binding.imgMap.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("geo:0,0?q=$lat, $long" +
                        "(${getString(R.string.contact_offices)})")
                `package` = "com.google.android.apps.maps"
            }
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            } else {
                utils.snackbarLong(binding.root, R.string.contact_map_error)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        /*mapFragment?.let {
            this.childFragmentManager.beginTransaction().remove(it).commit()
        }*/
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    /*
    * OnMapReadyCallback
    * */
    /*override fun onMapReady(googleMap: GoogleMap) {
        val location = LatLng(25.660076996672995, -100.37460701349325)
        map = googleMap
        map.addMarker(MarkerOptions()
                .position(location)
                .title(getString(R.string.contact_offices))
        )?.showInfoWindow()

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17f))
        map.addMarker(MarkerOptions().position(location))
    }*/

    protected fun enableAncora() {
        isAncora = true
        with(binding) {
            tilEmail.visibility = View.GONE
            tilName.visibility = View.GONE
            tilMsg.visibility = View.GONE
        }
    }

    protected fun setImgCover(imgRes: Int) {
        binding.imgMap.setImageResource(imgRes)
    }

    protected fun setAddress(address: String) {
        binding.setVariable(BR.address, address)
    }

    protected fun setLatLong(lat: Double, long: Double) {
        this.lat = lat
        this.long = long
    }
}