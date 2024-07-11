package com.example.nike.features.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.nike.R
import com.example.nike.common.NikeCompletableObserver
import com.example.nike.databinding.FragmentSignupBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment :Fragment(){

    val viewModel: AuthViewModel by viewModel()
    val compositeDisposable = CompositeDisposable()
    private lateinit var binding:FragmentSignupBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_signup,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signUpBtn.setOnClickListener {
            viewModel.signUp(binding.emailEt.text.toString(),binding.passwordEt.text.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                        override fun onComplete() {
                            requireActivity().finish()
                        }
                    })
        }

        binding.loginLinkBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container, LoginFragment())
            }.commit()
        }
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}