package com.example.droid.loan.ui.fragment

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
import com.example.droid.loan.domain.entity.Loan
import com.example.droid.loan.presentation.presenter.PersonalAreaPresenterImpl
import com.example.droid.loan.ui.adapter.PersonalAreaAdapter
import com.example.droid.loan.ui.base.BaseView
import com.example.droid.loan.ui.converter.OffsetDateTimeConverter
import com.example.droid.loan.ui.converter.ThrowableConverter
import kotlinx.android.synthetic.main.fragment_personal_area.*
import javax.inject.Inject

interface PersonalAreaView : BaseView {
    fun navigateToWithBundle(id: Int, bundle: Bundle)
    fun showLoans(loansList: List<Loan>)
    fun startLoading()
    fun finishLoading()
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
    private lateinit var personalAreaAdapter: PersonalAreaAdapter

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
            val loansListAdapter = loansListRecyclerView.adapter as PersonalAreaAdapter
            outState.putParcelableArrayList("loans", loansListAdapter.getLoans())
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
        loansListRecyclerView.layoutManager = LinearLayoutManager(view.context)
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
        personalAreaAdapter = PersonalAreaAdapter(
            context = view.context,
            loansArrayList = arrayListOf(),
            offsetDateTimeConverter = offsetDateTimeConverter,
            authorizedUserNameText = String.format(
                getString(R.string.authorized_user_name_text_view),
                presenter.getName()
            ),
            onCreateLoanButtonClick = { presenter.getLoanConditions() },
            onUpdateButtonClick = { presenter.updateLoans() },
            onLoanItemClick = { loan: Loan ->
                presenter.getLoanDetails(loan.id)
            })
        loansListRecyclerView.adapter = personalAreaAdapter
    }

    override fun navigateTo(id: Int) {
        findNavController().navigate(id)
    }

    override fun navigateToWithBundle(id: Int, bundle: Bundle) {
        findNavController().navigate(id, bundle)
    }

    override fun showLoans(loansList: List<Loan>) {
        personalAreaAdapter.showLoans(loansList)
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

    override fun startLoading() {
        loadingProgressBarPersonalArea.visibility = View.VISIBLE
    }

    override fun finishLoading() {
        loadingProgressBarPersonalArea.visibility = View.GONE
    }

    override fun recreateRequireActivity() {
        recreate(requireActivity())
    }

    override fun onDestroy() {
        if (this::presenter.isInitialized) presenter.onDestroy()
        super.onDestroy()
    }
}