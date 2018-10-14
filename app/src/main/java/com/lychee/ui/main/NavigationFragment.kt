package com.lychee.ui.main

interface NavigationFragment {

    /**
     * @return True if the fragment handled the back press, false otherwise.
     */
    fun onBackPressed(): Boolean {
        return false
    }
}