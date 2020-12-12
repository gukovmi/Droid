package com.example.droid.loan.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.droid.R
import com.example.droid.loan.App
import com.example.droid.loan.presentation.presenters.StartPresenterImpl
import com.example.droid.loan.ui.base.BaseView
import javax.inject.Inject

interface StartView : BaseView {
    fun navigateTo(id: Int)
}

class StartFragment : Fragment(), StartView {
    @Inject
    lateinit var presenter: StartPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.injectStartFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
    }

    override fun navigateTo(id: Int) {
        findNavController().navigate(id)
    }

    override fun onDestroy() {
        if (this::presenter.isInitialized) presenter.onDestroy()
        super.onDestroy()
    }
}