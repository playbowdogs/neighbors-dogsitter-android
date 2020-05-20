package com.playbowdogs.neighbors_dogsitter_android.ui.recordedClipsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.playbowdogs.neighbors_dogsitter_android.adapter.RecordedClipsListAdapter
import com.playbowdogs.neighbors_dogsitter_android.data.model.RecordedClipsResponse
import com.playbowdogs.neighbors_dogsitter_android.databinding.RecordedClipsListFragmentBinding
import com.playbowdogs.neighbors_dogsitter_android.utils.Status

class RecordedClipsListFragment : Fragment() {

    private lateinit var viewModel: RecordedClipsListViewModel
    private lateinit var mBinding: RecordedClipsListFragmentBinding
    private lateinit var adapter: RecordedClipsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = RecordedClipsListFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecordedClipsListViewModel::class.java)
        adapter = RecordedClipsListAdapter(arrayListOf())
        mBinding.fragmentRecordedClipsFragmentRecyclerView.adapter = adapter

        viewModel.getRecordedClips().observe(requireActivity(), Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        mBinding.fragmentRecordedClipsFragmentRecyclerView.visibility = View.VISIBLE
                        mBinding.progressView.visibility = View.GONE
                        resource.data?.let { accountCameras -> retrieveList(accountCameras.results) }
                    }
                    Status.ERROR -> {
                        mBinding.fragmentRecordedClipsFragmentRecyclerView.visibility = View.VISIBLE
                        mBinding.progressView.visibility = View.GONE
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        mBinding.progressView.visibility = View.VISIBLE
                        mBinding.fragmentRecordedClipsFragmentRecyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(results: List<RecordedClipsResponse.Result>) {
        adapter.apply {
            addClips(results)
            notifyDataSetChanged()
        }
    }
}