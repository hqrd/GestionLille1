package com.tac.hqrd.gestionlille1.helper

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import com.google.android.material.appbar.AppBarLayout
import com.tac.hqrd.gestionlille1.R

/**
 * Helper to help manage non scrollable fragments
 */
class ScrollFragmentHelper {
    companion object {
        private var mScrollFlags: Int = 0

        /**
         * We avoid this fragment from being considred scrollable by the AppBarLayout
         */
        fun stopScroll(activity: FragmentActivity?) {
            if (mScrollFlags != 0) return

            val appbar = activity!!.findViewById(R.id.main_appbar) as AppBarLayout
            appbar.setExpanded(true, true)
            val toolbar = activity?.findViewById(R.id.main_toolbar) as Toolbar
            val p = toolbar.layoutParams as AppBarLayout.LayoutParams
            mScrollFlags = p.scrollFlags
            p.scrollFlags = 0
            toolbar.layoutParams = p
        }

        /**
         * We set back the default scrollFlags
         */
        fun startScroll(activity: FragmentActivity?) {
            if (mScrollFlags == 0) return

            val toolbar = activity!!.findViewById(R.id.main_toolbar) as Toolbar
            val p = toolbar.layoutParams as AppBarLayout.LayoutParams
            p.scrollFlags = mScrollFlags
            mScrollFlags = 0
            toolbar.layoutParams = p
        }
    }
}