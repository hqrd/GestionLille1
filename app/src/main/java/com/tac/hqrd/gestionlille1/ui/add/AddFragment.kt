package com.tac.hqrd.gestionlille1.ui.add

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.tac.hqrd.gestionlille1.R
import com.tac.hqrd.gestionlille1.viewmodel.ListIssuesViewModel
import kotlinx.android.synthetic.main.add_fragment.*

class AddFragment : Fragment() {

    private lateinit var viewModel: ListIssuesViewModel

    private lateinit var appbar: AppBarLayout
    private lateinit var toolbar: Toolbar
    private var mScrollFlags: Int = AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS

    override fun onAttach(context: Context) {
        super.onAttach(context)
        toolbar = activity!!.findViewById(R.id.main_toolbar) as Toolbar
        appbar = activity!!.findViewById(R.id.main_appbar) as AppBarLayout
        appbar.setExpanded(true, true)
    }

    override fun onResume() {
        super.onResume()
        stopScroll()
    }

    override fun onPause() {
        startScroll()
        super.onPause()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.run {
            viewModel = ViewModelProviders.of(this).get(ListIssuesViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.add_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val navFrament = findNavController()
        button.setOnClickListener {
            viewModel.addIssue()
            val action = AddFragmentDirections.actionToListFragment()
            navFrament.navigate(action)
        }
    }


    fun stopScroll() {
        if (mScrollFlags == 0) return
        val p = toolbar.layoutParams as AppBarLayout.LayoutParams
        mScrollFlags = p.scrollFlags
        p.scrollFlags = 0
        toolbar.layoutParams = p
    }

    fun startScroll() {
        val p = toolbar.layoutParams as AppBarLayout.LayoutParams
        p.scrollFlags = mScrollFlags
        toolbar.layoutParams = p
    }

}
