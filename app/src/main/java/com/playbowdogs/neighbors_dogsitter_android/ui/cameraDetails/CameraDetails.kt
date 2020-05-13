package com.playbowdogs.neighbors_dogsitter_android.ui.cameraDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionInflater
import com.playbowdogs.neighbors_dogsitter_android.R
import com.playbowdogs.neighbors_dogsitter_android.databinding.CameraDetailsFragmentBinding
import com.playbowdogs.neighbors_dogsitter_android.ui.SharedViewModel

class CameraDetails : Fragment() {

    companion object {
        fun newInstance() =
            CameraDetails()
    }

    private lateinit var viewModel: CameraDetailsViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var mBinding: CameraDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.explode)
        mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.camera_details_fragment, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CameraDetailsViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        // TODO: Use the ViewModel

        setObservers()
    }

    private fun setObservers() {
        sharedViewModel.chosenResult.observe(requireActivity(), Observer {
            it?.let { chosenResult ->
                mBinding.model = chosenResult
                mBinding.detailCamFragmentVideoView.setVideoURI(chosenResult.streams[2].url.toUri())
                mBinding.detailCamFragmentVideoView.setOnPreparedListener { mediaPlayer ->
                    Log.e("VideoView", "should be visible")
                    mBinding.detailCamFragmentVideoView.alpha = 1f
                    mediaPlayer.start()
                }
            }
        })
    }

}