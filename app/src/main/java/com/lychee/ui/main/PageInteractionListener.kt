package com.lychee.ui.main

interface PageInteractionListener {

    fun showToolbarAndBottomNavigationView()

    fun hideToolbarAndBottomNavigationView()

    fun onScaleToolbarAndBottomNavigationView(toScale: Float)
}