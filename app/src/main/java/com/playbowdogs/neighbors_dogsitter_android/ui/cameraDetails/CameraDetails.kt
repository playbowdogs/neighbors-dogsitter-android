package com.playbowdogs.neighbors_dogsitter_android.ui.cameraDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.playbowdogs.neighbors_dogsitter_android.R
import com.playbowdogs.neighbors_dogsitter_android.data.model.AccountCamerasModel
import com.playbowdogs.neighbors_dogsitter_android.data.model.ChosenCamera
import com.playbowdogs.neighbors_dogsitter_android.data.model.GetRecordingInfoModel
import com.playbowdogs.neighbors_dogsitter_android.databinding.CameraDetailsFragmentBinding
import com.playbowdogs.neighbors_dogsitter_android.utils.Resource
import com.playbowdogs.neighbors_dogsitter_android.utils.Status

class CameraDetails : Fragment() {
    private lateinit var viewModel: CameraDetailsViewModel
    private lateinit var mBinding: CameraDetailsFragmentBinding
    private val chosenCamera = ChosenCamera.value
    val args: CameraDetailsArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.explode)
        mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.camera_details_fragment, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setOnBackPressedCallback()
        setViewModels()
        setObservers()
        setOnClickListeners()
        mBinding.model = chosenCamera
        viewModel.getCameraInfo()
    }

    private fun setOnBackPressedCallback() {
        if (args.isSingleCam) {
            val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
                requireActivity().finish()
            }
            callback.isEnabled = true
        }
    }

    private fun setViewModels() {
        viewModel = ViewModelProvider(this).get(CameraDetailsViewModel::class.java)
    }

    private fun setObservers() {

        viewModel.cameraStatus.observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        mBinding.progressViewDetails.visibility = View.GONE
                        setVideoView(resource)
                    }
                    Status.ERROR -> {
                        mBinding.progressViewDetails.visibility = View.GONE
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
//                        mBinding.progressViewDetails.visibility = View.VISIBLE
                    }
                }
            }
        })

        viewModel.cameraRecordingInfo.observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        mBinding.progressViewDetails.visibility = View.GONE
                        setRecordingButtons(resource)
                    }
                    Status.ERROR -> {
                        mBinding.progressViewDetails.visibility = View.GONE
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
//                        mBinding.progressViewDetails.visibility = View.VISIBLE

                    }
                }
            }
        })
    }

    private fun setOnClickListeners() {

        mBinding.cameraDetailsFragmentRecordStart.setOnClickListener {
            viewModel.startRecording()
        }

        mBinding.cameraDetailsFragmentRecordStop.setOnClickListener {
            viewModel.stopRecording()
        }

        mBinding.cameraDetailsFragmentLibrary.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_cameraDetails_to_recordedClipsListFragment))
    }

    private fun setVideoView(resource: Resource<AccountCamerasModel.Result>) {
        if (resource.data?.status == "online") {
            mBinding.detailCamFragmentVideoView.setVideoURI(resource.data.streams[2].url.toUri())
            mBinding.detailCamFragmentVideoView.setOnPreparedListener { mediaPlayer ->
                mediaPlayer.start()
                mBinding.detailCamFragmentVideoView.alpha = 1f

            }
        }
    }

    private fun setRecordingButtons(resource: Resource<GetRecordingInfoModel>) {
        when (resource.data?.status) {
            "RECORDING" -> {
                mBinding.cameraDetailsFragmentRecordStop.visibility = View.VISIBLE
                mBinding.cameraDetailsFragmentRecordStopText.visibility = View.VISIBLE

                mBinding.cameraDetailsFragmentRecordStart.visibility = View.INVISIBLE
                mBinding.cameraDetailsFragmentRecordStartText.visibility = View.INVISIBLE
            }
            "READY" -> {
                mBinding.cameraDetailsFragmentRecordStop.visibility = View.INVISIBLE
                mBinding.cameraDetailsFragmentRecordStopText.visibility = View.INVISIBLE

                mBinding.cameraDetailsFragmentRecordStart.visibility = View.VISIBLE
                mBinding.cameraDetailsFragmentRecordStartText.visibility = View.VISIBLE
            }
            "CONNECTING" -> {
                viewModel.getRecordingInfo()
            }
        }
    }
}