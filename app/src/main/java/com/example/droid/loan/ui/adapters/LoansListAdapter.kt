package com.example.droid.loan.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.droid.R
import com.example.droid.loan.domain.entities.Loan
import com.example.droid.loan.domain.entities.State
import com.example.droid.loan.ui.converters.OffsetDateTimeConverter
import kotlinx.android.synthetic.main.item_loan.view.*

typealias OnLoanItemClick = (Loan) -> Unit

class LoansListAdapter(
    private val context: Context,
    private var loansArrayList: ArrayList<Loan>,
    private val offsetDateTimeConverter: OffsetDateTimeConverter,
    private val onLoanItemClick: OnLoanItemClick
) : RecyclerView.Adapter<LoansListAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindLoan(
            loan: Loan,
            offsetDateTimeConverter: OffsetDateTimeConverter,
            onLoanItemClick: OnLoanItemClick
        ) {
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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_loan, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return loansArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindLoan(loansArrayList[position], offsetDateTimeConverter, onLoanItemClick)
    }

    fun getLoansArrayList(): ArrayList<Loan> {
        val loansArrayList = arrayListOf<Loan>()
        this.loansArrayList.forEach { loansArrayList.add(it) }
        return loansArrayList
    }

    fun showLoans(loansList: List<Loan>) {
        this.loansArrayList = arrayListOf()
        loansList.forEach { this.loansArrayList.add(it) }
        notifyDataSetChanged()
    }
}