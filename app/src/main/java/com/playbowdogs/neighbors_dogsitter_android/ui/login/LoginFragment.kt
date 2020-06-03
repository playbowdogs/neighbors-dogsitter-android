package com.playbowdogs.neighbors_dogsitter_android.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.playbowdogs.neighbors_dogsitter_android.R
import com.playbowdogs.neighbors_dogsitter_android.databinding.LoginFragmentBinding
import com.playbowdogs.neighbors_dogsitter_android.utils.Status
import org.koin.android.ext.android.inject

class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by inject()

    private lateinit var mBinding: LoginFragmentBinding
    private lateinit var afterTextChangedListener: TextWatcher

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

        createTextListener()
        setObservers()
        setOnClick()

        viewModel.checkAuthToken()
    }

    private fun createTextListener() {
        afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
                // ignore
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                viewModel.loginDataChanged(
                    mBinding.username.text.toString(),
                    mBinding.password.text.toString()
                )
            }
        }
    }

    private fun setObservers() {
        mBinding.username.addTextChangedListener(afterTextChangedListener)
        mBinding.password.addTextChangedListener(afterTextChangedListener)

        mBinding.password.setOnEditorActionListener { _: TextView?, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.postLogin()
            }
            false
        }

        viewModel.sharedPrefAuthToken.observe(viewLifecycleOwner, Observer {
            it.let { authToken ->
                Log.e("authToken", authToken)
                if (authToken != "") {
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                }
            }
        })

        viewModel.loginFormState.observe(viewLifecycleOwner, Observer { loginFormState ->
            if (loginFormState == null) {
                return@Observer
            }

            mBinding.login.isEnabled = loginFormState.isDataValid

            if (loginFormState.usernameError != null) {
                mBinding.username.error = getString(loginFormState.usernameError!!)
            } else {
                mBinding.username.error = null
            }
            if (loginFormState.passwordError != null) {
                mBinding.password.error = getString(loginFormState.passwordError!!)
            } else {
                mBinding.password.error = null
            }
        })

        viewModel.userModel.observe(viewLifecycleOwner, Observer {
            it.let { resource ->
                when (resource.status) {
                    Status.LOADING -> mBinding.progressView.visibility = View.VISIBLE

                    Status.SUCCESS -> {
                        mBinding.progressView.visibility = View.GONE
                        if (resource.data != null) {
                            LoggedInUser(resource.data.data)
                            viewModel.saveAuthToken(resource.data.data.authToken)
                            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                        }
                    }

                    Status.ERROR -> {
                        mBinding.progressView.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            resource.message  ?: "There was an error!",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun setOnClick() {
        mBinding.login.setOnClickListener { viewModel.postLogin() }
    }
}