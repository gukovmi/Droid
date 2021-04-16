package com.example.droid.loan.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.droid.R
import com.example.droid.loan.domain.entity.Loan
import com.example.droid.loan.domain.entity.State
import com.example.droid.loan.ui.converter.OffsetDateTimeConverter
import kotlinx.android.synthetic.main.item_loan.view.*
import kotlinx.android.synthetic.main.item_header_personal_area.view.*

typealias OnLoanItemClick = (Loan) -> Unit
typealias OnButtonClick = () -> Unit

class PersonalAreaAdapter(
    private val context: Context,
    private var loansArrayList: ArrayList<Loan>,
    private val offsetDateTimeConverter: OffsetDateTimeConverter,
    private val authorizedUserNameText: String,
    private val onCreateLoanButtonClick: OnButtonClick,
    private val onUpdateButtonClick: OnButtonClick,
    private val onLoanItemClick: OnLoanItemClick
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private companion object {
        const val HEADER_VIEW_TYPE_CODE = 0
        const val LOAN_VIEW_TYPE_CODE = 1
    }

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindHeader() {
            itemView.apply {
                authorizedUserNameTextView.text = authorizedUserNameText
                if (loansArrayList.isEmpty()) loansHistoryTextView.visibility = View.GONE
                else loansHistoryTextView.visibility = View.VISIBLE
                createLoanButton.setOnClickListener { onCreateLoanButtonClick() }
                updateLoansButton.setOnClickListener { onUpdateButtonClick() }
            }
        }
    }

    inner class LoansListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindLoan(loan: Loan) {
            itemView.apply {
                amountTextView.text =
                    String.format(context.getString(R.string.amount_loan_text_view), loan.amount)
                idTextView.text =
                    String.format(context.getString(R.string.id_loan_text_view), loan.id)
                when (loan.state) {
                    State.APPROVED -> stateTextView.text = String.format(
                        context.getString(R.string.state_loan_text_view),
                        context.getString(R.string.approved)
                    )
                    State.REJECTED -> stateTextView.text = String.format(
                        context.getString(R.string.state_loan_text_view),
                        context.getString(R.string.rejected)
                    )
                    State.REGISTERED -> stateTextView.text = String.format(
                        context.getString(R.string.state_loan_text_view),
                        context.getString(R.string.registered)
                    )
                }
                when (loan.state) {
                    State.APPROVED -> stateImageView.setImageResource(R.drawable.ic_twotone_check_circle_24)
                    State.REJECTED -> stateImageView.setImageResource(R.drawable.ic_twotone_cancel_24)
                    State.REGISTERED -> stateImageView.setImageResource(R.drawable.ic_twotone_help_24)
                }
                dateTextView.text = offsetDateTimeConverter.fromOffsetDateTimeString(loan.date)
                setOnClickListener { onLoanItemClick(loan) }
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (position == 0) HEADER_VIEW_TYPE_CODE
        else LOAN_VIEW_TYPE_CODE

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder =
        when (viewType) {
            HEADER_VIEW_TYPE_CODE -> {
                val view =
                    LayoutInflater.from(context).inflate(R.layout.item_header_personal_area, parent, false)
                HeaderViewHolder(view)
            }
            LOAN_VIEW_TYPE_CODE -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_loan, parent, false)
                LoansListViewHolder(view)
            }
            else -> throw IllegalArgumentException()
        }

    override fun getItemCount(): Int {
        return loansArrayList.size + 1
    }

    fun getLoans(): ArrayList<Loan> = loansArrayList

    fun showLoans(loansList: List<Loan>) {
        this.loansArrayList.clear()
        loansArrayList.addAll(loansList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            HEADER_VIEW_TYPE_CODE -> (holder as HeaderViewHolder).bindHeader()
            LOAN_VIEW_TYPE_CODE -> (holder as LoansListViewHolder).bindLoan(loansArrayList[position - 1])
        }
    }
}