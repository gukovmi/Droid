package com.example.droid.loan.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.droid.R
import com.example.droid.loan.App
import com.example.droid.loan.presentation.presenter.LogoutPresenter
import com.example.droid.loan.ui.base.BaseView
import com.example.droid.loan.ui.converter.ThrowableConverter
import kotlinx.android.synthetic.main.fragment_logout_dialog.*
import javax.inject.Inject

interface LogoutView : BaseView {
    fun navigateTo(id: Int)
    fun returnToPreviousScreen()
    fun showError(throwable: Throwable)
}

class LogoutDialogFragment : DialogFragment(), LogoutView {
    @Inject
    lateinit var presenter: LogoutPresenter
    private lateinit var throwableConverter: ThrowableConverter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.injectLogoutDialogFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_logout_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        throwableConverter = ThrowableConverter(view.context)
        presenter.attachView(this)
        initViews()
    }

    private fun initViews() {
        yesLogoutDialogButton.setOnClickListener {
            presenter.logout()
        }
        noLogoutDialogButton.setOnClickListener {
            presenter.cancel()
        }
    }

    override fun navigateTo(id: Int) {
        findNavController().navigate(id)
    }

    override fun returnToPreviousScreen() {
        findNavController().popBackStack()
    }

    private fun showToast(message: String) {
        Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
    }

    override fun showError(throwable: Throwable) {
        showToast(throwableConverter.cast(throwable))
    }

    override fun onDestroy() {
        if (this::presenter.isInitialized) presenter.onDestroy()
        super.onDestroy()
    }
}