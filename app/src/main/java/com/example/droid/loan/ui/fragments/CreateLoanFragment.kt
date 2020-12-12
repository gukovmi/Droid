package com.example.droid.loan.ui.fragments

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
import com.example.droid.loan.domain.entities.LoanConditions
import com.example.droid.loan.presentation.presenters.CreateLoanPresenterImpl
import com.example.droid.loan.ui.base.BaseView
import com.example.droid.loan.ui.converters.ThrowableConverter
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_create_loan.*
import javax.inject.Inject

interface CreateLoanView : BaseView {
    fun showLoanConditions(loanConditions: LoanConditions)
    fun navigateTo(id: Int)
    fun navigateToWithBundle(id: Int, bundle: Bundle)
    fun showError(throwable: Throwable)
    fun showEmptyFieldsWarning()
    fun showExceedingMaxAmountWarning()
    fun showIsNotInitializedVariableWarning()
    fun startCreateLoanLoading()
    fun finishCreateLoanLoading()
    fun recreateRequireActivity()
}

class CreateLoanFragment : Fragment(), CreateLoanView {
    @Inject
    lateinit var presenter: CreateLoanPresenterImpl

    @Inject
    lateinit var throwableConverter: ThrowableConverter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.injectCreateLoanFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_loan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        initViews()
        val loanConditions = arguments?.getParcelable<LoanConditions>("loanConditions")
        if (loanConditions != null) {
            presenter.setLoanConditions(loanConditions)
            presenter.showLoanConditions()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val firstNameCreateEditText =
            view?.findViewById<TextInputLayout>(R.id.firstNameCreateEditText)
        if (firstNameCreateEditText != null) {
            outState.putString("firstName", firstNameCreateEditText.editText?.text.toString())
        }
        val lastNameCreateEditText =
            view?.findViewById<TextInputLayout>(R.id.lastNameCreateEditText)
        if (lastNameCreateEditText != null) {
            outState.putString("lastName", lastNameCreateEditText.editText?.text.toString())
        }
        val amountCreateEditText = view?.findViewById<TextInputLayout>(R.id.amountCreateEditText)
        if (amountCreateEditText != null) {
            outState.putString("amount", amountCreateEditText.editText?.text.toString())
        }
        val phoneNumberCreateEditText =
            view?.findViewById<TextInputLayout>(R.id.phoneNumberCreateEditText)
        if (phoneNumberCreateEditText != null) {
            outState.putString("phoneNumber", phoneNumberCreateEditText.editText?.text.toString())
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        val firstNameCreateEditText =
            view?.findViewById<TextInputLayout>(R.id.firstNameCreateEditText)
        firstNameCreateEditText?.editText?.setText(savedInstanceState?.getString("firstName"))
        val lastNameCreateEditText =
            view?.findViewById<TextInputLayout>(R.id.lastNameCreateEditText)
        lastNameCreateEditText?.editText?.setText(savedInstanceState?.getString("lastName"))
        val amountCreateEditText = view?.findViewById<TextInputLayout>(R.id.amountCreateEditText)
        amountCreateEditText?.editText?.setText(savedInstanceState?.getString("amount"))
        val phoneNumberCreateEditText =
            view?.findViewById<TextInputLayout>(R.id.phoneNumberCreateEditText)
        phoneNumberCreateEditText?.editText?.setText(savedInstanceState?.getString("phoneNumber"))
    }

    private fun initViews() {
        sendRequestButton.setOnClickListener {
            val amount = amountCreateEditText.editText?.text.toString()
            val firstName = firstNameCreateEditText.editText?.text.toString()
            val lastName = lastNameCreateEditText.editText?.text.toString()
            val phoneNumber = phoneNumberCreateEditText.editText?.text.toString()
            presenter.createLoan(
                amount = amount,
                firstName = firstName,
                lastName = lastName,
                phoneNumber = phoneNumber
            )
        }
        topAppBarCreateLoan.menu.findItem(R.id.logoutMenuItem).isVisible = false
        topAppBarCreateLoan.setOnMenuItemClickListener {
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
        topAppBarCreateLoan.setNavigationOnClickListener {
            presenter.returnToPreviousScreen()
        }
    }

    override fun showLoanConditions(loanConditions: LoanConditions) {
        maxAmountCreateTextView.text = String.format(
            getString(R.string.max_amount_create_text_view),
            loanConditions.maxAmount.toString()
        )
        percentCreateTextView.text = String.format(
            getString(R.string.percent_loan_text_view),
            loanConditions.percent.toString()
        )
        periodCreateTextView.text = String.format(
            getString(R.string.period_loan_text_view),
            loanConditions.period.toString()
        )
    }

    override fun navigateTo(id: Int) {
        findNavController().navigate(id)
    }

    override fun navigateToWithBundle(id: Int, bundle: Bundle) {
        findNavController().navigate(id, bundle)
    }

    private fun showToast(message: String) {
        Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
    }

    override fun showError(throwable: Throwable) {
        showToast(throwableConverter.cast(throwable))
    }

    override fun showEmptyFieldsWarning() {
        showToast(getString(R.string.some_fields_are_empty))
    }

    override fun showExceedingMaxAmountWarning() {
        showToast(getString(R.string.amount_is_not_valid))
    }

    override fun showIsNotInitializedVariableWarning() {
        showToast(getString(R.string.variable_is_not_initialized))
    }

    override fun startCreateLoanLoading() {
        loadingCreateLoanProgressBar.visibility = View.VISIBLE
    }

    override fun finishCreateLoanLoading() {
        loadingCreateLoanProgressBar.visibility = View.GONE
    }

    override fun recreateRequireActivity() {
        ActivityCompat.recreate(requireActivity())
    }

    override fun onDestroy() {
        if (this::presenter.isInitialized) presenter.onDestroy()
        super.onDestroy()
    }
}