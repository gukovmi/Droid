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
import com.example.droid.loan.domain.entity.Loan
import com.example.droid.loan.domain.entity.State
import com.example.droid.loan.presentation.presenter.LoanDetailsPresenterImpl
import com.example.droid.loan.ui.base.BaseView
import com.example.droid.loan.ui.converter.OffsetDateTimeConverter
import com.example.droid.loan.ui.converter.ThrowableConverter
import kotlinx.android.synthetic.main.fragment_loan_details.*
import javax.inject.Inject

interface LoanDetailsView : BaseView {
    fun showLoanDetails(loan: Loan)
    fun navigateBack()
    fun navigateTo(id: Int)
    fun showError(throwable: Throwable)
    fun startLoading()
    fun finishLoading()
    fun recreateRequireActivity()
}

class LoanDetailsFragment : Fragment(), LoanDetailsView {
    @Inject
    lateinit var presenter: LoanDetailsPresenterImpl
    private lateinit var throwableConverter: ThrowableConverter
    private lateinit var offsetDateTimeConverter: OffsetDateTimeConverter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.injectLoanDetailsFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_loan_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        throwableConverter = ThrowableConverter(view.context)
        offsetDateTimeConverter = OffsetDateTimeConverter(view.context)
        presenter.attachView(this)
        initViews()
        val loanId = arguments?.getLong("loanId")
        if (loanId != null)
            presenter.getLoanById(loanId)
    }

    private fun initViews() {
        topAppBarLoanDetails.menu.findItem(R.id.logoutMenuItem).isVisible = false
        topAppBarLoanDetails.setOnMenuItemClickListener {
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
        topAppBarLoanDetails.setNavigationOnClickListener {
            presenter.returnToPreviousScreen()
        }
    }

    override fun showLoanDetails(loan: Loan) {
        when (loan.state) {
            State.APPROVED -> {
                detailsTextView.text = getString(R.string.information_details_approved_text_view)
                stateDetailsImageView.setImageResource(R.drawable.ic_twotone_check_circle_24)
            }
            State.REJECTED -> {
                detailsTextView.text = getString(R.string.information_details_rejected_text_view)
                stateDetailsImageView.setImageResource(R.drawable.ic_twotone_cancel_24)
            }
            State.REGISTERED -> {
                detailsTextView.text = getString(R.string.information_details_registered_text_view)
                stateDetailsImageView.setImageResource(R.drawable.ic_twotone_help_24)
            }
        }
        dateDetailsTextView.text = offsetDateTimeConverter.fromOffsetDateTimeString(loan.date)
        amountDetailsTextView.text =
            String.format(getString(R.string.amount_loan_text_view), loan.amount)
        firstNameDetailsTextView.text =
            String.format(getString(R.string.first_name_loan_text_view), loan.firstName)
        lastNameDetailsTextView.text =
            String.format(getString(R.string.last_name_loan_text_view), loan.lastName)
        idDetailsTextView.text = String.format(getString(R.string.id_loan_text_view), loan.id)
        percentDetailsTextView.text =
            String.format(getString(R.string.percent_loan_text_view), loan.percent.toString())
        periodDetailsTextView.text =
            String.format(getString(R.string.period_loan_text_view), loan.period)
        when (loan.state) {
            State.APPROVED -> stateDetailsTextView.text = String.format(
                getString(R.string.state_loan_text_view),
                getString(R.string.approved)
            )
            State.REJECTED -> stateDetailsTextView.text = String.format(
                getString(R.string.state_loan_text_view),
                getString(R.string.rejected)
            )
            State.REGISTERED -> stateDetailsTextView.text = String.format(
                getString(R.string.state_loan_text_view),
                getString(R.string.registered)
            )
        }
    }

    override fun navigateBack() {
        findNavController().popBackStack()
    }

    override fun navigateTo(id: Int) {
        findNavController().navigate(id)
    }

    private fun showToast(message: String) {
        Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
    }

    override fun showError(throwable: Throwable) {
        showToast(throwableConverter.cast(throwable))
    }

    override fun startLoading() {
        loadingLoanDetailsProgressBar.visibility = View.VISIBLE
    }

    override fun finishLoading() {
        loadingLoanDetailsProgressBar.visibility = View.GONE
    }

    override fun recreateRequireActivity() {
        recreate(requireActivity())
    }

    override fun onDestroy() {
        if (this::presenter.isInitialized) presenter.onDestroy()
        super.onDestroy()
    }
}