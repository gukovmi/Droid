package com.example.droid.loan.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.droid.R
import com.example.droid.loan.App
import com.example.droid.loan.presentation.presenter.RegistrationPresenterImpl
import com.example.droid.loan.ui.base.BaseView
import com.example.droid.loan.ui.converter.ThrowableConverter
import kotlinx.android.synthetic.main.fragment_registration.*
import javax.inject.Inject

interface RegistrationView : BaseView {
    fun navigateTo(id: Int)
    fun showError(throwable: Throwable)
    fun showEmptyFieldsWarning()
    fun showRegistrationSuccessMessage(name: String)
    fun startLoading()
    fun finishLoading()
    fun recreateRequireActivity()
}

class RegistrationFragment : Fragment(), RegistrationView {
    @Inject
    lateinit var presenter: RegistrationPresenterImpl
    private lateinit var throwableConverter: ThrowableConverter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.injectRegistrationFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        throwableConverter = ThrowableConverter(view.context)
        presenter.attachView(this)
        initViews()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        nameRegistrationEditText?.let { outState.putString("name", it.editText?.text.toString()) }
        passwordRegistrationEditText?.let { outState.putString("password", it.editText?.text.toString()) }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        nameRegistrationEditText?.editText?.setText(savedInstanceState?.getString("name"))
        passwordRegistrationEditText?.editText?.setText(savedInstanceState?.getString("password"))
    }

    private fun initViews() {
        registrationButton.setOnClickListener {
            val name = nameRegistrationEditText.editText?.text.toString()
            val password = passwordRegistrationEditText.editText?.text.toString()
            presenter.registration(name, password)
        }
        skipRegistrationButton.setOnClickListener {
            presenter.skipRegistration()
        }
        topAppBarRegistration.menu.findItem(R.id.logoutMenuItem).isVisible = false
        topAppBarRegistration.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.helpMenuItem -> {
                    presenter.showHelpDialog()
                    true
                }
                R.id.englishLanguageMenuItem -> {
                    presenter.setLanguage("en")
                    true
                }
                R.id.russianLanguageMenuItem -> {
                    presenter.setLanguage("ru")
                    true
                }
                else -> false
            }
        }
    }

    override fun navigateTo(id: Int) {
        findNavController().navigate(id)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun showError(throwable: Throwable) {
        showToast(throwableConverter.cast(throwable))
    }

    override fun showEmptyFieldsWarning() {
        showToast(getString(R.string.some_fields_are_empty))
    }

    override fun showRegistrationSuccessMessage(name: String) {
        showToast(getString(R.string.registration_success, name))
    }

    override fun startLoading() {
        loadingRegistrationProgressBar.visibility = View.VISIBLE
    }

    override fun finishLoading() {
        loadingRegistrationProgressBar.visibility = View.GONE
    }

    override fun recreateRequireActivity() {
        recreate(requireActivity())
    }

    override fun onDestroy() {
        if (this::presenter.isInitialized) presenter.onDestroy()
        super.onDestroy()
    }
}