package com.example.droid.loan.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.droid.R
import com.example.droid.loan.App
import com.example.droid.loan.domain.entities.Loan
import com.example.droid.loan.presentation.presenters.PersonalAreaPresenterImpl
import com.example.droid.loan.ui.adapters.LoansListAdapter
import com.example.droid.loan.ui.base.BaseView
import com.example.droid.loan.ui.converters.OffsetDateTimeConverter
import com.example.droid.loan.ui.converters.ThrowableConverter
import kotlinx.android.synthetic.main.fragment_personal_area.*
import javax.inject.Inject

interface PersonalAreaView : BaseView {
    fun navigateToWithBundle(id: Int, bundle: Bundle)
    fun showLoans(loansList: List<Loan>)
    fun startLoansLoading()
    fun finishLoansLoading()
    fun startLoanConditionsLoading()
    fun finishLoanConditionsLoading()
    fun navigateTo(id: Int)
    fun showError(throwable: Throwable)
    fun showEmptyFieldsWarning()
    fun recreateRequireActivity()
}

class PersonalAreaFragment : Fragment(), PersonalAreaView {
    @Inject
    lateinit var presenter: PersonalAreaPresenterImpl
    private lateinit var throwableConverter: ThrowableConverter
    private lateinit var offsetDateTimeConverter: OffsetDateTimeConverter
    private lateinit var loansListAdapter: LoansListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.injectPersonalAreaFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_personal_area, container, false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val loansListRecyclerView = view?.findViewById<RecyclerView>(R.id.loansListRecyclerView)
        if (loansListRecyclerView != null) {
            val loansListAdapter = loansListRecyclerView.adapter as LoansListAdapter
            outState.putParcelableArrayList("loans", loansListAdapter.getLoansArrayList())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        throwableConverter = ThrowableConverter(view.context)
        offsetDateTimeConverter = OffsetDateTimeConverter(view.context)
        presenter.attachView(this)
        initViews(view)
        initAdapter(view)

        val fromLogin = arguments?.getBoolean("fromLogin")
        if (fromLogin == true) presenter.getHelpFirstTime()

        val loansArrayList = savedInstanceState?.getParcelableArrayList<Loan>("loans")
        if (loansArrayList != null)
            showLoans(loansArrayList)
        else
            presenter.getLoans()
    }

    private fun initViews(view: View) {
        authorizedUserNameTextView.text =
            String.format(getString(R.string.authorized_user_name_text_view), presenter.getName())
        loansListRecyclerView.layoutManager = LinearLayoutManager(view.context)
        createLoanButton.setOnClickListener {
            presenter.getLoanConditions()
        }
        updateLoansButton.setOnClickListener {
            presenter.updateLoans()
        }
        topAppBarPersonalArea.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.helpMenuItem -> {
                    presenter.showHelpDialog()
                    true
                }
                R.id.logoutMenuItem -> {
                    presenter.logout()
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

    private fun initAdapter(view: View) {
        loansListAdapter = LoansListAdapter(
            context = view.context,
            loansArrayList = arrayListOf(),
            offsetDateTimeConverter = offsetDateTimeConverter
        ) { loan: Loan ->
            presenter.getLoanDetails(loan.id)
        }
        loansListRecyclerView.adapter = loansListAdapter
    }

    override fun navigateTo(id: Int) {
        findNavController().navigate(id)
    }

    override fun navigateToWithBundle(id: Int, bundle: Bundle) {
        findNavController().navigate(id, bundle)
    }

    override fun showLoans(loansList: List<Loan>) {
        loansListAdapter.showLoans(loansList)
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

    override fun startLoansLoading() {
        loadingLoansListProgressBar.visibility = View.VISIBLE
    }

    override fun finishLoansLoading() {
        loadingLoansListProgressBar.visibility = View.GONE
    }

    override fun startLoanConditionsLoading() {
        loadingLoanConditionsProgressBar.visibility = View.VISIBLE
    }

    override fun finishLoanConditionsLoading() {
        loadingLoanConditionsProgressBar.visibility = View.GONE
    }

    override fun recreateRequireActivity() {
        recreate(requireActivity())
    }

    override fun onDestroy() {
        if (this::presenter.isInitialized) presenter.onDestroy()
        super.onDestroy()
    }
}