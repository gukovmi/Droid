package com.example.droid.loan.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.droid.R
import com.example.droid.loan.App
import com.example.droid.loan.domain.entity.Loan
import com.example.droid.loan.presentation.presenter.ResultPresenterImpl
import com.example.droid.loan.ui.base.BaseView
import kotlinx.android.synthetic.main.fragment_result.*
import javax.inject.Inject

interface ResultView : BaseView {
    fun showLoanDetails(loan: Loan)
    fun navigateTo(id: Int)
    fun recreateRequireActivity()
}

class ResultFragment : Fragment(), ResultView {
    @Inject
    lateinit var presenter: ResultPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.injectResultFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        initViews()

        val loan = arguments?.getParcelable<Loan>("loan")
        if (loan != null) {
            showLoanDetails(loan)
        }
    }

    private fun initViews() {
        topAppBarResult.menu.findItem(R.id.logoutMenuItem).isVisible = false
        topAppBarResult.setOnMenuItemClickListener {
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
        returnToPersonalAreaButton.setOnClickListener {
            presenter.returnToPreviousScreen()
        }
    }

    override fun showLoanDetails(loan: Loan) {
        informationResultTextView.text =
            String.format(getString(R.string.information_result_text_view), loan.amount, loan.id)
    }

    override fun navigateTo(id: Int) {
        findNavController().navigate(id)
    }

    override fun recreateRequireActivity() {
        ActivityCompat.recreate(requireActivity())
    }

    override fun onDestroy() {
        if (this::presenter.isInitialized) presenter.onDestroy()
        super.onDestroy()
    }
}