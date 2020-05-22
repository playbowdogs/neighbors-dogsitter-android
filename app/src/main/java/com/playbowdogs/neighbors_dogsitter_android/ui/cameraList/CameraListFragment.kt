package com.playbowdogs.neighbors_dogsitter_android.ui.cameraList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.playbowdogs.neighbors_dogsitter_android.R
import com.playbowdogs.neighbors_dogsitter_android.adapter.CameraListAdapter
import com.playbowdogs.neighbors_dogsitter_android.data.model.AccountCamerasModel
import com.playbowdogs.neighbors_dogsitter_android.data.model.ChosenCamera
import com.playbowdogs.neighbors_dogsitter_android.databinding.CameraListFragmentBinding
import com.playbowdogs.neighbors_dogsitter_android.utils.Status.*


class CameraListFragment : Fragment() {
    private lateinit var mBinding: CameraListFragmentBinding
    private lateinit var adapter: CameraListAdapter

    private lateinit var cameraListViewModel: CameraListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = CameraListFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupViewModel()
        setupUI()
        setupObservers()
    }

    private fun setupViewModel() {
        cameraListViewModel = ViewModelProvider(this).get(CameraListViewModel::class.java)
    }

    private fun setupUI() {
        mBinding.mainFragmentRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = CameraListAdapter(arrayListOf())
        mBinding.mainFragmentRecyclerView.adapter = adapter
    }

    private fun setupObservers() {
        mBinding.mainFragmentSwipeRefreshLayout.setOnRefreshListener {
            cameraListViewModel.getCamerasList()
            mBinding.mainFragmentSwipeRefreshLayout.isRefreshing = false
        }

         cameraListViewModel.getCamerasList().observe(requireActivity(), Observer {
            it?.let { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        mBinding.mainFragmentRecyclerView.visibility = View.VISIBLE
                        mBinding.progressView.visibility = View.GONE
                        resource.data?.let { accountCameras ->
                            if (accountCameras.results.size == 1) {
                                ChosenCamera(accountCameras.results[0])
                                findNavController().navigate(
                                    R.id.action_mainFragment_to_cameraDetails_true
                                )
                            } else {
                                retrieveList(accountCameras.results)
                            }
                        }
                    }
                    ERROR -> {
                        mBinding.mainFragmentRecyclerView.visibility = View.VISIBLE
                        mBinding.progressView.visibility = View.GONE
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
                    LOADING -> {
                        mBinding.progressView.visibility = View.VISIBLE
                        mBinding.mainFragmentRecyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(results: List<AccountCamerasModel.Result>) {
        adapter.apply {
            addUsers(results)
            notifyDataSetChanged()
        }
    }
}