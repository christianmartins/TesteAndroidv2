package br.com.crmm.bankapplication.framework.presentation.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import br.com.crmm.bankapplication.databinding.LoginFragmentLayoutBinding
import br.com.crmm.bankapplication.framework.presentation.ui.common.AbstractFragment
import br.com.crmm.bankapplication.framework.presentation.ui.extension.*
import br.com.crmm.bankapplication.framework.presentation.ui.login.state.LoginState
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : AbstractFragment(){

    private lateinit var binding: LoginFragmentLayoutBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = initBinding(inflater, container)
        return binding.root
    }

    private fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = LoginFragmentLayoutBinding.inflate(
        inflater, container, false
    ).apply {
        lifecycleOwner = this@LoginFragment.viewLifecycleOwner
        viewModel = this@LoginFragment.viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginState()
    }

    private fun loginState(){
        viewModel.loginState.observe(viewLifecycleOwner, Observer {state ->
            with(binding){
                loginProgress.invisible()
                when(state){
                    LoginState.InvalidInputUsernameState -> {
                        textInputLayoutUsername.invalidEmailOrCpf()
                    }
                    LoginState.InvalidInputPasswordState -> {
                        textInputLayoutUsername.hideError()
                        textInputLayoutPassword.invalidPassword()
                    }
                    LoginState.LoadingState -> {
                        textInputLayoutUsername.hideError()
                        textInputLayoutPassword.hideError()
                        loginProgress.show()
                    }
                }
            }
        })
    }
}