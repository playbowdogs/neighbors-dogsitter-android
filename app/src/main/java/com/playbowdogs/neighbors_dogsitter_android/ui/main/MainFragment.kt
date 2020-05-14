package com.playbowdogs.neighbors_dogsitter_android.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.playbowdogs.neighbors_dogsitter_android.adapter.MainAdapter
import com.playbowdogs.neighbors_dogsitter_android.data.model.AccountCamerasModel
import com.playbowdogs.neighbors_dogsitter_android.databinding.MainFragmentBinding
import com.playbowdogs.neighbors_dogsitter_android.ui.SharedViewModel
import com.playbowdogs.neighbors_dogsitter_android.utils.Status.*


class MainFragment : Fragment() {
    private lateinit var mBinding: MainFragmentBinding
    private lateinit var adapter: MainAdapter

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = MainFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        mBinding.mainFragmentRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = MainAdapter(arrayListOf(), sharedViewModel)
        mBinding.mainFragmentRecyclerView.adapter = adapter
    }

    private fun setupObservers() {
        mBinding.mainFragmentSwipeRefreshLayout.setOnRefreshListener {
            sharedViewModel.getCameras()
            mBinding.mainFragmentSwipeRefreshLayout.isRefreshing = false
        }

//        mBinding.mainFragmentRecyclerView.setOnClickListener(Navigation.createNavigateOnClickListener(
//            R.id.action_mainFragment_to_cameraDetails
//        ))

        sharedViewModel.getCameras().observe(requireActivity(), Observer {
            it?.let { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        mBinding.mainFragmentRecyclerView.visibility = View.VISIBLE
                        mBinding.progressView.visibility = View.GONE
                        resource.data?.let { accountCameras -> retrieveList(accountCameras.results) }
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