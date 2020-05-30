package com.playbowdogs.neighbors_dogsitter_android.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.playbowdogs.neighbors_dogsitter_android.databinding.LoginFragmentBinding
import org.koin.android.ext.android.inject

class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by inject()

    private lateinit var mBinding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = LoginFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mBinding.viewModel = viewModel

        setObservers()
        // TODO: Use the ViewModel
    }

    private fun setObservers() {
        viewModel.email.observe(viewLifecycleOwner, Observer {
            it.let { email ->
                mBinding.login.isEnabled = email.length >= 3
            }
        })

        mBinding.login.setOnClickListener {
            Log.e("onClick", "Clicked!")
            kotlin.run { viewModel.postLogin() }
        }
    }
}