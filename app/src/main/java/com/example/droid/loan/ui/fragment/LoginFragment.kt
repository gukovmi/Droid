package com.example.droid.loan.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.droid.R
import com.example.droid.loan.App
import com.example.droid.loan.presentation.presenter.LoginPresenter
import com.example.droid.loan.ui.base.BaseView
import com.example.droid.loan.ui.converter.ThrowableConverter
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

interface LoginView : BaseView {
    fun navigateBack()
    fun navigateTo(id: Int)
    fun navigateToWithBundle(id: Int, bundle: Bundle)
    fun showError(throwable: Throwable)
    fun showEmptyFieldsWarning()
    fun startLoading()
    fun finishLoading()
    fun recreateRequireActivity()
}

class LoginFragment : Fragment(), LoginView {
    @Inject
    lateinit var presenter: LoginPresenter
    private lateinit var throwableConverter: ThrowableConverter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.injectLoginFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        throwableConverter = ThrowableConverter(view.context)
        presenter.attachView(this)
        initViews()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        nameLoginEditText?.let { outState.putString("name", it.editText?.text.toString()) }
        passwordLoginEditText?.let { outState.putString("password", it.editText?.text.toString()) }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        nameLoginEditText?.editText?.setText(savedInstanceState?.getString("name"))
        passwordLoginEditText?.editText?.setText(savedInstanceState?.getString("password"))
    }

    private fun initViews() {
        loginButton.setOnClickListener {
            val name = nameLoginEditText.editText?.text.toString()
            val password = passwordLoginEditText.editText?.text.toString()
            presenter.login(name, password)
        }
        topAppBarLogin.menu.findItem(R.id.logoutMenuItem).isVisible = false
        topAppBarLogin.setOnMenuItemClickListener {
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
        topAppBarLogin.setNavigationOnClickListener {
            presenter.returnToPreviousScreen()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
    }

    override fun navigateBack() {
        findNavController().popBackStack()
    }

    override fun navigateTo(id: Int) {
        findNavController().navigate(id)
    }

    override fun navigateToWithBundle(id: Int, bundle: Bundle) {
        findNavController().navigate(id, bundle)
    }

    override fun showError(throwable: Throwable) {
        showToast(throwableConverter.cast(throwable))
    }

    override fun showEmptyFieldsWarning() {
        showToast(getString(R.string.some_fields_are_empty))
    }

    override fun startLoading() {
        loadingLoginProgressBar.visibility = View.VISIBLE
    }

    override fun finishLoading() {
        loadingLoginProgressBar.visibility = View.GONE
    }

    override fun recreateRequireActivity() {
        ActivityCompat.recreate(requireActivity())
    }

    override fun onDestroy() {
        if (this::presenter.isInitialized) presenter.onDestroy()
        super.onDestroy()
    }
}